package UnifiedGpProblmes.Robo.Simulator.ToRead;

import static java.lang.Math.sqrt;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

public class SegmentTest {

  private static final double EPS = 0.0001;

  @Test
  public void first() {
    Segment s = new Segment(new Point(0, 0), new Point(2, 2));
    double rez = s.dist(new Point(2, 0));
    assertTrue(rez - sqrt(2.0) < EPS);
    rez = s.dist(new Point(0, 2));
    assertTrue(rez - sqrt(2.0) < EPS);
    rez = s.dist(new Point(3, 3));
    assertTrue(rez - sqrt(2.0) < EPS);
    rez = s.dist(new Point(-1.0, -1.0));
    assertTrue(rez - sqrt(2.0) < EPS);
  }

  @Test
  public void second() {
    Segment s = new Segment(new Point(2, 2), new Point(0, 0));
    double rez = s.dist(new Point(2, 0));
    assertTrue(rez - sqrt(2.0) < EPS);
    rez = s.dist(new Point(0, 2));
    assertTrue(rez - sqrt(2.0) < EPS);
    rez = s.dist(new Point(3, 3));
    assertTrue(rez - sqrt(2.0) < EPS);
    rez = s.dist(new Point(-1.0, -1.0));
    assertTrue(rez - sqrt(2.0) < EPS);
  }

  @Test
  public void third() {
    Segment s = new Segment(new Point(5, 5), new Point(7, 3));
    double rez = s.dist(new Point(7, 5));
    assertTrue(rez - sqrt(2.0) < EPS);
    rez = s.dist(new Point(4, 2));
    assertTrue(rez - 2 * sqrt(2.0) < EPS);
    rez = s.dist(new Point(6, 4));
    assertTrue(rez < EPS);
    rez = s.dist(new Point(6, 5));
    assertTrue(rez - 1.0 < EPS);
    rez = s.dist(new Point(9, 5));
    assertTrue(rez - 2 * sqrt(2.0) < EPS);
  }
  
  @Test
  public void commonPointExists() {
    Segment s1 = new Segment(new Point(0, 0), new Point(2, 2));
    Segment s2 = new Segment(new Point(2, 0), new Point(0, 2));
    Optional<Point> i = s1.commonPoint(s2);
    assertTrue(i.isPresent());
    assertTrue(Math.abs(i.get().x - 1.0) < EPS);
    assertTrue(Math.abs(i.get().y - 1.0) < EPS);

  }

  @Test
  public void commonPointNotExist_paralell() {
    Segment s1 = new Segment(new Point(0, 0), new Point(2, 2));
    Segment s2 = new Segment(new Point(1, 1), new Point(3, 3));
    Optional<Point> i = s1.commonPoint(s2);
    assertTrue(!i.isPresent());
  }

  @Test
  public void commonPointNotExist_SegemntsDoNotTouch() {
    Segment s1 = new Segment(new Point(0, 0), new Point(2, 2));
    Segment s2 = new Segment(new Point(0, 1), new Point(-1, -2));
    Optional<Point> i = s1.commonPoint(s2);
    assertTrue(!i.isPresent());
  }

}
