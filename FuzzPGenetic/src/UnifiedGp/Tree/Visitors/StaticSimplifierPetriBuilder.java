package UnifiedGp.Tree.Visitors;

import java.util.ArrayDeque;
import java.util.Deque;

import UnifiedGp.Tree.DepthFirstPostOrderVisitor;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.VisitorCostumizer;
import UnifiedGp.Tree.Nodes.BlockLeaf;
import UnifiedGp.Tree.Nodes.ConstantLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.InversionLeaf;
import UnifiedGp.Tree.Nodes.MemoryLeaf;
import UnifiedGp.Tree.Nodes.NegateLeaf;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutputLeaf;

public class StaticSimplifierPetriBuilder {

  private DepthFirstPostOrderVisitor<NodeType> visitor;
  private Deque<INode<NodeType>> newNetStack;

  public StaticSimplifierPetriBuilder() {
   VisitorCostumizer<NodeType> costumizer = new VisitorCostumizer<>();
    costumizer.registerPredicatedConsumer(node -> node.isLeaf(), this::visitLeaf);
    costumizer.registerOperatorConsumer(NodeType.Seq, this::seqVisit);
    costumizer.registerOperatorConsumer(NodeType.Conc, this::concVisit);
    costumizer.registerOperatorConsumer(NodeType.Add, this::addVisit);
    costumizer.registerOperatorConsumer(NodeType.Multiply, this::multiplyVisit);
    costumizer.registerOperatorConsumer(NodeType.Loop, this::loopVisit);
    costumizer.registerOperatorConsumer(NodeType.PosNegSplit, this::posNeg);
    costumizer.registerOperatorConsumer(NodeType.Selc, this::selectVisit);
    visitor = new DepthFirstPostOrderVisitor<>(costumizer);
  }

  private void visitLeaf(INode<NodeType> node) {
    newNetStack.push(node);
  }

