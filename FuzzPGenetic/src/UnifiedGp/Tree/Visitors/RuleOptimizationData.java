package UnifiedGp.Tree.Visitors;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RuleOptimizationData {
  public enum OptType{
   Aux, Inp, Out, NoModif, Modif, SplitEnter,  SplitExit 
  }
  final Map<OptType, Set<Integer>>  data;
  
  public RuleOptimizationData() {
    data =new EnumMap<OptType, Set<Integer> >(OptType.class);
  }
  
  public void register(OptType type, Integer trId){
    data.putIfAbsent(type, new HashSet<>());
    data.get(type).add(trId);
  }
  
  public int allTransitions(){
    return data.values().stream().mapToInt(trIdSet -> trIdSet.size()).sum();
  }

  public Set<Integer> getType(OptType type) {
    return data.get(type);
  }

}
