package UnifiedGp.Tree.TestColorTree;

import UnifiedGp.Tree.ILeaf;

public class RedLeaf implements ILeaf<Color, SubColorRed>, HasNumber {
  SubColorRed subColor;
  int nr;

  public RedLeaf(SubColorRed subColor, int nr) {
    this.subColor = subColor;
    this.nr = nr;
  }

  @Override
  public Color getType() {
    return Color.RED;
  }

  @Override
  public SubColorRed getSubtype() {
    return subColor;
  }

  @Override
  public int getMyNr() {
    return nr;
  }

}
