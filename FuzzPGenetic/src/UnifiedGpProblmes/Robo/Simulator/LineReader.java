package UnifiedGpProblmes.Robo.Simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segment;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Segments;

public class LineReader {

  public static Segments getProblem() {
    return getPoint("sm2.json", 0.40, new Point(0, 0));

  }

  public static Segments getPoint(String file, double firstSegmentLEngth, Point fiPoint) {
    String all = "";

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      StringBuilder sb = new StringBuilder();
      all = br.lines().collect(Collectors.joining());
    } catch (Exception e) {
      System.err.println(e);
    }
    Gson gg = new Gson();
    ReadClass f = gg.fromJson(all, ReadClass.class);
    return new Segments(createContunousSegments(firstSegmentLEngth, f, fiPoint));
  }

  private static ArrayList<Segment> createContunousSegments(double firstSegLenght, ReadClass c, Point first) {
    ArrayList<Segment> segments = new ArrayList<>();
    Point p0 = c.Elements.get(0);
    Point last = first;
    Double multi = null;
    for (int i = 1; i < c.Elements.size(); i++) {
      Point newLast = p0.relative(c.Elements.get(i));
      if (multi == null) {
        // the first segment is 2 m long
        multi = Math.sqrt(newLast.x * newLast.x + newLast.y * newLast.y) * (1.0 / firstSegLenght);
      }
      newLast = new Point(newLast.x / multi + first.x, newLast.y / multi + first.y);
      if (last.distance(newLast) < 0.001) {
        break;
      }
      segments.add(new Segment(last, newLast));
      last = newLast;
    }
    // segments.remove(segments.size() - 1);

    return segments;
  }

  public static class ReadClass {
    public List<Point> Elements;

  }

}
