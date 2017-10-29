package UnifiedGp.Tree.Visitors;

import static org.hamcrest.MatcherAssert.assertThat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.sun.prism.j2d.J2DPipeline;

import UnifiedGp.TestUtils;
import UnifiedGp.GpIndi.UsageStats.SelectRandomBasedOnUsageAndDepth;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FireCounterRecorder;

public class NodeUsageMergerTest {
  
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
    convRez.addToInpIfPossible(inps, 0, new UnifiedToken(1.0));
    exec.runTick(inps);
    
    UsageStats overallRez = associator.calcUsage(root, counterRecorder,
        convRez.nodeTransitionMapping.get());
    
    DynamicallySimplifiedPetriNetBuilder builder = new DynamicallySimplifiedPetriNetBuilder();
    IInnerNode<NodeType> newRoot = builder.createSimplifiedTree(root, counterRecorder.getFiredTransition(),convRez.nodeTransitionMapping.get());
    
    UsageStats i = NodeUsageMerger.merge(root, overallRez, newRoot, new UsageStats());
    
    assertThat("overall depth", i.getDepth(newRoot) == 3);
    assertThat("overall run", i.getUsage(newRoot) == 3);
    
    SelectRandomBasedOnUsageAndDepth l = new SelectRandomBasedOnUsageAndDepth();

  }

}
