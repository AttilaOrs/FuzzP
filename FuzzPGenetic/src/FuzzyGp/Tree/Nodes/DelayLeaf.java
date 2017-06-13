package FuzzyGp.Tree.Nodes;

public class DelayLeaf implements ILeaf {
  private final int delayNr;

  public DelayLeaf(int delayNr) {
    this.delayNr = delayNr;
  }

  public int getDelay() {
    return this.delayNr;
  }

  @Override
  public ILeaf myClone() {
    return new DelayLeaf(delayNr);
  }

}
