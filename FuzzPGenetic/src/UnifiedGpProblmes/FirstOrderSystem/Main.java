package UnifiedGpProblmes.FirstOrderSystem;

import java.io.File;
import java.util.ArrayList;

import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreaturePoolWithStreams;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifiedGpSuplier;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import UnifiedGp.Tree.Visitors.TreeBuilder;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FullRecorder;
import main.ScenarioSaverLoader;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IOperatorFactory;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;
public class Main {

  public static void main(String args[]) {

    ArrayList<IOperatorFactory<ICreatureGenerator<UnifiedGpIndi>>> gens = new ArrayList<>();
    gens.add(() -> new UnifiedGpSuplier(createTreeBuilder()));

    ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
    mutators.add(() -> new UnifiedGpIndiTreeMutator(createTreeBuilder()));
    
    ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
    breeders.add(() -> new UnifiedGpIndiBreeder());

    ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
    fitnesses.add(() -> new FirstOrderFitnes());

    ICreaturePool<UnifiedGpIndi> pool = new CreaturePoolWithStreams<UnifiedGpIndi>(gens, mutators, breeders, fitnesses);

    SimpleGA<UnifiedGpIndi> algo = new SimpleGA<>(pool, new LinearRankSelection());
    SimpleGA.iteration = 100;
    SimpleGA.population = 1000;
    algo.theAlgo();

    /*
     * IterationLogger logger = algo.getLogger();
     * PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree"),
     * "bloat_tree.svg");
     * PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"),
     * "bloat_time.svg");
     * PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("fit"),
     * "firtes.svg");
     */

    Integer i = algo.getMaxId();

    UnifiedGpIndi rez = pool.get(i);
    ToPetriNet toPetri = new ToPetriNet(FirstOrderFitnes.createProblemSpecification());
    PetriConversationResult convRez = toPetri.toNet(rez.getRoot());
    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<UnifiedPetriNet>();
    saver.save(convRez.net,
        "rez.json");

    FirstOrderFitnes fitnes = new FirstOrderFitnes(true);
    double fitness = fitnes.evaluate(rez);
    FullRecorder<UnifiedToken> rec = fitnes.getRecorder();
    ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> scenarioSaver = new ScenarioSaverLoader<>(UnifiedPetriNet.class);
    scenarioSaver.setFullRec(rec);
    scenarioSaver.setPetriNet(convRez.net);
    scenarioSaver.save(new File("rezScenario.json"));

    System.out.println(fitness);

    // UnifiedVizualizer.visualize(convRez.net, rec,
    // TransitionPlaceNameStore.createOrdinarNames(convRez.net));

  }

  public static TreeBuilder<NodeType> createTreeBuilder() {
    return new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(FirstOrderFitnes.createProblemSpecification()));
  }

}
