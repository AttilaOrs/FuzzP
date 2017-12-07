package UnifiedPetriRuleOptimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import core.FuzzyPetriLogic.FuzzyValue;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedPetriNetCloner;

public class BitIndiDecoder {


  private final UnifiedPetriNet originalNet;
  private final List<Integer> trToOptimize;
  private final long nrOfFuzzyRules;

  public BitIndiDecoder(UnifiedPetriNet original, List<Integer> toOptimze) {
    originalNet = original;
    trToOptimize = toOptimze;
    nrOfFuzzyRules = computeNrOfRules();
  }

  public long getNrOfRules() {
    return nrOfFuzzyRules;
  }

  private long computeNrOfRules() {
    long nonPhiRules = trToOptimize.stream()
        .flatMap(trId -> originalNet.getTableForTransition(trId).getValues())
        .filter(((Predicate<FuzzyValue>) FuzzyValue::isPhi).negate())
        .count();
    return nonPhiRules;
  }

  public UnifiedPetriNet modified(BitIndi indi) {
    HashMap<Integer, IUnifiedTable> tables = new HashMap<>();
    IterBasedOnInidi iter = new IterBasedOnInidi(indi);
    for (Integer trId : trToOptimize) {
      IUnifiedTable newTable = originalNet.getTableForTransition(trId).newTableBasedOnValues(iter);
      tables.put(trId, newTable);
    }
    return UnifiedPetriNetCloner.cloneUnifiedPetriNetWithModifiedTabler(originalNet, tables);

  }

  public BitIndiDecoder myClone() {
    UnifiedPetriNet cloned = UnifiedPetriNetCloner.cloneUnifiedPetriNet(originalNet);
    ArrayList<Integer> wow = new ArrayList<>(trToOptimize);
    return new BitIndiDecoder(cloned, wow);
  }

  public class IterBasedOnInidi implements Iterator<FuzzyValue> {

    private BitIndi indi;
    private int currentIndex;

    public IterBasedOnInidi(BitIndi indi) {
      this.indi = indi;
      this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
      return currentIndex * 3 + 2 < indi.getGenome().length();
    }

    @Override
    public FuzzyValue next() {
      int val = 0;
      for (int i = 0; i < 3; i++) {
        val += (indi.getGenome().get(currentIndex * 3 + i) ? 1 : 0) << i;
      }
      currentIndex++;
      FuzzyValue toRet = FuzzyValue.fromInt(val);
      if (toRet.isPhi()) {
        System.err.println("Shoudn't decocode phi");

      }
      return toRet;

    }

  }
}
