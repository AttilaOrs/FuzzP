package UnifiedGpProblmes.SymbolicRegression;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class Simulator {

  private static double[] rands = new double[] { 0.09855312166810393, 0.9950588555695256, 0.6073980593871112,
      -0.9837926970414893, -0.2382793156804328, 0.38336515499510415, -0.4063627442623805, -0.5790158885833625,
      -0.5294610035867517, 0.6787291404477349, -0.7354031613161325, 0.07707638961413288, 0.7967725623464484,
      -0.6763866422668507, 0.4473047632061987, 0.09261569845240292, -0.4532658632883708, -0.17203482095336686,
      -0.7389505746378158, -0.7665570002475527, -0.484100350199016, 0.6355188535164942, 0.8971441949158919,
      -0.06106917307548787, 0.3608429223371221, -0.17615642318883507, 0.04292476443800586, 0.05350223366969942,
      0.19145246848788744, 0.29471291995926574, -0.9626935199968638, -0.8301208623973526, -0.9188643830133977,
      0.7548129320605429, -0.3176763872869953, -0.8801190144327294, 0.6539672643851271, 0.6605442017806283,
      -0.745922746005509, -0.6516634586236327, 0.7403497821547252, -0.22471975924263787, 0.2977562053709869,
      0.06181395556742808, 0.10649614354498782, -0.4031239090797495, 0.8202946127572525, 0.7052054911649818,
      -0.0585106876485485, 0.37537039377361303 };

  private static Function<Double, Double> defaultFunc = d -> d * d * d * d + d * d * d + d * d + d;

  private Function<Double, Double> func;
  private double[] points;

  public Simulator() {
    func = defaultFunc;
    points = rands;
  }

  public Double compare(Function<Double, Double> comp) {
    Double sum = 0.0;
    for (int i = 0; i < points.length; i++) {
      Double v = comp.apply(points[i]);
      if (v != null) {
        sum += abs(v - func.apply(points[i]));
      } else {
        sum += abs(2 * func.apply(points[i]));
      }
    }
    return sum;
  }

  public int getLength() {
    return points.length;
  }

  public Map<String, Map<Double, Double>> sim(Function<Double, Double> comp) {
    Map<Double, Double> orig = new HashMap<>();
    Map<Double, Double> sim = new HashMap<>();
    for (double i = -1.0; i <= 1.0; i += 0.01) {
      orig.put(i, func.apply(i));
      Double rez = comp.apply(i);
      if (rez != null) {
        sim.put(i, rez);
      }
    }
    Map<String, Map<Double, Double>> toRet = new HashMap<>();
    toRet.put("orig", orig);
    toRet.put("sim", sim);
    return toRet;
  }

  public static void main(String[] args) {
    ArrayList<Double> rands = new ArrayList<>();
    Random rnd = new Random();
    for(int i = 0; i < 50; i++){
      rands.add(rnd.nextDouble() * ((rnd.nextBoolean() ? 1.0 : -1.0)));
    }
    System.out.println(rands);

  }

}
