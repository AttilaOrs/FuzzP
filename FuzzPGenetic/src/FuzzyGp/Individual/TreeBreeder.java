package FuzzyGp.Individual;

import java.util.Random;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Visitors.CopyReplaces;
import FuzzyGp.Tree.Visitors.RandomNodeSelector;
import structure.operators.ICreatureBreeder;

public class TreeBreeder implements ICreatureBreeder<TreeIndividual> {
  RandomNodeSelector selector = new RandomNodeSelector();
  CopyReplaces replacer = new CopyReplaces();

  @Override
  public TreeIndividual[] breed(TreeIndividual mother, TreeIndividual father, Random rnd) {
    INode motherSelected = selector.randomNode(mother.getRoot(), rnd);
    INode fatherSelected = selector.randomNode(father.getRoot(), rnd);
    if (motherSelected == null || fatherSelected == null) {

      return new TreeIndividual[] { new TreeIndividual(replacer.copy(mother.getRoot())),
          new TreeIndividual(replacer.copy(father.getRoot())) };
    }

    INode motherSelectoinCopy = replacer.copy(motherSelected);
    INode fatherSelectionCopy = replacer.copy(fatherSelected);

    INode mothersChildren = replacer.replace(mother.getRoot(), motherSelected, fatherSelectionCopy);

    INode fathersChidlren = replacer.replace(father.getRoot(), fatherSelected, motherSelectoinCopy);

    return new TreeIndividual[] { new TreeIndividual(mothersChildren), new TreeIndividual(fathersChidlren) };
  }

}
