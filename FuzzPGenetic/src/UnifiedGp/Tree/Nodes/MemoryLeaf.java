package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;

public class MemoryLeaf implements ILeaf<NodeType, MemorySubtype> {
  
  private int memNr ;
  
  public MemoryLeaf(int memoryNr) {
    this.memNr = memoryNr;
  }

  @Override
  public NodeType getType() {
    return NodeType.Memory;
  }

  @Override
  public MemorySubtype getSubtype() {
    return MemorySubtype.SimpleMemory;
  }

  public int getMemNr() {
    return memNr;
  }
}
