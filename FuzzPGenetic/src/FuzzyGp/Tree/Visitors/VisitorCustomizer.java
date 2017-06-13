package FuzzyGp.Tree.Visitors;

import FuzzyGp.Tree.Nodes.DelayLeaf;
import FuzzyGp.Tree.Nodes.FullRuleLeaf;
import FuzzyGp.Tree.Nodes.ILeaf;
import FuzzyGp.Tree.Nodes.InputLeaf;
import FuzzyGp.Tree.Nodes.InversionLeaf;
import FuzzyGp.Tree.Nodes.Operator;
import FuzzyGp.Tree.Nodes.OutLeaf;
import FuzzyGp.Tree.Nodes.ZeroEventInput;

public interface VisitorCustomizer {

  default void visitLeaf(ILeaf node) {
  };

  default void visitOperator(Operator oo) {
  };

  default void visitDelayLeaf(DelayLeaf node) {
  };

  default void visitOutLeaf(OutLeaf node) {
  };

  default void visitInpLeaf(InputLeaf node) {
  };

  default void visitConc(Operator oo) {
  };

  default void visitSelect(Operator oo) {
  };

  default void visitSeq(Operator oo) {
  };

  default void visitLoop(Operator oo) {
  };

  default void visitZeroEventInput(ZeroEventInput node) {
  };

  default void visitInversionLeaf(InversionLeaf node) {
  };

  default void visitFullRuleLeaf(FullRuleLeaf node) {
  };
}
