package FuzzyGp.Tree.Visitors;

import java.util.ArrayDeque;
import java.util.HashMap;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Nodes.DelayLeaf;
import FuzzyGp.Tree.Nodes.FullRuleLeaf;
import FuzzyGp.Tree.Nodes.ILeaf;
import FuzzyGp.Tree.Nodes.InputLeaf;
import FuzzyGp.Tree.Nodes.InputType;
import FuzzyGp.Tree.Nodes.InversionLeaf;
import FuzzyGp.Tree.Nodes.Operator;
import FuzzyGp.Tree.Nodes.OutLeaf;
import FuzzyGp.Tree.Nodes.OutType;
import FuzzyGp.Tree.Nodes.ZeroEventInput;

public class ToSlispExpression implements VisitorCustomizer {

  public static String convertToSlisp(INode node) {
    return (new ToSlispExpression(node)).getString();
  }

  public static final HashMap<Class<? extends ILeaf>, String> leafStarters;

  static {
    leafStarters = new HashMap<>();
    leafStarters.put(InputLeaf.class, "i");
    leafStarters.put(OutLeaf.class, "o");
    leafStarters.put(DelayLeaf.class, "d");
    leafStarters.put(ZeroEventInput.class, "z");
    leafStarters.put(InversionLeaf.class, "v");
    leafStarters.put(FullRuleLeaf.class, "f");
  }

  public static final String leafSeparator = "-";

  ArrayDeque<String> stack;

  public ToSlispExpression(INode node) {
    stack = new ArrayDeque<>();
    DepthFirstVisitorRunner depth = new DepthFirstVisitorRunner(this);
    depth.visit(node);
  }

  public String getString() {
    return stack.pop();
  }

  @Override
  public void visitDelayLeaf(DelayLeaf node) {
    stack.push(leafStarters.get(node.getClass()) + node.getDelay());
  }

  @Override
  public void visitOutLeaf(OutLeaf node) {
    if (node.getOutType() == OutType.defultOutType) {
      stack.push(leafStarters.get(node.getClass()) + node.getOutNr());
    } else {
      stack.push(leafStarters.get(node.getClass()) + leafSeparator + node.getOutType().typeNameInSlisp + leafSeparator
          + node.getOutNr());
    }
  }

  @Override
  public void visitInpLeaf(InputLeaf node) {
    if (node.getInputType() == InputType.defultInputType) {
      stack.push(leafStarters.get(node.getClass()) + node.getInpNr());
    } else {
      stack.push(leafStarters.get(node.getClass()) + leafSeparator + node.getInputType().typeNameInSlisp + leafSeparator
          + node.getInpNr());
    }

  }

  @Override
  public void visitFullRuleLeaf(FullRuleLeaf node) {
    stack.push(leafStarters.get(node.getClass()) + leafSeparator + node.getInp() + leafSeparator + node.getOut());
  }

  @Override
  public void visitZeroEventInput(ZeroEventInput node) {
    stack.push(leafStarters.get(node.getClass()));
  }

  @Override
  public void visitOperator(Operator oo) {
    String secondChStr = stack.pop();
    String firstChStr = stack.pop();
    String myStr = oo.type.OpReprezantaion;
    stack.push("(" + myStr + " " + firstChStr + " " + secondChStr + " )");
  }

  @Override
  public void visitInversionLeaf(InversionLeaf node) {
    stack.push(leafStarters.get(node.getClass()));
  }

}
