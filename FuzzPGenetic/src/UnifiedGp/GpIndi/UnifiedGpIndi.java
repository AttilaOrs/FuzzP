package UnifiedGp.GpIndi;

import java.util.ArrayDeque;
import java.util.Deque;

import UnifiedGp.Tree.DepthFirstPostOrderVisitor;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.VisitorCostumizer;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.CopyReplace;
import structure.GPIndividSize;
import structure.IGPGreature;

public class UnifiedGpIndi implements IGPGreature {

  private IInnerNode<NodeType> root;
  private transient GPIndividSize size;

  public UnifiedGpIndi(IInnerNode<NodeType> root) {
    this.root = root;
    this.size = null;
  }
  

  public IInnerNode<NodeType> getRoot() {
    return root;
  }

  public void setRoot(IInnerNode<NodeType> root) {
    this.root = root;
  }

  private transient Deque<GPIndividSize> s;

  @Override
  public GPIndividSize getSizes() {
      s = new ArrayDeque<>();
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
    return size;
  }


  @Override
  public IGPGreature myClone() {
    CopyReplace<NodeType> t = new CopyReplace<>();
    return new UnifiedGpIndi((IInnerNode<NodeType>) t.copyReplace(root, null, null));
  }

}
