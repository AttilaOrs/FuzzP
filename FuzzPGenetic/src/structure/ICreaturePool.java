package structure;

import java.util.List;
import java.util.Map;

public interface ICreaturePool<TCreature extends IGPGreature> {

  Map<Integer, Double[]> calculateFitnessAndDeleteOldGeneration();

  Map<Integer, Double[]> calculateFitnessAndMergeOldGenWithNew();

  void survive(List<int[]> idsToSurvive);

  void mutate(int whichMutator, List<int[]> idsToMutate);

  void crossover(int whichbreeder, List<int[]> idsToCross);

  void generate(int whichGenerator, int firstId, int howMany);

  public List<String> getGeneratorsNames();

  public List<String> getMutatorsNames();

  public List<String> getBreedersNames();

  public List<String> getFitnesCalsNames();

  TCreature get(int id);

  void terminatePool();

  public GenerationSizeStats getSizeStats();

  public static class GenerationSizeStats {

    public final GPIndividSize min, max, avg;
    public final Map<Integer, Integer> sizeHist;

    public GenerationSizeStats(GPIndividSize min, GPIndividSize max, GPIndividSize avg,
        Map<Integer, Integer> sizeHist) {
      this.min = min;
      this.max = max;
      this.avg = avg;
      this.sizeHist = sizeHist;
    }
  }

}
