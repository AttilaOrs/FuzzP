package FuzzyGp.Individual;

import java.util.Random;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Nodes.DelayLeaf;
import FuzzyGp.Tree.Nodes.InputLeaf;
import FuzzyGp.Tree.Nodes.InversionLeaf;
import FuzzyGp.Tree.Nodes.OutLeaf;
import FuzzyGp.Tree.Nodes.ZeroEventInput;
import FuzzyGp.Tree.Visitors.CopyReplaces;
import FuzzyGp.Tree.Visitors.RandomNodeSelectorWithType;
import structure.operators.ICreatureMutator;

public class LeafMutator implements ICreatureMutator<TreeIndividual> {
  CopyReplaces replaces = new CopyReplaces();
  RandomTreeBuilder builder;
  RandomNodeSelectorWithType selector = new RandomNodeSelectorWithType();

  public LeafMutator(RandomTreeBuilder treeBuilder) {
    this.builder = treeBuilder;
  }

  @Override
  public TreeIndividual mutate(TreeIndividual creature, Random random) {
    INode ww = selector.getRandomNodeWithtypes(creature.getRoot(), random, InputLeaf.class, OutLeaf.class,
        ZeroEventInput.class, DelayLeaf.class, InversionLeaf.class);
    INode nn = builder.createLeaf(random);

    INode copyRoot = replaces.replace(creature.getRoot(), ww, nn);
    return new TreeIndividual(copyRoot);
  }
}
