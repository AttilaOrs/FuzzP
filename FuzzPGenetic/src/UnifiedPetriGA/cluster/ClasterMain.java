package UnifiedPetriGA.cluster;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;
import static org.ejml.dense.row.CommonOps_DDRM.mult;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ejml.data.DMatrix1Row;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;

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
    
    Integer currentTr;
    DMatrixRMaj currentGraphMatrix;
    
    public DMatrixRMaj createGraphMatrix(int limit, int nrOfTransitions){
      
      
      currentGraphMatrix =new DMatrixRMaj(nrOfTransitions, nrOfTransitions);
      for(currentTr = 0; currentTr < nrOfTransitions; currentTr++){
        if(rez.containsKey(currentTr)){
          int sum = rez.get(currentTr).entrySet().stream().mapToInt(e -> e.getValue()).filter(i -> i > limit).sum();
          double probPerHit  = 0.90 / sum;
          rez.get(currentTr).entrySet().stream()
            .filter(e -> e.getValue() > limit)
            .forEach( e -> {
              setValueForMatrix(e.getKey(), e.getValue()*probPerHit);
            });
          currentGraphMatrix.set(currentTr, currentTr, 0.10); // self loop
        } else {
          //unconnected transition
          currentGraphMatrix.set(currentTr, currentTr, 1.00); // self loop
        }
        
      }
      return currentGraphMatrix;
    }
    
    
    private void setValueForMatrix(Integer seTr, Double val){
      currentGraphMatrix.set( seTr, currentTr, val);
    }
    
    
  }
  
  public static class MarkovClustering  {
    
    private final DMatrixRMaj base;

    public MarkovClustering(DMatrixRMaj base){
      this.base = base;
    }
    
    public DMatrixRMaj run(){
      double residual = 1.0;
      DMatrixRMaj old = base;
      while(residual > 0.001){
        DMatrixRMaj expaneded = new DMatrixRMaj(base.numCols, base.numRows);
        mult(old, old, expaneded);
        //System.out.println(expaneded);
        residual = inflate(expaneded) ;
        old = expaneded;
        System.out.println("pukk" +residual);
        
      }
      return old;
    }

    private double inflate(DMatrixRMaj expaneded) {
      double overallMaxDiff = 0;
      for(int i = 0; i < expaneded.numCols; i++){
        double sum = 0.0;
        double sumNormalized = 0.0;
        double maxNormalized = 0.0;
        int nonZero = 0;
        for(int j = 0; j < expaneded.numRows; j++){
          double v = expaneded.get(j, i);
          v *=v;
          v = (v < 0.00001)?0.0:v;
          expaneded.set(j, i, v);
          sum += v;
        }
        if(sum != 0.0){
          for(int j = 0; j < expaneded.numRows; j++){
            double v = expaneded.get(j, i);
            v /= sum;
            expaneded.set(j, i, v);
            sumNormalized +=v;
            maxNormalized = (maxNormalized<v)?v:maxNormalized;
            nonZero += (v!=0.0)?1:0;
          }
          double w = maxNormalized -  (sumNormalized/nonZero);
          overallMaxDiff = (overallMaxDiff < w)?w:overallMaxDiff;
        }
      }
      
      
      return overallMaxDiff;
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
    
    System.out.println(transitionConnectionRecord.createGraphMatrix(0, net.getNrOfTransition()));
    
    MarkovClustering cls = new MarkovClustering(transitionConnectionRecord.createGraphMatrix(0, net.getNrOfTransition()));
    System.out.println(cls.run());
  }

}
