package UnifiedPetriGA.mapper;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import core.FuzzyPetriLogic.FuzzyValue;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.tables.Operator;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;

class UnifiedTableFactory {
  private boolean optimizeOperators;

  public UnifiedTableFactory(boolean optimizeOperators) {
    this.optimizeOperators = optimizeOperators;
  }

  UnifiedOneXOneTable createOneXOne(Queue<Integer> ll) {
    Map<FuzzyValue, FuzzyValue> valtable = new HashMap<>();
    for (int i = 0; i <= 5; i++) {
      valtable.put(FuzzyValue.inOrder.get(i), FuzzyValue.inOrder.get(ll.poll() % 6));
    }
    return new UnifiedOneXOneTable(valtable);
  }

  UnifiedOneXTwoTable createOneXTwo(Queue<Integer> ll) {
    Map<FuzzyValue, FuzzyValue> valtable = new HashMap<>();
    Map<FuzzyValue, FuzzyValue> valtable2 = new HashMap<>();
    for (int i = 0; i <= 5; i++) {
      valtable.put(FuzzyValue.inOrder.get(i), FuzzyValue.inOrder.get(ll.poll() % 6));
    }
    for (int i = 0; i <= 5; i++) {
      valtable2.put(FuzzyValue.inOrder.get(i), FuzzyValue.inOrder.get(ll.poll() % 6));
    }
    return new UnifiedOneXTwoTable(valtable, valtable2);
  }

  UnifiedTwoXOneTable createTwoXOne(Queue<Integer> ll, UnifiedTwoXOneTable orig) {
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable = new EnumMap<>(FuzzyValue.class);
    for (int i = 0; i < 5; i++) {
      EnumMap<FuzzyValue, FuzzyValue> tempRulaTable = new EnumMap<>(FuzzyValue.class);
      for (int q = 0; q < 5; q++) {
        tempRulaTable.put(FuzzyValue.inOrder.get(q), FuzzyValue.inOrder.get(ll.remove() % 6));
      }
      ruleTable.put(FuzzyValue.inOrder.get(i), tempRulaTable);
    }
    Operator op = orig.getOpertaor();
    if (optimizeOperators) {
      op = Operator.values()[ll.poll() % Operator.values().length];
    }
    return new UnifiedTwoXOneTable(ruleTable, op);
  }

  UnifiedTwoXTwoTable createTwoXTwo(Queue<Integer> ll, UnifiedTwoXTwoTable orig) {
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable = new EnumMap<>(FuzzyValue.class);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable2 = new EnumMap<>(FuzzyValue.class);
    for (int i = 0; i < 5; i++) {
      EnumMap<FuzzyValue, FuzzyValue> tempRulaTable = new EnumMap<>(FuzzyValue.class);
      for (int q = 0; q < 5; q++) {
        tempRulaTable.put(FuzzyValue.inOrder.get(q), FuzzyValue.inOrder.get(ll.poll() % 6));
      }
      ruleTable.put(FuzzyValue.inOrder.get(i), tempRulaTable);
    }
    for (int i = 0; i < 5; i++) {
      EnumMap<FuzzyValue, FuzzyValue> tempRulaTable = new EnumMap<>(FuzzyValue.class);
      for (int q = 0; q < 5; q++) {
        tempRulaTable.put(FuzzyValue.inOrder.get(q), FuzzyValue.inOrder.get(ll.poll() % 6));
      }
      ruleTable2.put(FuzzyValue.inOrder.get(i), tempRulaTable);
    }
    Operator op = orig.getOpertaor();
    if (optimizeOperators) {
      op = Operator.values()[ll.poll() % Operator.values().length];
    }
    return new UnifiedTwoXTwoTable(ruleTable, ruleTable2, op);
  }

  IUnifiedTable create(IUnifiedTable oldOne, Queue<Integer> genome) {
    if (oldOne instanceof UnifiedOneXOneTable) {
      return createOneXOne(genome);
    }
    if (oldOne instanceof UnifiedOneXTwoTable) {
      return createOneXTwo(genome);
    }
    if (oldOne instanceof UnifiedTwoXOneTable) {
      return createTwoXOne(genome, (UnifiedTwoXOneTable) oldOne);
    }
    if (oldOne instanceof UnifiedTwoXTwoTable) {
      return createTwoXTwo(genome, (UnifiedTwoXTwoTable) oldOne);
    }
    return null;

  }

