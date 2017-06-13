package FuzzyGp.Tree.Nodes;

public class InversionLeaf implements ILeaf {

  @Override
  public ILeaf myClone() {
    return new InversionLeaf();
  }

}
