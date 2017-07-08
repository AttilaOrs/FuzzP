package UnifiedGpProblmes.CartCentering;

import UnifiedGpProblmes.CartCentering.CartController.command;

public class CartModel {

  private static final double tau = 0.15;
  double currentPos = 0;
  double currentSpeed = 0;

  public CartModel(double pos) {
    currentPos = pos;
  }

  public double[] runTick(command rez) {
    double newSpeed = currentSpeed + 2 * tau * (rez == command.Pos ? 1.0 : ((rez == command.Neg) ? -1.0 : 0.0));
    double newPos = currentPos + tau * currentSpeed;
    double toRet[] = new double[] { currentSpeed, currentPos };
    currentSpeed = newSpeed;
    currentPos = newPos;
    return toRet;
  }

}
