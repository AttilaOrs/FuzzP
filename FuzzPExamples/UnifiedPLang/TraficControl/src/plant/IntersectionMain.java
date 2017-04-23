package plant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

import Main.Plotter;
import plant.Intersection.InputToIntersection;
import plant.Intersection.IntersectionOut;

public class IntersectionMain {

  static Map<String, List<Double>> toSave;

  public static void main(String args[]) {
    double initalInputLane[] = new double[] { 25.0, 30.0, 16.0, 20.0 };
    double initalOuputLane[] = new double[] { 27.0, 32.0, 19.0, 22.0 };
    Intersection intersectin = new Intersection(initalInputLane, initalOuputLane);
    toSave = new HashMap<>();
    double prevOutLane[] = new double[] { 0.0, 0.0, 0.0, 0.0 };

    for (int i = 0; i <= 20; i++) {
      Intersection.InputToIntersection ii = new InputToIntersection();
      ii.currentSplit = new double[] { 0.8, 0.7, 0.7, 0.8 };
      ii.inpLaneEnter = generateEnter();
      ii.outLaneExit = generateOut(prevOutLane);
      intersectin.setInput(ii);

      if (i % 10 == 1) {
        intersectin.setOpenPhaseOne();
      }
      if (i % 10 == 4) {
        intersectin.setClosePhaseOne();
      }
      if (i % 10 == 5) {
        intersectin.setOpenPhaseTwo();
      } 
      if (i % 10 == 0) {
        intersectin.setClosePhaseTwo();
      }
      IntersectionOut rez = intersectin.runTick();
      save(rez.inpLaneDemand, "iDem");
      save(rez.outLaneCapacity, "oCap");
      prevOutLane = rez.outLaneState;


    }
    Plotter p = new Plotter(toSave);
    p.makeJpg("Intersection.jpg");
    writeDatFile("intersection.dat");


  }

  private static double maxed(double d) {
    return (d > 10.0) ? 10 : d;

  }

  private static double[] generateOut(double[] prevOutLane) {
    double toRet[] = new double[]{
        maxed(rnd.nextDouble() * prevOutLane[0]),
        maxed(rnd.nextDouble() * prevOutLane[1] * 0.5),
        maxed(rnd.nextDouble() * prevOutLane[2] * 0.8),
        maxed(rnd.nextDouble() * prevOutLane[3] * 0.4),
    };

    save(toRet, "o~");
    return toRet;
  }

  private static Random rnd = new Random();

  private static double[] generateEnter() {
    double toRet[] = new double[] {
        rnd.nextDouble() * 5.0 + 0.0,
        rnd.nextDouble() * 3.0 + 3.0,
        rnd.nextDouble() * 2.0 + 1.0,
        rnd.nextDouble() * 4.0 + 1.0,
    };
    String prefix = "e~";
    save(toRet, prefix);
    return toRet;
  }

  private static void save(double[] toRet, String prefix) {
    for (int i = 0; i < 4; i++) {
      if (!toSave.containsKey(prefix + i)) {
        toSave.put(prefix + i, new ArrayList<>());
      }
      toSave.get(prefix + i).add(toRet[i]);
    }
  }

  private static void writeDatFile(String fileName) {
    StringBuilder bld = new StringBuilder();
    TreeSet<String> orderedKeys = new TreeSet<>(toSave.keySet());
    bld.append("#tick ");
    for (String name : orderedKeys) {
      bld.append(name).append(" ");
    }
    bld.append("\n");
    List<Double> oneOfDatas = toSave.get(orderedKeys.toArray()[0]);
    for (int i = 0; i < oneOfDatas.size(); i++) {
      bld.append(i).append(" ");
      for (String name : orderedKeys) {
        bld.append(toSave.get(name).get(i)).append(" ");
      }
      bld.append("\n");

    }
    try {
      Files.write(Paths.get(fileName), Arrays.asList(bld.toString()));
    } catch (IOException e) {
      System.err.println("probably wrong filename " + fileName);
      e.printStackTrace();
    }

  }


}
