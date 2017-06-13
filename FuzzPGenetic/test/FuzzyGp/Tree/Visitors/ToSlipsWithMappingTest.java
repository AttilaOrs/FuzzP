package FuzzyGp.Tree.Visitors;

import org.junit.Test;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Parser;
import FuzzyGp.Tree.Visitors.NodeToTransitionMapping;
import FuzzyGp.Tree.Visitors.ToPetriNet;
import FuzzyGp.Tree.Visitors.ToSlipsWithMapping;

public class ToSlipsWithMappingTest {

  @Test
  public void test() {
    String reez = "(* (* o1 (* (& d6 o1 ) d1 ) ) (* o0 (& (& d7 d5 ) (& (& (* d7 (& (& (* d7 (& d7 (& (* o1 d7 ) d7 ) ) ) (* (* (& d6 o1 ) z ) z ) ) (& (* (* d7 (& (* o1 (* d7 (* d7 d7 ) ) ) d7 ) ) (& d7 (& (* o1 (* d7 o0 ) ) d7 ) ) ) (* i-nr-0 v ) ) ) ) (* d7 (& (& (* d7 (& (* (# d7 i-cpe-0 ) (& d7 d7 ) ) (& (* o1 (* d7 o0 ) ) d7 ) ) ) (* d7 (& d7 (& (& d7 (& (* o1 d7 ) d7 ) ) d7 ) ) ) ) z ) ) ) (* d7 (& (& (* d7 (& d7 (& z d7 ) ) ) (* d7 (& d7 (& (* o1 (* (& o1 (* v o1 ) ) (* d7 d7 ) ) ) v ) ) ) ) (& (* (* d7 (& (* o1 (* d7 (* (* d7 o0 ) (* d7 d8 ) ) ) ) d7 ) ) z ) (* d1 z ) ) ) ) ) ) ) )";
    INode rr = Parser.parse(reez);
    ToPetriNet pp = new ToPetriNet(true);
    pp.convert(rr);
    NodeToTransitionMapping map = pp.getMapping();
    ToSlipsWithMapping underTest = new ToSlipsWithMapping(rr, map);
    String k = underTest.getString();
    System.out.println(k);

  }

}
