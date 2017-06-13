package AlgoImpl;

import java.util.Map;

import AlgoImpl.pools.PoolDecoratorForOperatorHistory;
import structure.ICreaturePool;
import structure.IFitnesTransformer;
import structure.IGPGreature;
import structure.ISelector;

public class MultiobjectivMultioperatorAdaptativGa<TCreature extends IGPGreature>
    extends MultiobjectiveMulioperatorGA<TCreature> {

  private PoolDecoratorForOperatorHistory<TCreature> decoratorPool;

  public MultiobjectivMultioperatorAdaptativGa(ICreaturePool<TCreature> pool, ISelector selector,
      IFitnesTransformer transformer, double[] fitnessWeights, double[] generatorWeigths, double[] crossoverWeights,
      double[] mutationWeights) {

    super(pool, selector, transformer, fitnessWeights, generatorWeigths, crossoverWeights, mutationWeights);
    decoratorPool = new PoolDecoratorForOperatorHistory<>(pool, logger, transformer);
    super.pool = decoratorPool;
  }

  @Override
  protected Map<Integer, Double[]> transform(Map<Integer, Double[]> calculateFitness) {
    // decorator pool does the trick already
    return calculateFitness;
  }

}
