package AlgoImpl;

import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import structure.ICreaturePool;
import structure.IFitnesTransformer;
import structure.IGPGreature;
import structure.ISelector;

public class MultiobjectiveMulioperatorGA<TCreature extends IGPGreature> extends MultiobjectiveGA<TCreature> {

  private double[] generatorNormalWeigths;
  private double[] crossoverNormalWeigths;
  private double[] mutatationNormalWeigths;

  public MultiobjectiveMulioperatorGA(ICreaturePool<TCreature> pool, ISelector selector, ISelector survSelector,
      IFitnesTransformer transformer,
      double[] fitnessWeights, double[] generatorWeigths, double[] crossoverWeights, double[] mutationWeights) {
    super(pool, selector, survSelector, fitnessWeights, transformer);

    this.generatorNormalWeigths = normalise(generatorWeigths);
    this.crossoverNormalWeigths = normalise(crossoverWeights);
    this.mutatationNormalWeigths = normalise(mutationWeights);

  }

  int hack;

  @Override
  public void theAlgo() {
    int eliteNr = population * ELIT / 100;
    int survNr = population * SELECTION / 100;
    int mutNr = population * MUTATION / 100;
    int cross = population * CROSSOVER / 200;

    for (iter = 0; iter < iteration; iter++) {
      long timeStart = System.nanoTime();
      if (iter == 0) {
        int curent = 0;
        for (int generatirIndex = 0; generatirIndex < generatorNormalWeigths.length; generatirIndex++) {
          int howMaany = (int) (population * generatorNormalWeigths[generatirIndex]);
          pool.generate(generatirIndex, curent, howMaany);
          curent += howMaany;
        }
      } else {
        int curentIndex = 0;
        for (hack = 0; hack < fitnesNormalWeigths.length; hack++) {
          List<Entry<Integer, Double[]>> elite = res.entrySet().stream()
              .sorted((entry1, entry2) -> entry1.getValue()[hack].compareTo(entry2.getValue()[hack]) * -1)
              .limit((long) (eliteNr * fitnesNormalWeigths[hack])).collect(Collectors.toList());
          List<int[]> elitsSurv = elite.stream().map(entry -> new int[] { entry.getKey(), nextIndex() })
              .collect(Collectors.toList());
          // String st = elite.stream().map(i -> i.getKey() + " " +
          // i.getValue()[0]).collect(Collectors.joining("\n"));
          pool.survive(elitsSurv);
        }

        for (int i = 0; i < fitnesNormalWeigths.length; i++) {
          List<int[]> toSurv = survSelector.selectOne(res, i, (int) (survNr * fitnesNormalWeigths[i]), 2);
          for (int j = 0; j < toSurv.size(); j++) {
            toSurv.get(j)[1] = nextIndex();

          }
          pool.survive(toSurv);
        }
        for (int mutIndex = 0; mutIndex < mutatationNormalWeigths.length; mutIndex++) {
          for (int ff = 0; ff < fitnesNormalWeigths.length; ff++) {
            List<int[]> toMut = selector.selectOne(res, ff,
                (int) (mutNr * fitnesNormalWeigths[ff] * mutatationNormalWeigths[mutIndex]), 2);
            for (int i = 0; i < toMut.size(); i++) {
              toMut.get(i)[1] = population * iter + curentIndex;
              curentIndex++;
            }
            pool.mutate(mutIndex, toMut);
          }
        }

        for (int crossIndex = 0; crossIndex < crossoverNormalWeigths.length; crossIndex++) {
          for (int ff = 0; ff < fitnesNormalWeigths.length; ff++) {
            int fffff = (int) (cross * fitnesNormalWeigths[ff] * crossoverNormalWeigths[crossIndex]);
            if (fffff == 0) {
              break;
            }
            List<int[]> toCross = selector.selectPairs(res, ff,
                fffff, 4);
            for (int i = 0; i < toCross.size(); i++) {
              toCross.get(i)[2] = population * iter + curentIndex;
              curentIndex++;
              toCross.get(i)[3] = population * iter + curentIndex;
              curentIndex++;
            }

            pool.crossover(crossIndex, toCross);
          }
        }

      }
      res = transform(pool.calculateFitnessAndDeleteOldGeneration());
      long timeStop = System.nanoTime();

      logMemoryAndGc();
      logIterationResults(iter, res);
      super.logNonFitnessRelated(iter, pool.getSizeStats(), (timeStop - timeStart) / res.size());

      System.gc();

    }

    Optional<Entry<Integer, Double[]>> max = res.entrySet().stream()
        .max((entry1, entry2) -> entry1.getValue()[0].compareTo(entry2.getValue()[0]));
    this.maxId = max.get().getKey();
    pool.terminatePool();
  }

}
