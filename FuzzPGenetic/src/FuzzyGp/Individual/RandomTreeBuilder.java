package FuzzyGp.Individual;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Nodes.DelayLeaf;
import FuzzyGp.Tree.Nodes.FullRuleLeaf;
import FuzzyGp.Tree.Nodes.ILeaf;
import FuzzyGp.Tree.Nodes.InputLeaf;
import FuzzyGp.Tree.Nodes.InputType;
import FuzzyGp.Tree.Nodes.InversionLeaf;
import FuzzyGp.Tree.Nodes.Operator;
import FuzzyGp.Tree.Nodes.OperatorType;
import FuzzyGp.Tree.Nodes.OutLeaf;
import FuzzyGp.Tree.Nodes.OutType;
import FuzzyGp.Tree.Nodes.ZeroEventInput;

import java.util.Random;

import structure.operators.ICreatureGenerator;

public class RandomTreeBuilder implements ICreatureGenerator<TreeIndividual> {

  private ProblemSpecification specifiaction;
  private double leafChance = 0.5;
  private int maxDepth = 10;

  public RandomTreeBuilder(ProblemSpecification pr, int maxDepth, double leafCh) {
    this.specifiaction = pr;
    this.maxDepth = maxDepth;
    this.leafChance = leafCh;
  }

  @Override
  public TreeIndividual genearteRandomCreature(Random rnd) {
    return new TreeIndividual(createRandom(rnd, false, maxDepth));
  }

  public INode createRandom(Random rnd, boolean possibleOnlyLeaf, int mmmax) {
    if (possibleOnlyLeaf && (leafChance > rnd.nextDouble() || mmmax <= 0)) {
      return createLeaf(rnd);
    }
    INode ff = createRandom(rnd, true, mmmax - 1);
    INode ss = createRandom(rnd, true, mmmax - 1);
    int valInex = rnd.nextInt(OperatorType.values().length);
    return new Operator(OperatorType.values()[valInex], ff, ss);
  }

  public ILeaf createLeaf(Random rnd) {
    return getWithChanse(rnd, leafFactories).makeLeaf(rnd, specifiaction);
  }

  public static class ProblemSpecification {
    public ProblemSpecification(int nrOfInps, int nrOfOuts, int maxDelay) {
      this.nrOfInput = nrOfInps;
      this.nrOfOutput = nrOfOuts;
      this.maxDelay = maxDelay;
    }

    int nrOfInput;
    int nrOfOutput;
    int maxDelay;
  }

  private static interface LeafFactory {
    ILeaf makeLeaf(Random rnd, ProblemSpecification sp);
  }

  public static final EnumMap<InputType, Double> inpTypeChance = new EnumMap<InputType, Double>(InputType.class);

  static {
    inpTypeChance.put(InputType.BM, 0.25);
    inpTypeChance.put(InputType.BR, 0.15);
    inpTypeChance.put(InputType.BE, 0.10);
    inpTypeChance.put(InputType.NR, 0.125);
    inpTypeChance.put(InputType.NM, 0.125);
    inpTypeChance.put(InputType.CNE, 0.125);
    inpTypeChance.put(InputType.CPE, 0.125);
    inpTypeChance.put(InputType.SD, 0.100);
    inpTypeChance.put(InputType.SU, 0.100);
    inpTypeChance.put(InputType.SZ, 0.100);
  }

  public static final EnumMap<OutType, Double> outTypeChane = new EnumMap<OutType, Double>(OutType.class);

  static {
    outTypeChane.put(OutType.W, 0.5);
    outTypeChane.put(OutType.WZ, 0.2);
    outTypeChane.put(OutType.EIN, 0.125);
    outTypeChane.put(OutType.EIP, 0.125);
  }

  private final static HashMap<LeafFactory, Double> leafFactories;

  static {
    leafFactories = new HashMap<>();
    leafFactories.put((rnd, sp) -> new InputLeaf(rnd.nextInt(sp.nrOfInput), getWithChanse(rnd, inpTypeChance)), 0.3);
    leafFactories.put((rnd, sp) -> new OutLeaf(rnd.nextInt(sp.nrOfOutput), getWithChanse(rnd, outTypeChane)), 0.3);
    leafFactories.put((rnd, sp) -> new DelayLeaf(rnd.nextInt(sp.maxDelay)), 0.3);
    leafFactories.put((rnd, sp) -> new ZeroEventInput(), 0.1);
    leafFactories.put((rnd, sp) -> new InversionLeaf(), 0.1);
    leafFactories.put((rnd, sp) -> new FullRuleLeaf(rnd.nextInt(sp.nrOfInput), rnd.nextInt(sp.nrOfOutput)), 0.1);
  }

  public static <type> type getWithChanse(Random rnd, Map<type, Double> chanses) {
    double sum = chanses.values().stream().mapToDouble(d -> d).sum();
    double rndValue = rnd.nextDouble() * sum;
    double tempSum = 0;
    for (Entry<type, Double> mm : chanses.entrySet()) {
      tempSum += mm.getValue();
      if (tempSum > rndValue) {
        return mm.getKey();
      }
    }

    return null;
  }

}
