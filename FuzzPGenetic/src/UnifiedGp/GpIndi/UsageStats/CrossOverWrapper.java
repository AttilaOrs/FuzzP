package UnifiedGp.GpIndi.UsageStats;

import java.util.Random;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import structure.operators.ICreatureBreeder;

public class CrossOverWrapper implements ICreatureBreeder<UnifiedGpIndiWithUsageStats> {
  private ICreatureBreeder<UnifiedGpIndi> original;
  private CrossOverWrapper(ICreatureBreeder<UnifiedGpIndi> original) {
    this.original = original;
  }


  @Override
  public UnifiedGpIndiWithUsageStats[] breed(UnifiedGpIndiWithUsageStats mother, UnifiedGpIndiWithUsageStats father,
      Random rnd) {
    UnifiedGpIndi[] rez = original.breed(mother, father, rnd);
    return new UnifiedGpIndiWithUsageStats[] { new UnifiedGpIndiWithUsageStats(rez[0].getRoot()),
        new UnifiedGpIndiWithUsageStats(rez[1].getRoot()) };
  }

  public CrossOverWrapper wrap(ICreatureBreeder<UnifiedGpIndi> original) {
    return new CrossOverWrapper(original);
  }

}
