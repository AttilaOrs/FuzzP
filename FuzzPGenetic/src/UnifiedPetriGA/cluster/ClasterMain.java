package UnifiedPetriGA.cluster;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FullRecorder;
import core.common.recoder.fullrecorderevents.AbstractTransitionEvent;
import core.common.recoder.fullrecorderevents.IFullRecorderEvent;
import main.ScenarioSaverLoader;

public class ClasterMain {
  
  public static class CommonRun {
    private final HashMap<Integer, Map<Integer, Integer>> rez;
     
    public CommonRun(){
      rez = new HashMap<>();
    }
    
    public void put(Integer fiTrId, Integer seTrId){
      rez.putIfAbsent(fiTrId, new HashMap<>());
      Integer newCount =   rez.get(fiTrId).getOrDefault(seTrId, 0)+1 ;
      rez.get(fiTrId).put(seTrId, newCount);
      rez.putIfAbsent(seTrId, new HashMap<>());
      rez.get(seTrId).put(fiTrId, newCount);
    }
    
    public String toString(){
      return rez.entrySet().stream()
          .map(e -> e.getKey() + " " + e.getValue())
          .collect(joining("\n"));
    }
    
    
  }
  
  public static void main(String args[]){
   
    ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> saver = new ScenarioSaverLoader<>(
        UnifiedPetriNet.class);
    
    saver.load(new File("rezScenario.json"), UnifiedToken::buildFromString);
    
    CommonRun transitionConnectionRecord = new CommonRun();
    
    FullRecorder<UnifiedToken> rec = saver.getFullRec();
    UnifiedPetriNet net = saver.getPetriNet();
    for(List<IFullRecorderEvent> eventsOfTick :rec.eventGroupedByTicks()){
      Set<Integer> transitions = eventsOfTick.stream()
        .filter(e -> e instanceof AbstractTransitionEvent)
        .map(e -> ((AbstractTransitionEvent) e).trNr)
        .collect(toSet());
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
    
    System.out.println(transitionConnectionRecord);
  }

}
