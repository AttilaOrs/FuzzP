package FuzzyGp.Tree.Visitors;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Nodes.DelayLeaf;
import FuzzyGp.Tree.Nodes.FullRuleLeaf;
import FuzzyGp.Tree.Nodes.ILeaf;
import FuzzyGp.Tree.Nodes.InputLeaf;
import FuzzyGp.Tree.Nodes.InversionLeaf;
import FuzzyGp.Tree.Nodes.Operator;
import FuzzyGp.Tree.Nodes.OutLeaf;
import FuzzyGp.Tree.Nodes.ZeroEventInput;

public class BreadthFirstVisitorRunner {

  private VisitorCustomizer visitor;

  public BreadthFirstVisitorRunner(VisitorCustomizer v) {
    this.visitor = v;
  }

  public void visit(INode node) {
    if (node instanceof Operator) {
      Operator oo = (Operator) node;
      visitor.visitOperator(oo);
      switch (oo.type) {
      case LOOP:
        visitor.visitLoop(oo);
        break;
      case SEQ:
        visitor.visitSeq(oo);
        break;
      case SELCT:
        visitor.visitSelect(oo);
        break;
      case CONC:
        visitor.visitConc(oo);
      }
      visit(oo.firstChildern);
      visit(oo.secondChildren);
    } else {
      visitor.visitLeaf((ILeaf) node);
      if (node instanceof InputLeaf) {
        visitor.visitInpLeaf((InputLeaf) node);
      }

      if (node instanceof OutLeaf) {
        visitor.visitOutLeaf((OutLeaf) node);
      }

      if (node instanceof DelayLeaf) {
        visitor.visitDelayLeaf((DelayLeaf) node);
      }

      if (node instanceof ZeroEventInput) {
        visitor.visitZeroEventInput((ZeroEventInput) node);
      }

      if (node instanceof InversionLeaf) {
        visitor.visitInversionLeaf((InversionLeaf) node);
      }
      if (node instanceof FullRuleLeaf) {
        visitor.visitFullRuleLeaf((FullRuleLeaf) node);
      }
    }

  }
}
