package FuzzyGp.Tree.Nodes;

public class ZeroEventInput implements ILeaf {

  @Override
  public ILeaf myClone() {
    return new ZeroEventInput();
  }

}
