package UnifiedGpProblmes.CartCentering;

public interface CartController {

  command control(double[] st);

  enum command {
    Pos, Neg, Non
  }

}
