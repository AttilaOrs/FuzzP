package UnifiedGp.Tree.Visitors;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.TestUtils;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FireCounterRecorder;

public class NodeUsageAsociatorTest {

  private ToPetriNet toPetriNet;
  private NodeUsageAsociator associator;

  @Before
  public void setup() {
    toPetriNet = new ToPetriNet(TestUtils.simpleScaleProvider(2.0), true, true);
    associator = new NodeUsageAsociator();
  }

  @Test
  public void test() {
    IInnerNode<NodeType> root = TestUtils.selectionNet();
    PetriConversationResult convRez = toPetriNet.toNet(root);

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(convRez.net);
    FireCounterRecorder<UnifiedToken> counterRecorder = new FireCounterRecorder<>();
    exec.setRecorder(counterRecorder);

    Map<Integer, UnifiedToken> inps = new HashMap<>();

    convRez.addToInpIfPossible(inps, 0, new UnifiedToken(1.0));
    exec.runTick(inps);

    exec.runTick(inps);

    inps.clear();
    convRez.addToInpIfPossible(inps, 1, new UnifiedToken(1.0));
    exec.runTick(inps);
    
    UsageStats overallRez = associator.calcUsage(root, counterRecorder,
        convRez.nodeTransitionMapping.get());
    

    assertThat("overall depth", overallRez.getDepth(root) == 3);
    assertThat("overall run", overallRez.getUsage(root) == 3);

    assertThat("question deth", overallRez.getDepth(root) == 3);
    assertThat("overall run", overallRez.getUsage(root) == 3);


  }

}
