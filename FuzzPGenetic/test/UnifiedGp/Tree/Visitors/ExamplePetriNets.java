package UnifiedGp.Tree.Visitors;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.TestUtils;
import UnifiedGp.Tree.Nodes.ConstantLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.InputType;
import UnifiedGp.Tree.Nodes.MemoryLeaf;
import UnifiedGp.Tree.Nodes.NegateLeaf;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutType;
import UnifiedGp.Tree.Nodes.OutputLeaf;
import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.DrawableUnifiedPetriNet;
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

  @Test
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

    v.makeImage("thEx");


  }

}
