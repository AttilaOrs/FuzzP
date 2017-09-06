package UnifiedGp.GpIndi;

import java.util.Random;

import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.TreeBuilderConfig;
import UnifiedGp.Tree.Visitors.TreeBuilderTopDown;
import structure.operators.ICreatureGenerator;

public class HalfRampHalfFull implements ICreatureGenerator<UnifiedGpIndi> {


  private TreeBuilderTopDown<NodeType> builder;
  private int depth;

  public HalfRampHalfFull(TreeBuilderConfig<NodeType> config, int depth) {
    this.builder = new TreeBuilderTopDown<>(config);
    this.depth = depth;
  }

  public HalfRampHalfFull(TreeBuilderConfig<NodeType> config) {
    this.builder = new TreeBuilderTopDown<>(config);
    this.depth = config.getMaxDepth();
  }


  @Override
  public UnifiedGpIndi genearteRandomCreature(Random rnd) {
    if (rnd.nextBoolean()) {
      return new UnifiedGpIndi(builder.genearteFullRandomTree(rnd, depth));
    } else {
      return new UnifiedGpIndi(builder.genearteRandomTree(rnd, depth));
    }
  }

}
