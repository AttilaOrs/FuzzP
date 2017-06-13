package FuzzyGp.Individual;

import java.util.Random;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Visitors.CopyReplaces;
import FuzzyGp.Tree.Visitors.RandomNodeSelector;
import structure.operators.ICreatureMutator;

public class CuttingMutator implements ICreatureMutator<TreeIndividual> {

  private RandomTreeBuilder builder;
  RandomNodeSelector selector = new RandomNodeSelector();
  CopyReplaces replaces = new CopyReplaces();

  public CuttingMutator(RandomTreeBuilder treeBuilder) {
    this.builder = treeBuilder;
  }

  @Override
  public TreeIndividual mutate(TreeIndividual creature, Random random) {
    INode randomOperator = selector.randomNode(creature.getRoot(), random);
    INode newStuff = builder.createLeaf(random);
    if (randomOperator == null) {
      return new TreeIndividual(newStuff);
    }
    return new TreeIndividual(replaces.replace(creature.getRoot(), randomOperator, newStuff));
  }

}
