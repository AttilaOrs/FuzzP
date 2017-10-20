package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class Court {
  private static Court first = null;
  private static Court second = null;
  private static Court third = null;
  private static Court fourth = null;

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

  public static void main(String[] args) {
    System.out.println(getFirst().lines.getSmallSegments().size());
    System.out.println(getSecond().lines.getSmallSegments().size());
    System.out.println(getThird().lines.getSmallSegments().size());
    System.out.println(getFourth().lines.getSmallSegments().size());


  }


}
