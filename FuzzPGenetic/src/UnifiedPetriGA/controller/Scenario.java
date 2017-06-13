package UnifiedPetriGA.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class Scenario {
  private static final double MAX = 50.0;
  List<double[]> values;

  public Scenario() {
    this(new ArrayList<>());
  }

  public Scenario(List<double[]> values) {
    this.values = values;
  }

  public void initRadnomScenario(int length) {
    Random rnd = new Random();
    for (int i = 0; i < length; i++) {
      double[] dd = new double[] { rnd.nextDouble() * MAX, rnd.nextDouble() * MAX, rnd.nextDouble() * MAX,
          rnd.nextDouble() * MAX };
      values.add(dd);
    }
  }

  public static void saveJson(String fileName, Scenario sc) {
    String json = new Gson().toJson(sc);
    try {
      Files.write(Paths.get(fileName), json.getBytes());
    } catch (IOException e) {
      System.err.println("Probably wrong filename");
      e.printStackTrace();
    }
  }

  public static Scenario readFromJson(String fileName) {
    Scenario sc = new Scenario();
    try {
      String jsonStr = String.join("\n", Files.readAllLines(Paths.get(fileName)));
      sc = (new Gson()).fromJson(jsonStr, Scenario.class);
    } catch (IOException e) {
      System.err.println("Probably wrong filename");
      e.printStackTrace();
    }
    return sc;
  }

  public int getLength() {
    return values.size();

  }

  public double[] getValuesForTick(int i) {
    return values.get(i);
  }

  public Scenario myClone() {
    List<double[]> clonedValues = values.stream().map(d -> Arrays.copyOf(d, 4)).collect(Collectors.toList());
    return new Scenario(clonedValues);
  }

}
