package UETPNLisp;

import java.util.LinkedList;
import java.util.Queue;

import org.antlr.v4.runtime.tree.ParseTree;

import UETPNLisp.gen.UETPNLispBaseVisitor;
import UETPNLisp.gen.UETPNLispParser;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.BlockLeaf;
import UnifiedGp.Tree.Nodes.ConstantLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.InputType;
import UnifiedGp.Tree.Nodes.InversionLeaf;
import UnifiedGp.Tree.Nodes.MemoryLeaf;
import UnifiedGp.Tree.Nodes.NegateLeaf;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutType;
import UnifiedGp.Tree.Nodes.OutputLeaf;

public class Visitor extends UETPNLispBaseVisitor<Boolean> {

  private InputType lastInpType = null;
  private OutType lastOutType = null;
  private Integer lastNumber = null;
  private Double lastDouble = null;
  Queue<INode<NodeType>> queue;



  INode<NodeType> getRoot() {
    return builtUpTree();
  }

  private INode<NodeType> builtUpTree() {
    INode<NodeType> i = queue.poll();
    if (!i.isLeaf() ) {
      INode<NodeType> fi = builtUpTree();
      INode<NodeType> se = builtUpTree();
      return new InnerNode(i.getType(), fi, se);
    }
    return i;
  }

  @Override
  public Boolean visit(ParseTree tr) {
    queue = new LinkedList<>();
    return super.visit(tr);
  }


  @Override
  public Boolean visitConst(UETPNLispParser.ConstContext ctx) {
    visitChildren(ctx);
    queue.add(new ConstantLeaf(lastDouble));
    lastDouble = null;
    return false;
  }

  @Override
  public Boolean visitSeq(UETPNLispParser.SeqContext ctx) {
    visitChildren(ctx);
    addOperator(NodeType.Seq);
    return false;
  }

  @Override
  public Boolean visitConc(UETPNLispParser.ConcContext ctx) {
    visitChildren(ctx);
    addOperator(NodeType.Conc);
    return false;
  }

  @Override
  public Boolean visitSelc(UETPNLispParser.SelcContext ctx) {
    visitChildren(ctx);
    addOperator(NodeType.Selc);
    return true;
  }

  @Override
  public Boolean visitLoop(UETPNLispParser.LoopContext ctx) {
    visitChildren(ctx);
    addOperator(NodeType.Loop);
    return true;
  }

  @Override
  public Boolean visitAdd(UETPNLispParser.AddContext ctx) {
    visitChildren(ctx);
    addOperator(NodeType.Add);
    return true;
  }

  @Override
  public Boolean visitMulti(UETPNLispParser.MultiContext ctx) {
    visitChildren(ctx);
    addOperator(NodeType.Multiply);
    return true;
  }


  @Override
  public Boolean visitDelay(UETPNLispParser.DelayContext ctx) {
    visitChildren(ctx);
    queue.add(new DelayLeaf(lastNumber));
    lastNumber = null;
    return true;
  }

  @Override
  public Boolean visitOutput(UETPNLispParser.OutputContext ctx) {
    visitChildren(ctx);
    queue.add(new OutputLeaf(lastNumber, lastOutType));
    lastNumber = null;
    lastOutType = null;
    return true;
  }

  @Override
  public Boolean visitBlock(UETPNLispParser.BlockContext ctx) {
    queue.add(new BlockLeaf());
    return false;
  }

  @Override
  public Boolean visitMemory(UETPNLispParser.MemoryContext ctx) {
    visitChildren(ctx);
    queue.add(new MemoryLeaf(lastNumber));
    lastNumber = null;
    return false;
  }
  @Override
  public Boolean visitPosNegSplit(UETPNLispParser.PosNegSplitContext ctx) {
    visitChildren(ctx);
    addOperator(NodeType.PosNegSplit);
    return true;
  }

  private void addOperator(NodeType type) {
    queue.add(new InnerNode(type, null, null));
  }

  @Override
  public Boolean visitInv(UETPNLispParser.InvContext ctx) {
    queue.add(new InversionLeaf());
    return true;
  }

  @Override
  public Boolean visitNegate(UETPNLispParser.NegateContext ctx) {
    visitChildren(ctx);
    queue.add(new NegateLeaf());
    return true;
  }

  @Override
  public Boolean visitPoz_neg_double(UETPNLispParser.Poz_neg_doubleContext ctx) {
    lastDouble = Double.parseDouble(ctx.getText());
    return true;
  }


  @Override
  public Boolean visitCopyOutType(UETPNLispParser.CopyOutTypeContext ctx) {
    visitChildren(ctx);
    lastOutType = OutType.Copy;
    return true;
  }

  @Override
  public Boolean visitInput(UETPNLispParser.InputContext ctx) {
    visitChildren(ctx);
    queue.add(new InputLeaf(lastInpType, lastNumber));
    lastInpType = null;
    lastNumber = null;
    return false;
  }

  @Override
  public Boolean visitNr(UETPNLispParser.NrContext ctx) {
    visitChildren(ctx);
    lastNumber = Integer.parseInt(ctx.getText());
    return true;
  }
  @Override
  public Boolean visitBlockingReader(UETPNLispParser.BlockingReaderContext ctx) {
    visitChildren(ctx);
    lastInpType = InputType.ReaderBlocking;
    return true;
  }

  @Override
  public Boolean visitNonblockingReader(UETPNLispParser.NonblockingReaderContext ctx) {
    visitChildren(ctx);
    lastInpType = InputType.ReaderNonBlocking;
    return true;
  }

  @Override
  public Boolean visitEnableIfPhi(UETPNLispParser.EnableIfPhiContext ctx) {
    visitChildren(ctx);
    lastInpType = InputType.EnableIfPhi;
    return true;

  }

  @Override
  public Boolean visitEnableIfNotPhi(UETPNLispParser.EnableIfNotPhiContext ctx) {
    visitChildren(ctx);
    lastInpType = InputType.EnableIfNonPhi;
    return true;
  }

  @Override
  public Boolean visitShiftUpIfEventTable(UETPNLispParser.ShiftUpIfEventTableContext ctx) {
    visitChildren(ctx);
    lastInpType = InputType.ShiftUp;
    return true;
  }

  @Override
  public Boolean visitShiftDownIfEventTable(UETPNLispParser.ShiftDownIfEventTableContext ctx) {
    visitChildren(ctx);
    lastInpType = InputType.ShiftDown;
    return true;
  }
}
