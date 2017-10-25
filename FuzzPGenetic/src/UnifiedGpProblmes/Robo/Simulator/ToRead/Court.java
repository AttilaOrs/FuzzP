package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import commonUtil.PlotUtils;

public class Court {
  private static Court first = null;
  private static Court second = null;
  private static Court third = null;
  private static Court fourth = null;
  private static Court maze = null;

  public static Court getFirst() {
    if (first == null) {
      first = readFromJson("ffCourt.json");
    }
    return first;
  }

  public static Court getSecond() {
    if (second == null) {
      second = readFromJson("ssCourt.json");
    }
    return second;
  }

  public static Court getThird() {
    if (third == null) {
      third = readFromJson("thCourt.json");
    }
    return third;
  }

  public static Court getFourth() {
    if (fourth == null) {
      fourth = readFromJson("fourthCourt.json");
    }
    return fourth;
  }

  public static Court getMaze() {
    if (maze == null) {
      maze = readFromJson("maze.json");
    }
    return maze;
  }

  private static Court readFromJson(String file) {
    String all = null;
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      StringBuilder sb = new StringBuilder();
      all = br.lines().collect(Collectors.joining());
    } catch (Exception e) {
      System.err.println(e);
    }
    Gson gg = new Gson();
    Court f = gg.fromJson(all, Court.class);
    return f;
  }

  private final Segments lines;
  private final Segments walls;

  public Court(Segments lines, Segments walls) {
    this.lines = lines;
    this.walls = walls;
  }

  public Segments getLines() {
    return lines;
  }

  public Segments getWalls() {
    return walls;
  }

  public Court myClone() {
    return new Court(lines.myClone(), walls.myClone());

  }

  public static void main(String[] args) {

    ArrayList<Segment> i = new ArrayList<>();
    i.add(new Segment(new Point(-2.10, -0.10), new Point(0.30, -0.10)));
    i.add(new Segment(new Point(-2.10, 2.50), new Point(0.30, 2.50)));
    i.add(new Segment(new Point(0.30, -0.10), new Point(0.30, 2.50)));
    i.add(new Segment(new Point(-0.30, -0.10), new Point(-0.30, 2.10)));
    i.add(new Segment(new Point(-0.90, 0.50), new Point(-0.90, 2.50)));
    i.add(new Segment(new Point(-1.50, -0.10), new Point(-1.50, 2.10)));
    i.add(new Segment(new Point(-2.10, -0.10), new Point(-2.10, 2.50)));
    Segments ww = new Segments(i);

    ArrayList<Segment> w = new ArrayList<>();
    w.add(new Segment(new Point(0, 0), new Point(0, 2.30)));
    w.add(new Segment(new Point(0, 2.30), new Point(-0.60, 2.30)));
    w.add(new Segment(new Point(-0.60, 2.30), new Point(-0.60, 0.20)));
    w.add(new Segment(new Point(-0.60, 0.20), new Point(-1.20, 0.20)));
    w.add(new Segment(new Point(-1.20, 0.20), new Point(-1.20, 2.30)));
    w.add(new Segment(new Point(-1.20, 2.30), new Point(-1.80, 2.30)));
    w.add(new Segment(new Point(-1.80, 2.30), new Point(-1.80, 0.20)));
    Segments ss = new Segments(w);
    Court c = new Court(ss, ww);
    Gson g = new Gson();
    String jsnStr = g.toJson(c);
    PlotUtils.writeToFile("maze.json", jsnStr);

  }


}
