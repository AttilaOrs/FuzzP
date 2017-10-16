package UnifiedGp.GpIndi.UsageStats;

import java.util.Random;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import structure.operators.ICreatureMutator;

public class MutationWrapper implements ICreatureMutator<UnifiedGpIndiWithUsageStats> {

  private ICreatureMutator<UnifiedGpIndi> original;

  private MutationWrapper(ICreatureMutator<UnifiedGpIndi> original) {
    this.original = original;
  }

  @Override
  public UnifiedGpIndiWithUsageStats mutate(UnifiedGpIndiWithUsageStats creature, Random random) {
    UnifiedGpIndi rez = original.mutate(creature, random);
    return new UnifiedGpIndiWithUsageStats(rez.getRoot());
  }

}
