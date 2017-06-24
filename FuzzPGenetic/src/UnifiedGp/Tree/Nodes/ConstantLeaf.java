package UnifiedGp.Tree.Nodes;

import UnifiedGp.Tree.ILeaf;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;

public class ConstantLeaf implements ILeaf<NodeType> {
  
  private final double consValue;
  
  public ConstantLeaf(double consValue) {
    this.consValue = consValue;
  }

  public double getConsValue() {
    return consValue;
  }
  
  @Override
  public NodeType getType() {
    return NodeType.Const;
  }

  
  private final static String copyTableStr = "{"+
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-2,-2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><-1,-1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 0, 0>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 1, 1>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>< 2, 2>]"+//
  	"[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]"+//
  "}";
  
  private final static String reciveTableStr = "{"+
  	"[<-2><-2><-2><-2><-2><FF>]"+//
  	"[<-1><-1><-1><-1><-1><FF>]"+//
  	"[< 0>< 0>< 0>< 0>< 0><FF>]"+//
  	"[< 1>< 1>< 1>< 1>< 1><FF>]"+//
  	"[< 2>< 2>< 2>< 2>< 2><FF>]"+//
  	"[<FF><FF><FF><FF><FF><FF>]"+//
  "}";
  
  
  public final static UnifiedTwoXTwoTable copyTable = UnifiedTableParser.parseUnifiedTwoXTwoTable(copyTableStr);
  
  public final static UnifiedTwoXOneTable recieveTable = UnifiedTableParser.parseUnifiedTwoXOneTable(reciveTableStr );
  


}
