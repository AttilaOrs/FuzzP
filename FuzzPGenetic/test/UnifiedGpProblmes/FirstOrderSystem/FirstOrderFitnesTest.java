package UnifiedGpProblmes.FirstOrderSystem;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.ConstantLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutType;
import UnifiedGp.Tree.Nodes.OutputLeaf;
import commonUtil.PlotUtils;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FullRecorder;
import core.common.recoder.fullrecorderevents.IFullRecorderEvent;
import core.common.recoder.fullrecorderevents.TokenFromPlaceToTransition;
import main.ScenarioSaverLoader;

public class FirstOrderFitnesTest {

  @Test
  public void test() {
    FirstOrderFitnes f = new FirstOrderFitnes();
    UnifiedGpIndi creature = new UnifiedGpIndi(createConstantNet(0.1));
    double i = f.evaluate(creature);

    UnifiedGpIndi creature2 = new UnifiedGpIndi(createConstantNet(0.9));
    double i2 = f.evaluate(creature2);

    assertTrue(i < i2);


  }

  public static IInnerNode<NodeType> createConstantNet(double value) {
    ConstantLeaf c = new ConstantLeaf(value);
    OutputLeaf o = new OutputLeaf(0, OutType.Copy);
    DelayLeaf d = new DelayLeaf(1);
    InnerNode i = new InnerNode(NodeType.Seq, c, o);
    return new InnerNode(NodeType.Loop, i, d);
  }

  public static void main(String args[]) {

    StringBuilder build = new StringBuilder();
    ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> scenarioSaver = new ScenarioSaverLoader<>(UnifiedPetriNet.class);
    scenarioSaver.load(new File("rezScenario.json"), UnifiedToken::buildFromString);
    FullRecorder<UnifiedToken> ww = scenarioSaver.getFullRec();
    int cntr = 0;
    for (List<IFullRecorderEvent> evs : ww.eventGroupedByTicks()) {
      Double fi = null;
      Double se = null;
      for (IFullRecorderEvent e : evs) {
        if (e instanceof TokenFromPlaceToTransition) {
          TokenFromPlaceToTransition<UnifiedToken> takenOut = (TokenFromPlaceToTransition) e;
          if (takenOut.place == 586) {
            fi = takenOut.token.getValue();
          } else if (takenOut.place == 581) {
            se = takenOut.token.getValue();
          }
        }

      }
        build.append(cntr).append(",");
        build.append(fi).append(",");
        build.append(se).append("\n");
        cntr++;

    }

    PlotUtils.writeToFile("cont.csv", build.toString());
    // 586; 581


  }

}
