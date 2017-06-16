package UnifiedGp.Tree.TestColorTree;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;

public class ColoredInnerNode implements IInnerNode<Color>, HasNumber {
  int nr;
  Color myColor;
  INode<Color> firstChild;
  INode<Color> secondChild;

  public ColoredInnerNode(int nr, Color myColor, INode<Color> firstChild, INode<Color> secondChild) {
    this.nr = nr;
    this.myColor = myColor;
    this.firstChild = firstChild;
    this.secondChild = secondChild;
  }

  @Override
  public Color getType() {
    return myColor;
  }

  @Override
  public INode<Color> getFirstChild() {
    return firstChild;
  }

  @Override
  public INode<Color> getSecondChild() {
    return secondChild;
  }

  @Override
  public int getMyNr() {
    return nr;
  }
}
