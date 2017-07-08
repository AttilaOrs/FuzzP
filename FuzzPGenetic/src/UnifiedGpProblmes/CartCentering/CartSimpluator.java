package UnifiedGpProblmes.CartCentering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import UnifiedGpProblmes.CartCentering.CartController.command;

public class CartSimpluator {

  private int tickCntr;
  private double pos;
  private boolean log;
  private Map<String, List<Double>> logg;

  private static final String SPEED = "speed";
  private static final String POS = "psoition";

  public CartSimpluator(int i, double pos, boolean log) {
    this.tickCntr = i;
    this.pos = pos;
    this.log = log;
    if (log) {
      logg = new HashMap<>();
      getLogg().put(SPEED, new ArrayList<>());
      getLogg().put(POS, new ArrayList<>());
    }

  }

  public double sim(CartController c) {
    CartModel model = new CartModel(pos);
    double[] d = new double[]{0.0, pos};
    double sum = 0.0;
    for (int i = 0; i < tickCntr; i++) {
      command rez = c.control(d);
      d = model.runTick(rez);
      sum += Math.abs(d[1]);
      if (log) {
        getLogg().get(SPEED).add(d[0]);
        getLogg().get(POS).add(d[1]);
      }
    }
    return sum;
  }

  public Map<String, List<Double>> getLogg() {
    return logg;
  }


}