  public void putValuesFromTable(IUnifiedTable oldOne, LinkedList<Integer> genome) {
    if (oldOne instanceof UnifiedOneXOneTable) {
      putOneXOne(genome, (UnifiedOneXOneTable) oldOne);
    }
    if (oldOne instanceof UnifiedOneXTwoTable) {
      putOneXTwo(genome, (UnifiedOneXTwoTable) oldOne);
    }
    if (oldOne instanceof UnifiedTwoXOneTable) {
      putTwoXOne(genome, (UnifiedTwoXOneTable) oldOne);
    }
    if (oldOne instanceof UnifiedTwoXTwoTable) {
      putTwoXTwo(genome, (UnifiedTwoXTwoTable) oldOne);
    }
  }

  private void putTwoXTwo(LinkedList<Integer> genome, UnifiedTwoXTwoTable oldOne) {
    Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable = oldOne.getTables().get(0);
    Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable2 = oldOne.getTables().get(1);
    for (int i = 0; i < 5; i++) {
      Map<FuzzyValue, FuzzyValue> tempRulaTable = ruleTable.get(FuzzyValue.inOrder.get(i));
      for (int q = 0; q < 5; q++) {
        FuzzyValue vv = tempRulaTable.get(FuzzyValue.inOrder.get(q));
        genome.add(FuzzyValue.inOrder.indexOf(vv));

      }
    }
    for (int i = 0; i < 5; i++) {
      Map<FuzzyValue, FuzzyValue> tempRulaTable = ruleTable2.get(FuzzyValue.inOrder.get(i));
      for (int q = 0; q < 5; q++) {
        FuzzyValue vv = tempRulaTable.get(FuzzyValue.inOrder.get(q));
        genome.add(FuzzyValue.inOrder.indexOf(vv));
      }
      ruleTable2.put(FuzzyValue.inOrder.get(i), tempRulaTable);
    }
    Operator op = oldOne.getOpertaor();
    if (optimizeOperators) {
      for (int i = 0; i < Operator.values().length; i++) {
        if (Operator.values()[i].equals(op)) {
          genome.add(i);
          break;
        }
      }
    }
  }

  private void putTwoXOne(LinkedList<Integer> genome, UnifiedTwoXOneTable oldOne) {
    Map<FuzzyValue, Map<FuzzyValue, FuzzyValue>> ruleTable = oldOne.getTable();
    for (int i = 0; i < 5; i++) {
      Map<FuzzyValue, FuzzyValue> tempRulaTable = ruleTable.get(FuzzyValue.inOrder.get(i));
      for (int q = 0; q < 5; q++) {
        FuzzyValue vv = tempRulaTable.get(FuzzyValue.inOrder.get(q));
        genome.add(FuzzyValue.inOrder.indexOf(vv));
      }
    }
    Operator op = oldOne.getOpertaor();
    if (optimizeOperators) {
      for (int i = 0; i < Operator.values().length; i++) {
        if (Operator.values()[i].equals(op)) {
          genome.add(i);
          break;
        }
      }
    }
  }

  private IUnifiedTable putOneXTwo(LinkedList<Integer> genome, UnifiedOneXTwoTable oldOne) {
    Map<FuzzyValue, FuzzyValue> valtable = oldOne.getTables().get(0);
    Map<FuzzyValue, FuzzyValue> valtable2 = oldOne.getTables().get(1);
    for (int i = 0; i <= 5; i++) {
      FuzzyValue vv = valtable.get(FuzzyValue.inOrder.get(i));
      genome.add(FuzzyValue.inOrder.indexOf(vv));
    }
    for (int i = 0; i <= 5; i++) {
      FuzzyValue vv = valtable2.get(FuzzyValue.inOrder.get(i));
      genome.add(FuzzyValue.inOrder.indexOf(vv));
    }
    return null;
  }

  private void putOneXOne(LinkedList<Integer> genome, UnifiedOneXOneTable oldOne) {
    Map<FuzzyValue, FuzzyValue> valtable = oldOne.getTable();
    for (int i = 0; i <= 5; i++) {
      FuzzyValue vv = valtable.get(FuzzyValue.inOrder.get(i));
      genome.add(FuzzyValue.inOrder.indexOf(vv));

    }
  }

}
