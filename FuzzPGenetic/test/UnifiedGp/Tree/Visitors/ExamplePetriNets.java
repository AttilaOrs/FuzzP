package UnifiedGp.Tree.Visitors;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.TestUtils;
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
import UnifiedPetriGA.controller.TableToLatex;
import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.DrawableUnifiedPetriNet;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;
import dotDrawer.PetriDotDrawerVertical;

public class ExamplePetriNets {

  private ToPetriNet toNet;

  @Before
  public void before() {
    toNet = new ToPetriNet(TestUtils.simpleScaleProvider(2.0), true);
  }

  public void firstSimpleExample() {
    InputLeaf i0 = new InputLeaf(InputType.ReaderBlocking, 0);
    InputLeaf i1 = new InputLeaf(InputType.ReaderBlocking, 1);
    InnerNode add = new InnerNode(NodeType.Add, i0, i1);
    OutputLeaf o0 = new OutputLeaf(0, OutType.Copy);
    InnerNode seq = new InnerNode(NodeType.Seq, add, o0);
    DelayLeaf d0 = new DelayLeaf(1);
    InnerNode loop = new InnerNode(NodeType.Loop, seq, d0);
    System.out.println(loop.toString());
    PetriConversationResult ll = toNet.toNet(loop);
    
    PetriDotDrawerVertical v = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(ll.net, false, TransitionPlaceNameStore.createOrdinarNames(ll.net)));
    
