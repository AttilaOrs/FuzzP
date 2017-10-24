package UnifiedGp.GpIndi.UsageStats;


import java.util.ArrayDeque;
import java.util.Deque;

import UnifiedGp.Tree.DepthFirstPostOrderVisitor;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.ILeaf;
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
import UnifiedGp.Tree.Visitors.UsageStats;

public class StaticSimplifierWithStats {

  private DepthFirstPostOrderVisitor<NodeType> visitor;
  private Deque<INode<NodeType>> newNetStack;
  private UsageStats oldStat;
  private UsageStats newStat;
  private InvalidStatsDeleter cleaner;

  public StaticSimplifierWithStats() {
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
    cleaner = new InvalidStatsDeleter();
  }

  private void visitLeaf(INode<NodeType> node) {
    ILeaf<NodeType> leaf = (ILeaf<NodeType>) node;
    ILeaf<NodeType> i = leaf.myClone();
    newStat.add(i, oldStat.getUsage(node), 0, 1);
    newNetStack.push(i);
  }

  private Boolean selectVisit(INode<NodeType> conc) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.getType().equals(NodeType.Block) && se.getType().equals(NodeType.Block)) {
      putBlockLeaf();
      return Boolean.TRUE;
    }
    /*
     * if (fi.getType().equals(NodeType.Block)) { newNetStack.push(se); return
     * Boolean.TRUE; } if (se.getType().equals(NodeType.Block)) {
     * newNetStack.push(fi); return Boolean.TRUE; }
     */

    notModfied((IInnerNode<NodeType>) conc, fi, se);
    return Boolean.TRUE;
  }

  private Boolean posNeg(INode<NodeType> conc) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.getType().equals(NodeType.Block) && se.getType().equals(NodeType.Block)) {
      putBlockLeaf();
      return Boolean.TRUE;
    }
    notModfied((IInnerNode<NodeType>) conc, fi, se);
    return Boolean.TRUE;
  }

  private Boolean loopVisit(INode<NodeType> conc) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.getType().isBlock() && se.getType().isBlock()) {
      putBlockLeaf();
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
        putBlockLeaf();
        return Boolean.TRUE;
      }
      if (fi.getType().equals(NodeType.Const) && se.getType().equals(NodeType.Const)) {
        double r = (((ConstantLeaf) fi).getConsValue() * ((ConstantLeaf) se).getConsValue());
        putConstant(fi, r);
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
        putBlockLeaf();
        return Boolean.TRUE;
      }
      if (fi.getType().equals(NodeType.Const) && se.getType().equals(NodeType.Const)) {
        double r = (((ConstantLeaf) fi).getConsValue() + ((ConstantLeaf) se).getConsValue());
        putConstant(fi, r);
        return Boolean.TRUE;
      }
    }
    notModfied((IInnerNode<NodeType>) conc, fi, se);
    return Boolean.TRUE;
  }

  private void putConstant(INode<NodeType> fi, double r) {
    ConstantLeaf e = new ConstantLeaf(r);
    newStat.add(e, newStat.getUsage(fi), 0, 1);
    newNetStack.push(e);
  }

  private void putBlockLeaf() {
    BlockLeaf e = new BlockLeaf();
    newNetStack.push(e);
    newStat.add(e, 0, 0, 1);
  }

  private void putLeaf(ILeaf<NodeType> node, int usage) {
    newNetStack.push(node);
    newStat.add(node, usage, 0, 1);
  }

  private Boolean concVisit(INode<NodeType> conc) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.isLeaf() && se.isLeaf()) {
      if (fi.getType().equals(NodeType.Block) && se.getType().equals(NodeType.Block)) {
        putBlockLeaf();
        return Boolean.TRUE;
      }
      if (fi.getType().equals(NodeType.Const) && se.getType().equals(NodeType.Const)) {
        double r = (((ConstantLeaf) fi).getConsValue() + ((ConstantLeaf) se).getConsValue()) / 2.0;
        putConstant(fi, r);
        return Boolean.TRUE;
      }
      if (fi.getType().equals(NodeType.Inv) && se.getType().equals(NodeType.Inv)) {
        putLeaf(new InversionLeaf(), newStat.getUsage(fi));
        return Boolean.TRUE;
      }
      if (fi.getType().equals(NodeType.Negate) && se.getType().equals(NodeType.Negate)) {
        putLeaf(new NegateLeaf(), newStat.getUsage(fi));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Inp) && fi.getType().equals(NodeType.Inp) &&
          (((InputLeaf) fi).getSubtype().equals(((InputLeaf) se).getSubtype()))
          && ((InputLeaf) fi).inpNr() == ((InputLeaf) se).inpNr()) {
        putLeaf(((InputLeaf) fi).myClone(), newStat.getUsage(fi));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Out) && fi.getType().equals(NodeType.Out) &&
          (((OutputLeaf) fi).getSubtype().equals(((OutputLeaf) se).getSubtype()))
          && ((OutputLeaf) fi).outNr() == ((OutputLeaf) se).outNr()) {
        putLeaf(((OutputLeaf) fi).myClone(), newStat.getUsage(fi));
        return Boolean.TRUE;
      }

    }

    notModfied((IInnerNode<NodeType>) conc, fi, se);
    return Boolean.TRUE;
  }

  private Boolean seqVisit(INode<NodeType> node) {
    INode<NodeType> se = newNetStack.pop();
    INode<NodeType> fi = newNetStack.pop();
    if (fi.getType().isBlock() && se.getType().isBlock()) {
      putBlockLeaf();
      return Boolean.TRUE;
    }
    if (se.isLeaf() && fi.isLeaf()) {
      if (se.getType().equals(NodeType.Delay) && fi.getType().equals(NodeType.Delay)) {
        int delay = ((DelayLeaf) se).getDelay() + ((DelayLeaf) fi).getDelay();
        putLeaf(new DelayLeaf(delay), newStat.getUsage(fi));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Memory) && fi.getType().equals(NodeType.Memory)) {
        int memory = ((MemoryLeaf) se).getMemNr() + ((MemoryLeaf) fi).getMemNr();
        putLeaf(new MemoryLeaf(memory), newStat.getUsage(fi));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Const) && fi.getType().equals(NodeType.Const)) {
        double c = ((ConstantLeaf) se).getConsValue();
        putConstant(fi, c);
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Inv) && fi.getType().equals(NodeType.Inv)) {
        putLeaf(new DelayLeaf(0), newStat.getUsage(fi));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Negate) && fi.getType().equals(NodeType.Negate)) {
        putLeaf(new DelayLeaf(0), newStat.getUsage(fi));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Inp) && fi.getType().equals(NodeType.Inp) &&
          (((InputLeaf) fi).getSubtype().equals(((InputLeaf) se).getSubtype()))
          && ((InputLeaf) fi).inpNr() == ((InputLeaf) se).inpNr()) {
        putLeaf(((InputLeaf) fi).myClone(), newStat.getUsage(fi));
        return Boolean.TRUE;
      }
      if (se.getType().equals(NodeType.Out) && fi.getType().equals(NodeType.Out) &&
          (((OutputLeaf) fi).getSubtype().equals(((OutputLeaf) se).getSubtype()))
          && ((OutputLeaf) fi).outNr() == ((OutputLeaf) se).outNr()) {
        putLeaf(((OutputLeaf) fi).myClone(), newStat.getUsage(fi));
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
    if (se.getType().isBlock() && fi.getType().isSeq()) {
      IInnerNode<NodeType> fiSe = (IInnerNode<NodeType>) fi;
      if (fiSe.getSecondChild().getType().isBlock()) {
        newNetStack.push(fi);
        return Boolean.TRUE;
      }
    }
    notModfied((IInnerNode<NodeType>) node, fi, se);
    return Boolean.TRUE;
  }

  private void notModfied(IInnerNode<NodeType> node, INode<NodeType> fi, INode<NodeType> se) {
    IInnerNode<NodeType> myClone = node.myClone(fi, se);

    int firedFi = newStat.getUsage(fi);
    int firedSe = newStat.getUsage(se);
    int fire = (firedFi > firedSe) ? firedFi : firedSe;

    int depthFi = newStat.getDepth(fi);
    int depthSe = newStat.getDepth(se);
    int depth = ((depthFi > depthSe) ? depthFi : depthSe) + 1;

    int sizeFi = newStat.getSize(fi);
    int sizeSe = newStat.getSize(se);
    int size = sizeFi + sizeSe + 1;
    newStat.add(myClone, fire, depth, size);
    newNetStack.push(myClone);
  }

  public IInnerNode<NodeType> simplifyTree(IInnerNode<NodeType> original, UsageStats oldStat) {
    this.oldStat = oldStat;
    this.newStat = new UsageStats(oldStat.getAllTickNr());

    newNetStack = new ArrayDeque<>();
    visitor.visit(original);
    INode<NodeType> rez = newNetStack.pop();
    if (rez.isLeaf()) {
      BlockLeaf se = new BlockLeaf();
      InnerNode newRez = new InnerNode(NodeType.Seq, rez, se);
      newStat.add(se, 0, 0, 1);
      newStat.add(newRez, 1, newStat.getDepth(rez), newStat.getSize(rez) + 2);
      rez = newRez;
    }
    cleaner.clean(rez, newStat);
    return (IInnerNode<NodeType>) rez;
  }

  public UsageStats getNewStat() {
    return newStat;
  }

}
