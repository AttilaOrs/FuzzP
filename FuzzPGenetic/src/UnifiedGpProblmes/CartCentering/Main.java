package UnifiedGpProblmes.CartCentering;

import java.io.File;
import java.util.ArrayList;

import AlgoImpl.IterationLogger;
import AlgoImpl.SimpleGA;
import AlgoImpl.Selectors.LinearRankSelection;
import AlgoImpl.pools.CreatureParallelPool;
import UnifiedGp.GpIndi.TreeBuilderCongigGeneralImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.GpIndi.UnifiedGpIndiBreeder;
import UnifiedGp.GpIndi.UnifiedGpIndiTreeMutator;
import UnifiedGp.GpIndi.UnifiedGpSuplier;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import UnifiedGp.Tree.Visitors.TreeBuilder;
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

    // gens.add(() -> ((rnd) -> new
    // UnifiedGpIndi(FirstOrderFitnesTest.createConstantNet(1.5))));

    ArrayList<IOperatorFactory<ICreatureMutator<UnifiedGpIndi>>> mutators = new ArrayList<>();
    mutators.add(() -> new UnifiedGpIndiTreeMutator(createTreeBuilder()));
    
    ArrayList<IOperatorFactory<ICreatureBreeder<UnifiedGpIndi>>> breeders = new ArrayList<>();
    breeders.add(() -> new UnifiedGpIndiBreeder());

    ArrayList<IOperatorFactory<ICreatureFitnes<UnifiedGpIndi>>> fitnesses = new ArrayList<>();
    fitnesses.add(() -> new CartFitnes());

    ICreaturePool<UnifiedGpIndi> pool = new CreatureParallelPool<UnifiedGpIndi>(gens, mutators, breeders, fitnesses);

    SimpleGA<UnifiedGpIndi> algo = new SimpleGA<>(pool, new LinearRankSelection());
    SimpleGA.iteration = 100;
    SimpleGA.population = 1000;
    algo.theAlgo();

    IterationLogger logger = algo.getLogger();
    /*
     * PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("tree"),
     * "bloat_tree_cart.svg");
     * PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("time"),
     * "bloat_time_cart.svg");
     * PlotUtils.plot(logger.getLogsForPlottingContatinigStrings("fit"),
     * "firtes_cart.svg");
     */

    Integer i = algo.getMaxId();

    UnifiedGpIndi rez = pool.get(i);
    finalize(rez);

    // UnifiedVizualizer.visualize(convRez.net, rec,
    // TransitionPlaceNameStore.createOrdinarNames(convRez.net));

  }

  private static void finalize(UnifiedGpIndi rez) {
    ToPetriNet toNet = new ToPetriNet(UnifiedPetriController.create());
    PetriConversationResult ll = toNet.toNet(rez.getRoot());
    UnifiedPetriController cont = new UnifiedPetriController(ll);
    FullRecorder<UnifiedToken> fullRec = new FullRecorder<>();
    cont.setFiredRec(fullRec);
    CartSimpluator sim = new CartSimpluator(50, 10, false);
    sim.sim(cont);
    // UnifiedVizualizer.visualize(ll.net, fullRec,
    // TransitionPlaceNameStore.createSimplerOrdinarNames(ll.net));
    ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> scenarioSaver = new ScenarioSaverLoader<>(UnifiedPetriNet.class);
    scenarioSaver.setFullRec(fullRec);
    scenarioSaver.setPetriNet(ll.net);
    scenarioSaver.save(new File("rezScenarioCart.json"));

  }

  public static TreeBuilder<NodeType> createTreeBuilder() {
    return new TreeBuilder<>(new TreeBuilderCongigGeneralImpl(UnifiedPetriController.create()));

  }

}
