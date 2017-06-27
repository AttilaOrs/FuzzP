package UnifiedGp.GpIndi;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import UnifiedGp.ProblemSpecification;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.BlockLeaf;
import UnifiedGp.Tree.Nodes.ConstantLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.InputLeaf;
import UnifiedGp.Tree.Nodes.InputType;
import UnifiedGp.Tree.Nodes.InversionLeaf;
import UnifiedGp.Tree.Nodes.MemoryLeaf;
import UnifiedGp.Tree.Nodes.NegateLeaf;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutType;
import UnifiedGp.Tree.Nodes.OutputLeaf;
import UnifiedGp.Tree.Visitors.TreeBuilderConfig;

public class TreeBuilderCongigGeneralImpl implements TreeBuilderConfig<NodeType> {

  private static final Map<NodeType, Double> defaulLeafProbs = new HashMap<>();
  private static final Map<NodeType, Double> defaulOpProbs = new HashMap<>();
  private static final double[] consts = new double[] { 0.5, 1, 2, 5, 10, 50 };
  static {
    defaulLeafProbs.put(NodeType.Inp, 1.0);
    defaulLeafProbs.put(NodeType.Out, 1.0);
    defaulLeafProbs.put(NodeType.Delay, 0.5);
    defaulLeafProbs.put(NodeType.Memory, 0.5);
    defaulLeafProbs.put(NodeType.Block, 0.25);
    defaulLeafProbs.put(NodeType.Const, 0.25);
    defaulLeafProbs.put(NodeType.Inv, 0.1);
    defaulLeafProbs.put(NodeType.Negate, 0.1);

    defaulOpProbs.put(NodeType.Seq, 1.0);
    defaulOpProbs.put(NodeType.Conc, 0.5);
    defaulOpProbs.put(NodeType.Loop, 0.5);
    defaulOpProbs.put(NodeType.Selc, 0.25);
    defaulOpProbs.put(NodeType.Multiply, 0.25);
    defaulOpProbs.put(NodeType.Add, 0.25);
    defaulOpProbs.put(NodeType.PosNegSplit, 0.25);
    assert (defaulLeafProbs.size() + defaulOpProbs.size() == NodeType.values().length);
  }

  private ProblemSpecification spec;
  private Map<NodeType, Double> leafProbs;
  private Map<NodeType, Double> opProbs;
  private Map<NodeType, Function<Random, INode<NodeType>>> factories;

  public TreeBuilderCongigGeneralImpl(ProblemSpecification spec) {
    this(defaulLeafProbs, defaulOpProbs, spec);
  }

  public TreeBuilderCongigGeneralImpl(Map<NodeType, Double> leafProbs, Map<NodeType, Double> opProbs,
      ProblemSpecification spec) {
    this.spec = spec;
    this.leafProbs = leafProbs;
    this.opProbs = opProbs;
    initializeFacories();
  }

  private void initializeFacories() {
    factories = new HashMap<>();
    factories.put(NodeType.Inp, rnd -> new InputLeaf(InputType.values()[rnd.nextInt(InputType.values().length)],
        rnd.nextInt(spec.getInputCount())));
    factories.put(NodeType.Out, rnd -> new OutputLeaf(rnd.nextInt(spec.getOuputCount()),
        OutType.values()[rnd.nextInt(OutType.values().length)]));
    factories.put(NodeType.Delay, rnd -> new DelayLeaf(rnd.nextInt(spec.getMaxDelay())));
    factories.put(NodeType.Memory, rnd -> new MemoryLeaf(rnd.nextInt(spec.getMaxDelay())));
    factories.put(NodeType.Block, rnd -> new BlockLeaf());
    factories.put(NodeType.Const, rnd -> new ConstantLeaf(consts[rnd.nextInt(consts.length)])); // TODO
    factories.put(NodeType.Inv, rnd -> new InversionLeaf());
    factories.put(NodeType.Negate, rnd -> new NegateLeaf());
  }



  @Override
  public double getLeafOrInnerNode() {
    return 0.6;
  }

  @Override
  public Map<NodeType, Double> getLeafProbabilities() {
    return leafProbs;
  }

  @Override
  public Map<NodeType, Double> getOperatorProbabilities() {
    return opProbs;
  }

  @Override
  public Function<Random, INode<NodeType>> getLeafFactory(NodeType t) {
    return factories.get(t);
  }

  @Override
  public int getMaxDepth() {
    return 10;
  }

  @Override
  public UnifiedGp.Tree.Visitors.TreeBuilderConfig.RandomInnerodeCreator<NodeType> getInnerNodeFactory(NodeType t) {
    return (rnd, fi, se) -> new InnerNode(t, fi, se);
  }
  
  

}
