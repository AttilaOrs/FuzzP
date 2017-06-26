package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;

public class MemoryLeaf implements ILeaf<NodeType> {
  
  private int memNr ;
  
  public MemoryLeaf(int memoryNr) {
    this.memNr = memoryNr;
  }

  @Override
  public NodeType getType() {
    return NodeType.Memory;
  }

  public int getMemNr() {
    return memNr;
  }

  @Override
  public ILeaf<NodeType> myClone() {
    return new MemoryLeaf(memNr);
  }
}
