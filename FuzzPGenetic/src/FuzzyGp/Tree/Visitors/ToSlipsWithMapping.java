package FuzzyGp.Tree.Visitors;

import static FuzzyGp.Tree.Visitors.ToSlispExpression.leafSeparator;
import static FuzzyGp.Tree.Visitors.ToSlispExpression.leafStarters;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.stream.Collectors;

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

public class ToSlipsWithMapping implements VisitorCustomizer {

  ArrayDeque<String> stack;
  NodeToTransitionMapping mapping;

  public ToSlipsWithMapping(INode node, NodeToTransitionMapping mapping) {
    this.mapping = mapping;
    stack = new ArrayDeque<>();
    DepthFirstVisitorRunner depth = new DepthFirstVisitorRunner(this);
    depth.visit(node);
  }

  public String getString() {
    String ll = stack.pop();
    System.out.println(ll);
    return ll;
  }

  @Override
  public void visitDelayLeaf(DelayLeaf node) {
    String original = leafStarters.get(node.getClass()) + node.getDelay();
    stack.push(makeLeafStr(node, original));
  }

  @Override
  public void visitOutLeaf(OutLeaf node) {
    String original = "";
    if (node.getOutType() == OutType.defultOutType) {
      original = leafStarters.get(node.getClass()) + node.getOutNr();
    } else {
      original = leafStarters.get(node.getClass()) + leafSeparator + node.getOutType().typeNameInSlisp + leafSeparator
          + node.getOutNr();
    }
    stack.push(makeLeafStr(node, original));
  }

  @Override
  public void visitInpLeaf(InputLeaf node) {
    String original = "";
    if (node.getInputType() == InputType.defultInputType) {
      original = leafStarters.get(node.getClass()) + node.getInpNr();
    } else {
      original = leafStarters.get(node.getClass()) + leafSeparator + node.getInputType().typeNameInSlisp + leafSeparator
          + node.getInpNr();
    }
    stack.push(makeLeafStr(node, original));
  }

  @Override
  public void visitFullRuleLeaf(FullRuleLeaf node) {
    String original = leafStarters.get(node.getClass()) + leafSeparator + node.getInp() + leafSeparator + node.getOut();
    stack.push(makeLeafStr(node, original));
  }

  @Override
  public void visitZeroEventInput(ZeroEventInput node) {
    String original = leafStarters.get(node.getClass());
    stack.push(makeLeafStr(node, original));
  }

  @Override
  public void visitOperator(Operator oo) {
    String secondChStr = stack.pop().replace("@", "@ ");
    String firstChStr = stack.pop().replace("@", "@ ");
    String myStr = oo.type.OpReprezantaion;
    if (mapping.getMyTransitions(oo) != null) {
      String maped = Arrays.stream(mapping.getMyTransitions(oo)).mapToObj(i -> String.valueOf(i))
          .collect(Collectors.joining(","));
      myStr += " #" + maped;

    }
    stack.push("@(" + myStr + "\n" + firstChStr + "\n" + secondChStr + "\n@)");
  }

  @Override
  public void visitInversionLeaf(InversionLeaf node) {
    String original = leafStarters.get(node.getClass());
    stack.push(makeLeafStr(node, original));
  }

  private String makeLeafStr(ILeaf node, String original) {
    return "@" + original + " #" + mapping.getMyTransitions(node)[0];
  }
}
