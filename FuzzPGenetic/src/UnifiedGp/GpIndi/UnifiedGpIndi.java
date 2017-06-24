package UnifiedGp.GpIndi;

import UnifiedGp.Tree.IInnerNode;
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

  @Override
  public GPIndividSize getSizes() {
    return null;
  }


}
