package UnifiedGp.Tree.TestColorTree;

import UnifiedGp.Tree.ILeaf;

public class GreenLeaf implements ILeaf<Color>, HasNumber {
  SubColorGreen subColor;
  int nr;

  public GreenLeaf(SubColorGreen subColor, int nr) {
    this.subColor = subColor;
    this.nr = nr;
  }

  @Override
  public Color getType() {
    return Color.GREEN;
  }

  public SubColorGreen getSubtype() {
    return subColor;
  }

  @Override
  public int getMyNr() {
    return nr;
  }

  @Override
  public ILeaf<Color> myClone() {
    return new GreenLeaf(subColor, nr);
  }

}
