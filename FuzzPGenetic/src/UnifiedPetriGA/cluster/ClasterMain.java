package UnifiedPetriGA.cluster;

import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ejml.data.DMatrixRMaj;

import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FullRecorder;
import core.common.recoder.fullrecorderevents.AbstractTransitionEvent;
import core.common.recoder.fullrecorderevents.IFullRecorderEvent;
import main.ScenarioSaverLoader;

public class ClasterMain {
  
  
  
  public static void main(String args[]){
   
    ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> saver = new ScenarioSaverLoader<>(
        UnifiedPetriNet.class);
    
    saver.load(new File("rezScenario.json"), UnifiedToken::buildFromString);
    
    DistaneMetricCollector transitionConnectionRecord = new DistaneMetricCollector();
    
    FullRecorder<UnifiedToken> rec = saver.getFullRec();
    UnifiedPetriNet net = saver.getPetriNet();
    for(List<IFullRecorderEvent> eventsOfTick :rec.eventGroupedByTicks()){

      Set<Integer> transitions = eventsOfTick.stream()
        .filter(e -> e instanceof AbstractTransitionEvent)
        .map(e -> ((AbstractTransitionEvent) e).trNr)
        .collect(toSet());
      System.out.println(transitions);
      Iterator<Integer> iter = transitions.iterator();
      
      while(iter.hasNext()){
        Integer fiTr = iter.next();
        iter.remove();
        transitions.remove(fiTr);
        for(Integer seTr : transitions){
          transitionConnectionRecord.put(fiTr, seTr);
        }
      }
    }
    
    System.out.println(transitionConnectionRecord.createGraphMatrix(0, net.getNrOfTransition()));
    
    MarkovClustering cls = new MarkovClustering(transitionConnectionRecord.createGraphMatrix(0, net.getNrOfTransition()));
    DMatrixRMaj run = cls.run();
    System.out.println(run);
    System.out.println(cls.attractors(run));
    System.out.println(cls.attractors(run).size());

  }

}
