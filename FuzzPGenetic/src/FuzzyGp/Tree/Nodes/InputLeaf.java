package FuzzyGp.Tree.Nodes;

public class InputLeaf implements ILeaf {
  private final int inpNr;
  private final InputType type;

  public InputLeaf(int inpNr) {
    this(inpNr, InputType.defultInputType);
  }

  public InputLeaf(int inpNr, InputType type) {
    this.inpNr = inpNr;
    this.type = type;
  }

  public int getInpNr() {
    return this.inpNr;
  }

  public InputType getInputType() {
    return this.type;
  }

  @Override
  public ILeaf myClone() {
    return new InputLeaf(inpNr, type);
  }

  // since you cannot do this with an enum, this is the better place

}
