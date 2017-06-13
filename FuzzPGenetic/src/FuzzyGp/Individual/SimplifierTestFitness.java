package FuzzyGp.Individual;

import java.util.ArrayList;

import FuzzyGp.ControllerBuilder;
import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Visitors.DynaminSimplifier;

public class SimplifierTestFitness extends TestFitnes {
  DynaminSimplifier simp = new DynaminSimplifier();

  public SimplifierTestFitness(ControllerBuilder bl, String toCompareWith, ArrayList<ArrayList<Double>> testSequence) {
    super(bl, toCompareWith, testSequence);
  }

  @Override
  public double evaluate(TreeIndividual creature) {
    double rez = super.evaluate(creature);
    INode newRoot = simp.simplify(creature.getRoot(), rec.getFiredTransition(), mapping);
    creature.setRoot(newRoot);
    /*
     * TreeIndividual trr = new TreeIndividual(newRoot); double reezz =
     * super.evaluate(trr); if (reezz != rez) { System.out.println("problem" +
     * rez + " " + reezz); System.out.println("same\n" +
     * convertToSlisp(creature.getRoot()) + "\n " + convertToSlisp(newRoot)); }
     */

    if (rez > 0.95) {
      rez += (1.0 / (1.0 + creature.getSizes().depth));
    }
    return rez;
  }

}
