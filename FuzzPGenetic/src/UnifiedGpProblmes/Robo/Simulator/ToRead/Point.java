package UnifiedGpProblmes.Robo.Simulator.ToRead;

public class Point {
  public final double x, y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Point relative(Point to) {
    return new Point(to.x - x, to.y - y);
  }

  @Override
  public String toString() {
    return "[" + x + "," + y + "]";
  }

  public Point myClone() {
    return new Point(x, y);
  }
}
