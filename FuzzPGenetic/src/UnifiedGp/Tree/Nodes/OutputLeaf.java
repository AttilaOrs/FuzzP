package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;

public class OutputLeaf implements ILeaf<NodeType> {
  
  public final static String CopyTable = "{[<-2,-2><-1,-1><0,0><1,1><2,2><FF,FF>]}";

  private final int outNr;
  private final OutType myType;

  public OutputLeaf(int outNr, OutType myType) {
    this.outNr = outNr;
    this.myType = myType;
  }

  @Override
  public NodeType getType() {
    return NodeType.Out;
  }

  public OutType getSubtype() {
    return myType;
  }

  public int outNr(){
    return this.outNr;
  }
}
