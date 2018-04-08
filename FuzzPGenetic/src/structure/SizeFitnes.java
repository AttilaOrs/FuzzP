package structure;

public class SizeFitnes<Tcreature extends IGPGreature> implements ICreatureFitnes<Tcreature> {

  public static int acceptableSize = 10;
  public static int maxSize = 600;

  @Override
  public double evaluate(Tcreature creature) {
    return calcualte(creature.getSizes());
  }

  private double calcualte(GPIndividSize size) {
    double sum = ((double) size.leafs + size.ops);
    if (sum <= acceptableSize) {
      return 1.0;
    }
    if (sum >= maxSize) {
      return 0.0;
    }
    return 1.0 - ((sum - acceptableSize) / (maxSize - acceptableSize));
  }
}
