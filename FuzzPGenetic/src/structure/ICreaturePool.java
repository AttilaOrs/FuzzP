package structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public interface ICreaturePool<TCreature extends IGPGreature> {

  Map<Integer, Double[]> calculateFitness();
  
  Map<Integer, Double[]> getOldPopulationFitnes();

  void survive(List<int[]> idsToSurvive);

  void mutate(int whichMutator, List<int[]> idsToMutate);

  void crossover(int whichbreeder, List<int[]> idsToCross);

  void generate(int whichGenerator, int firstId, int howMany);

  public ArrayList<IOperatorFactory<ICreatureGenerator<TCreature>>> getGenerators();

  public ArrayList<IOperatorFactory<ICreatureMutator<TCreature>>> getMutators();

  public ArrayList<IOperatorFactory<ICreatureBreeder<TCreature>>> getBreeders();

  public ArrayList<IOperatorFactory<ICreatureFitnes<TCreature>>> getFitnesCals();

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