  private Boolean selectVisit(INode<NodeType> conc) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.getType().equals(NodeType.Block) && se.getType().equals(NodeType.Block)) {
      newNetStack.push(new BlockLeaf());
      return Boolean.TRUE;
    }
    if (fi.getType().equals(NodeType.Block)) {
      newNetStack.push(se);
      return Boolean.TRUE;
    }
    if (se.getType().equals(NodeType.Block)) {
      newNetStack.push(fi);
      return Boolean.TRUE;
    }

    notModfied((IInnerNode<NodeType>) conc, fi, se);
    return Boolean.TRUE;
  }

  private Boolean posNeg(INode<NodeType> conc) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.getType().equals(NodeType.Block) && se.getType().equals(NodeType.Block)) {
      newNetStack.push(new BlockLeaf());
      return Boolean.TRUE;
    }
    notModfied((IInnerNode<NodeType>) conc, fi, se);
    return Boolean.TRUE;
  }

  private Boolean loopVisit(INode<NodeType> conc) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.getType().equals(NodeType.Block) && se.getType().equals(NodeType.Block)) {
      newNetStack.push(new BlockLeaf());
      return Boolean.TRUE;
    }
    if (se.getType().equals(NodeType.Block)) {
      newNetStack.push(fi);
      return Boolean.TRUE;
    }

    notModfied((IInnerNode<NodeType>) conc, fi, se);
    return Boolean.TRUE;
  }

  private Boolean multiplyVisit(INode<NodeType> conc) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();

    if (fi.isLeaf() && se.isLeaf()) {
      if (fi.getType().equals(NodeType.Block) && se.getType().equals(NodeType.Block)) {
        newNetStack.push(new BlockLeaf());
        return Boolean.TRUE;
      }
      if (fi.getType().equals(NodeType.Const) && se.getType().equals(NodeType.Const)) {
        double r = (((ConstantLeaf) fi).getConsValue() * ((ConstantLeaf) se).getConsValue());
        newNetStack.push(new ConstantLeaf(r));
        return Boolean.TRUE;
      }
    }
    notModfied((IInnerNode<NodeType>) conc, fi, se);
    return Boolean.TRUE;
  }

  private Boolean addVisit(INode<NodeType> conc) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();

    if (fi.isLeaf() && se.isLeaf()) {
      if (fi.getType().equals(NodeType.Block) && se.getType().equals(NodeType.Block)) {
        newNetStack.push(new BlockLeaf());
        return Boolean.TRUE;
      }
      if (fi.getType().equals(NodeType.Const) && se.getType().equals(NodeType.Const)) {
        double r = (((ConstantLeaf) fi).getConsValue() + ((ConstantLeaf) se).getConsValue());
        newNetStack.push(new ConstantLeaf(r));
        return Boolean.TRUE;
      }
    }
    notModfied((IInnerNode<NodeType>) conc, fi, se);
    return Boolean.TRUE;
  }

  private Boolean concVisit(INode<NodeType> conc) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.isLeaf() && se.isLeaf()) {
      if (fi.getType().equals(NodeType.Block) && se.getType().equals(NodeType.Block)) {
        newNetStack.push(new BlockLeaf());
        return Boolean.TRUE;
      }
      if (fi.getType().equals(NodeType.Const) && se.getType().equals(NodeType.Const)){
        double r = (((ConstantLeaf) fi).getConsValue() + ((ConstantLeaf) se).getConsValue()) / 2.0;
        newNetStack.push(new ConstantLeaf(r));
        return Boolean.TRUE;
      }
      if (fi.getType().equals(NodeType.Inv) && se.getType().equals(NodeType.Inv)) {
        newNetStack.push(new InversionLeaf());
        return Boolean.TRUE;
      }
      if (fi.getType().equals(NodeType.Negate) && se.getType().equals(NodeType.Negate)) {
        newNetStack.push(new NegateLeaf());
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Inp) && fi.getType().equals(NodeType.Inp) &&
          (((InputLeaf) fi).getSubtype().equals(((InputLeaf) se).getSubtype()))
          && ((InputLeaf) fi).inpNr() == ((InputLeaf) se).inpNr()) {
        newNetStack.push(((InputLeaf) fi).myClone());
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Out) && fi.getType().equals(NodeType.Out) &&
          (((OutputLeaf) fi).getSubtype().equals(((OutputLeaf) se).getSubtype()))
          && ((OutputLeaf) fi).outNr() == ((OutputLeaf) se).outNr()) {
        newNetStack.push(((OutputLeaf) fi).myClone());
        return Boolean.TRUE;
      }

    }

    notModfied((IInnerNode<NodeType>) conc, fi, se);
    return Boolean.TRUE;
  }

  private Boolean seqVisit(INode<NodeType> node) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.getType().equals(NodeType.Block)) {
      newNetStack.push(new BlockLeaf());
      return Boolean.TRUE;
    }
    if (se.isLeaf() && fi.isLeaf()) {
      if (se.getType().equals(NodeType.Delay) && fi.getType().equals(NodeType.Delay)) {
        int delay = ((DelayLeaf)se).getDelay() +  ((DelayLeaf)fi).getDelay() ;
        newNetStack.push(new DelayLeaf(delay));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Memory) && fi.getType().equals(NodeType.Memory)) {
        int memory = ((MemoryLeaf) se).getMemNr() + ((MemoryLeaf) fi).getMemNr();
        newNetStack.push(new MemoryLeaf(memory));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Const) && fi.getType().equals(NodeType.Const)) {
        double c = ((ConstantLeaf) se).getConsValue();
        newNetStack.push(new ConstantLeaf(c));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Inv) && fi.getType().equals(NodeType.Inv)) {
        newNetStack.push(new DelayLeaf(0));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Negate) && fi.getType().equals(NodeType.Negate)) {
        newNetStack.push(new DelayLeaf(0));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Inp) && fi.getType().equals(NodeType.Inp) &&
          (((InputLeaf) fi).getSubtype().equals(((InputLeaf) se).getSubtype()))
          && ((InputLeaf) fi).inpNr() == ((InputLeaf) se).inpNr()) {
        newNetStack.push(((InputLeaf) fi).myClone());
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Inp) && fi.getType().equals(NodeType.Inp) &&
          (((InputLeaf) fi).getSubtype().equals(((InputLeaf) se).getSubtype()))
          && ((InputLeaf) fi).inpNr() == ((InputLeaf) se).inpNr()) {
        newNetStack.push(((InputLeaf) fi).myClone());
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Out) && fi.getType().equals(NodeType.Out) &&
          (((OutputLeaf) fi).getSubtype().equals(((OutputLeaf) se).getSubtype()))
          && ((OutputLeaf) fi).outNr() == ((OutputLeaf) se).outNr()) {
        newNetStack.push(((OutputLeaf) fi).myClone());
        return Boolean.TRUE;
      }
    }
    if (fi.getType().equals(NodeType.Delay) && ((DelayLeaf) fi).getDelay() == 0) {
      newNetStack.push(se);
      return Boolean.TRUE;
    }
    if (se.getType().equals(NodeType.Delay) && ((DelayLeaf) se).getDelay() == 0) {
      newNetStack.push(fi);
      return Boolean.TRUE;
    }
    notModfied((IInnerNode<NodeType>) node, fi, se);
    return Boolean.TRUE;
  }

  private void notModfied(IInnerNode<NodeType> node, INode<NodeType> fi, INode<NodeType> se) {
    newNetStack.push(node.myClone(fi, se));
  }



  public IInnerNode<NodeType> simplifyTree(IInnerNode<NodeType> original) {
    newNetStack = new ArrayDeque<>();
    visitor.visit(original);
    INode<NodeType> rez = newNetStack.pop();
    if (rez.isLeaf()) {
      rez = new InnerNode(NodeType.Seq, rez, new BlockLeaf());
    }

    return (IInnerNode<NodeType>) rez;
  }
}
