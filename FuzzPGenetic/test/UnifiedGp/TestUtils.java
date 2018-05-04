package UnifiedGp;

import java.util.function.Predicate;

import UnifiedGp.Tree.BreadthFirstVisitor;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.VisitorCostumizer;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.InputType;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutType;
import UnifiedGp.Tree.Nodes.OutputLeaf;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.DrawableUnifiedPetriNetWithExternalNames;
import dotDrawer.PetriDotDrawerVertical;

public class TestUtils {

  private static int cntr = 0;

  public static int countNode(IInnerNode<NodeType> root, Predicate<INode<NodeType>> pred) {
    cntr = 0;
    VisitorCostumizer<NodeType> costum = new VisitorCostumizer<>();
    costum.registerPredicatedConsumer(pred, node -> cntr++);
    BreadthFirstVisitor<NodeType> visitor = new BreadthFirstVisitor<>(costum);
    visitor.visit(root);
    return cntr;
  }

  public static void draw(IInnerNode<NodeType> node, String name) {
    ToPetriNet toNet = new ToPetriNet(simpleScaleProvider(2.0));
    PetriConversationResult rez = toNet.toNet(node);

    PetriDotDrawerVertical drawer = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNetWithExternalNames(rez.net, TransitionPlaceNameStore.createOrdinarNames(rez.net)));
    drawer.makeImage(name);

  }

  public static ScaleProvider simpleScaleProvider(Double d) {
    return new ScaleProvider() {

      @Override
      public Double getScaleForOut(int inpNr) {
        return d;
      }

      @Override
      public Double getScaleForInp(int inpNr) {
        return d;
      }

      @Override
      public double defaultScale() {
        return d;
      }
    };
  }

  public static IInnerNode<NodeType> simpleSeq() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    InnerNode seq = new InnerNode(NodeType.Seq, d1, d2);
    return seq;
  }

  public static IInnerNode<NodeType> allSimpleOps() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    DelayLeaf d3 = new DelayLeaf(3);
    DelayLeaf d4 = new DelayLeaf(4);
    DelayLeaf d5 = new DelayLeaf(4);
    InnerNode seq = new InnerNode(NodeType.Seq, d1, d2);
    InnerNode select = new InnerNode(NodeType.Selc, d3, d4);
    InnerNode conc = new InnerNode(NodeType.Conc, seq, select);
    InnerNode loop = new InnerNode(NodeType.Loop, conc, d5);
    return loop;
  }
  
  public static IInnerNode<NodeType> allSimpleModified() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    DelayLeaf d3 = new DelayLeaf(3);
    DelayLeaf d4 = new DelayLeaf(4);
    DelayLeaf d5 = new DelayLeaf(4);
    InnerNode seq = new InnerNode(NodeType.Seq, d3, d4);
    InnerNode select = new InnerNode(NodeType.Selc, d1, d2);
    InnerNode conc = new InnerNode(NodeType.Conc, seq, select);
    InnerNode loop = new InnerNode(NodeType.Loop, conc, d5);
    return loop;
  }
  
  public static IInnerNode<NodeType> loopLoopOne() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    DelayLeaf d3 = new DelayLeaf(3);
    InnerNode loopInner =  new InnerNode(NodeType.Loop, d1, d2);
    InnerNode loop = new InnerNode(NodeType.Loop, loopInner, d3);
    return loop;
  }
  
  public static IInnerNode<NodeType> concLoopOne() {
    DelayLeaf d1 = new DelayLeaf(1);
    DelayLeaf d2 = new DelayLeaf(2);
    DelayLeaf d3 = new DelayLeaf(3);
    InnerNode loopInner =  new InnerNode(NodeType.Loop, d1, d2);
    InnerNode loop = new InnerNode(NodeType.Conc, loopInner, d3);
    return loop;
  }

  public static IInnerNode<NodeType> selectionNet() {
    InnerNode seq1 = new InnerNode(NodeType.Seq, new InputLeaf(InputType.EnableIfNonPhi, 0),
        new OutputLeaf(0, OutType.Copy));

    InnerNode seq2 = new InnerNode(NodeType.Seq, new InputLeaf(InputType.EnableIfNonPhi, 1),
        new OutputLeaf(1, OutType.Copy));
    InnerNode selc = new InnerNode(NodeType.Selc, seq1, seq2);
    InnerNode loop = new InnerNode(NodeType.Loop, selc, new DelayLeaf(1));
    return loop;

  }
}
