package UnifiedGpProblmes.CartCentering;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CartSimpluatorTest {

  @Test
  public void test() {
    CartSimpluator sim = new CartSimpluator(50, 10.0, false);
    double opt = sim.sim(new MyController());
    double notOpt = sim.sim(new MyControllerTwo());
    assertTrue(opt < notOpt);
  }

  class MyController implements CartController {

    @Override
    public command control(double[] st) {
      double v = st[0];
      double x = st[1];
      if (-1.0 * x > v * Math.abs(v)) {
        return command.Pos;
      }
      return command.Neg;
    }

  }

  class MyControllerTwo implements CartController {

    @Override
    public command control(double[] st) {
      return command.Non;
    }

  }

}
