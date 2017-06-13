package FuzzyGp.Tree.Nodes;

public class FullRuleLeaf implements ILeaf {

  int inp, out;

  public FullRuleLeaf(int inp, int out) {
    this.inp = inp;
    this.out = out;
  }

  public int getInp() {
    return inp;
  }

  public int getOut() {
    return out;
  }

  @Override
  public ILeaf myClone() {
    return new FullRuleLeaf(inp, out);
  }

}
