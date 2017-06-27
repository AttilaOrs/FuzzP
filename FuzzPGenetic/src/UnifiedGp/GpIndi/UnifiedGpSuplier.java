package UnifiedGp.GpIndi;

import java.util.Random;

import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import structure.operators.ICreatureGenerator;

public class UnifiedGpSuplier implements ICreatureGenerator<UnifiedGpIndi> {

  TreeBuilder<NodeType> builder;

  public UnifiedGpSuplier(TreeBuilder<NodeType> builder) {
    this.builder = builder;
  }

  @Override
  public UnifiedGpIndi genearteRandomCreature(Random rnd) {
    return new UnifiedGpIndi(builder.genearteRandomCreature(rnd));
  }

}
