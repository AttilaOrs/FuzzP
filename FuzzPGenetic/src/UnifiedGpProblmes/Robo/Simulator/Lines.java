package UnifiedGpProblmes.Robo.Simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import UnifiedGpProblmes.Robo.Simulator.ToRead.Points;

public class Lines {

  public static Points getPoint() {
    String all = "";

    try (BufferedReader br = new BufferedReader(new FileReader("sm.json"))) {
      StringBuilder sb = new StringBuilder();
      all = br.lines().collect(Collectors.joining());
    } catch (Exception e) {
      System.err.println(e);
    }
    Gson gg = new Gson();
    Points f = gg.fromJson(all, Points.class);
    return f;

  }

}
