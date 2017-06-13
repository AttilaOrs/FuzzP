package FuzzyGp.Tree.Visitors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import FuzzyGp.Tree.Nodes.DelayLeaf;
import FuzzyGp.Tree.Nodes.InputLeaf;
import FuzzyGp.Tree.Nodes.InputType;
import FuzzyGp.Tree.Nodes.Operator;
import FuzzyGp.Tree.Nodes.OperatorType;
import FuzzyGp.Tree.Nodes.OutLeaf;
import FuzzyGp.Tree.Nodes.OutType;
import FuzzyGp.Tree.Visitors.ToSlispExpression;

public class ToSlispExpressionTest {

  @Test
  public void loTesty() {
    InputLeaf inp = new InputLeaf(0);
    OutLeaf out = new OutLeaf(0);
    DelayLeaf delay = new DelayLeaf(0);

    InputLeaf inp1 = new InputLeaf(1);
    DelayLeaf dela1 = new DelayLeaf(1);

    Operator oo1 = new Operator(OperatorType.SEQ, inp, out);
    Operator oo2 = new Operator(OperatorType.SELCT, oo1, delay);
    Operator oo3 = new Operator(OperatorType.LOOP, inp1, dela1);
    Operator oo4 = new Operator(OperatorType.CONC, oo3, oo2);

    String lisp = (new ToSlispExpression(oo4)).getString();

    assertEquals("(& (# i1 d1 ) (+ (* i0 o0 ) d0 ) )", lisp);

  }

  @Test
  public void diferentInptType() {
    InputLeaf inp = new InputLeaf(0, InputType.CNE);
    OutLeaf out = new OutLeaf(0, OutType.WZ);
    DelayLeaf delay = new DelayLeaf(0);

    InputLeaf inp1 = new InputLeaf(1, InputType.NR);
    DelayLeaf dela1 = new DelayLeaf(1);

    Operator oo1 = new Operator(OperatorType.SEQ, inp, out);
    Operator oo2 = new Operator(OperatorType.SELCT, oo1, delay);
    Operator oo3 = new Operator(OperatorType.LOOP, inp1, dela1);
    Operator oo4 = new Operator(OperatorType.CONC, oo3, oo2);

    String lisp = (new ToSlispExpression(oo4)).getString();

    assertEquals("(& (# i-nr-1 d1 ) (+ (* i-cne-0 o-wz-0 ) d0 ) )", lisp);

  }

}
