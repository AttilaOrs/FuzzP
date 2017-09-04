package UnifiedGp.GpIndi;

import java.util.Random;

import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.StaticSimplifierPetriBuilder;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import structure.operators.ICreatureGenerator;

public class UnifiedGpSuplier implements ICreatureGenerator<UnifiedGpIndi> {

  private static final boolean defaultStaticSimplification = true;
  private final boolean staticSimplificationEnabbled;
  private final StaticSimplifierPetriBuilder simplifier;
  TreeBuilder<NodeType> builder;

  public UnifiedGpSuplier(TreeBuilder<NodeType> builder) {
    this(builder, defaultStaticSimplification);
  }
  public UnifiedGpSuplier(TreeBuilder<NodeType> builder, boolean enabledStaticSimpl) {
    this.builder = builder;
    this.staticSimplificationEnabbled = enabledStaticSimpl;
    if (staticSimplificationEnabbled) {
      this.simplifier = new StaticSimplifierPetriBuilder();
    } else {
      simplifier = null;
    }
  }

  @Override
  public UnifiedGpIndi genearteRandomCreature(Random rnd) {
    if (staticSimplificationEnabbled) {
      return new UnifiedGpIndi(simplifier.simplifyTree(builder.genearteRandomTree(rnd)));
    }
    return new UnifiedGpIndi((builder.genearteRandomTree(rnd)));
  }

}
