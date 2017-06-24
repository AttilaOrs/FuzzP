package UnifiedGp.GpIndi;

import java.util.Stack;

import UnifiedGp.Tree.DepthFirstPostOrderVisitor;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.VisitorCostumizer;
import UnifiedGp.Tree.Nodes.NodeType;
import structure.GPIndividSize;
import structure.IGPGreature;

public class UnifiedGpIndi implements IGPGreature {

  public final IInnerNode<NodeType> root;
  private transient GPIndividSize size;

  public UnifiedGpIndi(IInnerNode<NodeType> root) {
    this.root = root;
    this.size = null;
  }

  private transient Stack<GPIndividSize> s;

  @Override
  public GPIndividSize getSizes() {
    if (size == null) {
      s = new Stack<>();
      VisitorCostumizer<NodeType> costumizer = new VisitorCostumizer<>();
      costumizer.registerPredicatedConsumer(p -> p.isLeaf(), p -> s.push(new GPIndividSize(0, 1, 0)));
      costumizer.registerPredicatedConsumer(p -> !p.isLeaf(), p -> {
        GPIndividSize ff = s.pop();
        GPIndividSize ss = s.pop();
        s.push(GPIndividSize.uniteAtOperator(ff, ss));
      });

      DepthFirstPostOrderVisitor<NodeType> depthVisitor = new DepthFirstPostOrderVisitor<>(costumizer);
      depthVisitor.visit(root);
      size = s.pop();
    }
    return size;
  }


}
