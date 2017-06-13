package FuzzyGp.Tree.Visitors;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Parser;
import FuzzyGp.Tree.Nodes.Operator;
import FuzzyGp.Tree.Nodes.OperatorType;
import FuzzyGp.Tree.Visitors.RandomNodeSelector;

public class RandomOperatorSelectorTest {

  @Test
  public void test() {
    INode node = Parser.parse("(# (+ i0 d2) (& (* o1 o0) (# i0 i7) ) )");
    RandomNodeSelector randomSelector = new RandomNodeSelector(false, false);
    HashMap<OperatorType, Integer> map = new HashMap<OperatorType, Integer>();
    for (int i = 0; i < 100; i++) {
      Operator oo = (Operator) randomSelector.randomNode(node, new Random());
      if (!map.containsKey(oo.type)) {
        map.put(oo.type, 1);
      } else {
        map.put(oo.type, map.get(oo.type));
      }
    }
    assertTrue(map.get(OperatorType.CONC) > 0);
    assertTrue(map.get(OperatorType.SEQ) > 0);
    assertTrue(map.get(OperatorType.SELCT) > 0);
    assertTrue(map.get(OperatorType.LOOP) > 0);
  }

}
