package UnifiedGp.GpIndi.UsageStats;

import java.util.Random;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import structure.operators.ICreatureGenerator;

public class GeneratorWrapperForUsage implements ICreatureGenerator<UnifiedGpIndiWithUsageStats> {

  public static GeneratorWrapperForUsage wrap(ICreatureGenerator<UnifiedGpIndi> original) {
    return new GeneratorWrapperForUsage(original);
  }

  private ICreatureGenerator<UnifiedGpIndi> original;

  private GeneratorWrapperForUsage(ICreatureGenerator<UnifiedGpIndi> original) {
    this.original = original;
  }

  @Override
  public UnifiedGpIndiWithUsageStats genearteRandomCreature(Random rnd) {
    UnifiedGpIndi w = original.genearteRandomCreature(rnd);
    return new UnifiedGpIndiWithUsageStats(w.getRoot());
  }

}
