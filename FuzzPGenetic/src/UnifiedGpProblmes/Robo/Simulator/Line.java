package UnifiedGpProblmes.Robo.Simulator;

import java.util.Optional;

import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;

public class Line {

  final double A, B, C;

  public Line(double A, double B, double C) {
    this.A = A;
    this.C = C;
    this.B = B;
  }

  public Point orthogonalFromPoint(Point from) {
    double den = A * A + B * B;
    double c_y = A * A * from.y - A * B * from.x - B * C;
    c_y = c_y / den;
    double c_x = B * B * from.x - A * B * from.y - A * C;
    c_x = c_x / den;
    Point ortho = new Point(c_x, c_y);
    return ortho;
  }

  public Optional<Point> commonWith(Line o) {
    double den = B * o.A - o.B * A;
    if (den < 0.0000000000000000000000000000001) {
      return Optional.empty();
    }
    double y = (o.C * A - C * o.A) / den;
    double x = (o.C * B - C * o.B) / (den * (-1.0));
    return Optional.of(new Point(x, y));
  }
}