    // v.makeImage("fiEx");
  }

  public void secondExample() {

    InputLeaf i0 = new InputLeaf(InputType.ReaderBlocking, 0);
    MemoryLeaf m = new MemoryLeaf(2);
    InnerNode seq = new InnerNode(NodeType.Seq, i0, m);
    ConstantLeaf cc = new ConstantLeaf(0.42);
    InnerNode multi = new InnerNode(NodeType.Multiply, seq, cc);
    InnerNode seq2 = new InnerNode(NodeType.Seq, new InputLeaf(InputType.EnableIfNonPhi, 1), multi);
    InnerNode loop = new InnerNode(NodeType.Loop, seq2, new OutputLeaf(0, OutType.Copy));

    System.out.println(loop.toString());
    PetriConversationResult ll = toNet.toNet(loop);

    PetriDotDrawerVertical v = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(ll.net, false, TransitionPlaceNameStore.createOrdinarNames(ll.net)));
    
    // v.makeImage("seEx");
  }

  public void thirdExample() {
    InputLeaf i = new InputLeaf(InputType.ShiftDown, 0);
    DelayLeaf d = new DelayLeaf(1);
    InnerNode conc = new InnerNode(NodeType.Conc, i, d);
    NegateLeaf negate = new NegateLeaf();
    InnerNode seq = new InnerNode(NodeType.Seq, conc, negate);
    InnerNode loop = new InnerNode(NodeType.Loop, seq, new OutputLeaf(0, OutType.Copy));

    System.out.println(loop.toString());
    PetriConversationResult ll = toNet.toNet(loop);

    PetriDotDrawerVertical v = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(ll.net, false, TransitionPlaceNameStore.createOrdinarNames(ll.net)));


    // v.makeImage("thEx");


  }

  public void inputExamples() {
    InputLeaf i = new InputLeaf(InputType.ReaderBlocking, 0);
    InputLeaf i2 = new InputLeaf(InputType.ReaderBlocking, 0);
    InnerNode multi = new InnerNode(NodeType.Multiply, i, i2);

    InnerNode loop = new InnerNode(NodeType.Loop, multi, new OutputLeaf(0, OutType.Copy));

    System.out.println(loop.toString());
    PetriConversationResult ll = toNet.toNet(loop);

    PetriDotDrawerVertical v = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(ll.net, false, TransitionPlaceNameStore.createOrdinarNames(ll.net)));

    IUnifiedTable m = ll.net.getTableForTransition(5);
    TableToLatex.saveTableLatex((UnifiedTwoXTwoTable) m, "inpT5.txt", true);


    // v.makeImage("inpExample");
  }

  public void inputExamplesTwo() {

    InputLeaf i = new InputLeaf(InputType.ReaderBlocking, 0);
    InputLeaf i2 = new InputLeaf(InputType.ReaderBlocking, 0);
    InnerNode multi = new InnerNode(NodeType.Multiply, i, i2);
    InputLeaf i3 = new InputLeaf(InputType.ReaderBlocking, 0);
    InnerNode add = new InnerNode(NodeType.Add, multi, i3);

    System.out.println(add.toString());
    PetriConversationResult ll = toNet.toNet(add);

    PetriDotDrawerVertical v = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(ll.net, false, TransitionPlaceNameStore.createOrdinarNames(ll.net)));
  }

  public void inputLatex() {
    TableToLatex.saveTabeLatex(InputType.EnableIfNonPhi.table, "enableIfNonPhi.txt", true);

  }

  public void ouputFirstExample() {

    InputLeaf i = new InputLeaf(InputType.ReaderBlocking, 0);
    InnerNode seq1 = new InnerNode(NodeType.Seq, new ConstantLeaf(2.5), new OutputLeaf(1, OutType.Copy));
    InnerNode seq2 = new InnerNode(NodeType.Seq, new ConstantLeaf(-2.5), new OutputLeaf(1, OutType.Copy));
    InnerNode pozNeg = new InnerNode(NodeType.PosNegSplit, seq1, seq2);
    InnerNode ss = new InnerNode(NodeType.Seq, i, pozNeg);

    System.out.println(ss.toString());
    PetriConversationResult ll = toNet.toNet(ss);

    PetriDotDrawerVertical v = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(ll.net, false, TransitionPlaceNameStore.createOrdinarNames(ll.net)));


  }
  
  public void constantExample() {
    ConstantLeaf c = new ConstantLeaf(3.5);
    OutputLeaf o = new OutputLeaf(0, OutType.Copy);
    DelayLeaf d = new DelayLeaf(1);
    
    InnerNode seq = new InnerNode(NodeType.Seq, c, o);
    InnerNode loop = new InnerNode(NodeType.Loop, seq, d);

    System.out.println(loop.toString());
    PetriConversationResult ll = toNet.toNet(loop);

    PetriDotDrawerVertical v = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(ll.net, false, TransitionPlaceNameStore.createOrdinarNames(ll.net)));
  }

  public void memoryExample() {
    MemoryLeaf m = new MemoryLeaf(3);
    InputLeaf i = new InputLeaf(InputType.ReaderBlocking, 0);

    InnerNode seq = new InnerNode(NodeType.Seq, i, m);
    InnerNode seq2 = new InnerNode(NodeType.Seq, seq, new OutputLeaf(0, OutType.Copy));

    InnerNode loop = new InnerNode(NodeType.Loop, seq2, new DelayLeaf(1));

    System.out.println(loop.toString());
    PetriConversationResult ll = toNet.toNet(loop);

    PetriDotDrawerVertical v = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(ll.net, false, TransitionPlaceNameStore.createOrdinarNames(ll.net)));

    // v.makeImage("memoryExample");


  }

  public void inversionExample() {
    InputLeaf i = new InputLeaf(InputType.ReaderBlocking, 0);
    InversionLeaf inv = new InversionLeaf();

    InnerNode seq = new InnerNode(NodeType.Seq, i, inv);
    InnerNode seq2 = new InnerNode(NodeType.Seq, seq, new OutputLeaf(0, OutType.Copy));


    System.out.println(seq2.toString());
    PetriConversationResult ll = toNet.toNet(seq2);

    PetriDotDrawerVertical v = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(ll.net, false, TransitionPlaceNameStore.createOrdinarNames(ll.net)));


  }

  public void selectionExample() {
    InputLeaf i = new InputLeaf(InputType.EnableIfNonPhi, 0);
    InputLeaf i2 = new InputLeaf(InputType.EnableIfNonPhi, 1);

    InnerNode seq1 = new InnerNode(NodeType.Seq, i, new OutputLeaf(1, OutType.Copy));
    InnerNode seq2 = new InnerNode(NodeType.Seq, i2, new OutputLeaf(0, OutType.Copy));

    InnerNode slec = new InnerNode(NodeType.Selc, seq1, seq2);

    InnerNode loop = new InnerNode(NodeType.Loop, slec, new DelayLeaf(1));

    System.out.println(loop.toString());
    PetriConversationResult ll = toNet.toNet(loop);

    PetriDotDrawerVertical v = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(ll.net, false, TransitionPlaceNameStore.createOrdinarNames(ll.net)));

    v.makeImage("selectionExample");
  }

  @Test
  public void posNegSplit() {
    InputLeaf i = new InputLeaf(InputType.ReaderBlocking, 0);
    InnerNode split = new InnerNode(NodeType.PosNegSplit, new OutputLeaf(0, OutType.Copy),
        new OutputLeaf(1, OutType.Copy));
    InnerNode seq = new InnerNode(NodeType.Seq, i, split);

    PetriConversationResult ll = toNet.toNet(seq);
    IUnifiedTable tt = ll.net.getTableForTransition(2);
    TableToLatex.saveTabeLatex((UnifiedTwoXOneTable) tt, "posNegEnd.txt", true);

    PetriDotDrawerVertical v = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(ll.net, false, TransitionPlaceNameStore.createOrdinarNames(ll.net)));

  }

}
