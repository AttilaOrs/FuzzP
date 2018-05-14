

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import AlgoImpl.IterationLogger;
import Main.UnifiedVizualizer;
import UETPNLisp.UETPNLisp;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGp.Tree.Visitors.ToPetriNet;
import commonUtil.PlotUtils;
import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.DrawableUnifiedPetriNetWithExternalNames;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecorder;
import dotDrawer.PetriDotDrawerVertical;

public class UETPNLispVisualzer {

  public static void main(String[] args) {
    INode<NodeType> root = UETPNLisp.fromFile(new File("example_new2.uls"));
    ToPetriNet tpn = new ToPetriNet(createProblemSpecification(), true, false);
    PetriConversationResult rez = tpn.toNet((IInnerNode<NodeType>) root);
    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(rez.net);
    exec.setRecorder(rec);
    Random rnd = new Random();
    IterationLogger logger = new IterationLogger();
    HashMap<Integer, UnifiedToken> inp = new HashMap<>();
    rez.addActionIfPossible(0, t -> logger.addLogToTopic("out", t.getValue()));
    double d = -1.0;
    double inc = 0.2;
    for (int i = 0; i < 40; i++) {
      inp.clear();
      UnifiedToken inp1 = new UnifiedToken(d);
      d += inc;
      if(Math.abs(d -1.0)< 0.01 || Math.abs(d +1.0)< 0.01) {
    	  inc *= -1.0;
      }
      UnifiedToken inp2 = (i==30)? new UnifiedToken(0.0) : new UnifiedToken();
      rez.addToInpIfPossible(inp, 0, inp1 );
      rez.addToInpIfPossible(inp, 1, inp2 );
      logger.addLogToTopic("inp1",inp1.getValue() );
      if(!inp2.isPhi()) {
		  logger.addLogToTopic("inp2",inp2.getValue() );
      }
      exec.runTick(inp);
    }
    UnifiedVizualizer.visualize(rez.net, rec, TransitionPlaceNameStore.createOrdinarNames(rez.net));

    PetriDotDrawerVertical drawer = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNetWithExternalNames(rez.net, TransitionPlaceNameStore.createOrdinarNames(rez.net)));
    drawer.makeImage("last_arc_fi");
    String inp0 = rec.evolutionOfPlaceDatFormatOnceInTick(rez.inpNrToInpPlace.get(0), t -> ((UnifiedToken)t).getValue());
    PlotUtils.writeToFile("inp0_exb.dat", inp0);
    String inp1 = rec.evolutionOfPlaceDatFormatOnceInTick(rez.inpNrToInpPlace.get(1), t -> ((UnifiedToken)t).getValue());
    PlotUtils.writeToFile("inp1.dat", inp1);
    String out = rec.evolutionOfPlaceDatFormatOnceInTick(9, t -> ((UnifiedToken)t).getValue());
    PlotUtils.writeToFile("out_exb.dat", out);
    

  }

  public static ProblemSpecificationImpl createProblemSpecification() {
    Map<Integer, Double> inpScale = new HashMap<>();
    for (int i = 0; i < 100; i++) {
      inpScale.put(i, 1.0);
    }
    Map<Integer, Double> outScale = new HashMap<>();
    for (int i = 0; i < 100; i++) {
      outScale.put(i, 1.0);
    }

    return new ProblemSpecificationImpl(2.0, 5, inpScale, outScale);
  }
}
