package UnifiedGpProblmes.Robo.Simulator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.ejml.dense.row.CommonOps_DDRM.add;
import static org.ejml.dense.row.CommonOps_DDRM.mult;

import org.ejml.data.DMatrixRMaj;



public class RoboMovmentSimulator implements IRoboMoovmentDescritions {
  private static final DMatrixRMaj A = new DMatrixRMaj(new double[][] {
      { 0.11039272, -0.02206672, -0.04028361, 0.00261544 },
      { -0.02206672, 0.11039272, 0.00261544, -0.04028361 },
      { 0.07941429, -0.00408547, 0.98910846, 0.00174709 },
      { -0.00408547, 0.07941429, 0.00174709, 0.98910846 },
  });
  private static final DMatrixRMaj B = new DMatrixRMaj(new double[][] {
      { 4.04366647, -0.266526734 },
      { -0.266526734, 4.04366647 },
      { 0.53311875, -0.01707272 },
      { -0.01707272, 0.53311875 },
  });

  private static final DMatrixRMaj C = new DMatrixRMaj(new double[][] {
      { 0.  ,   0. ,    0.001,  0.001},
      { 0., 0., -0.025, 0.025 },
  });
  private static final double dt = 0.2;


  double x, y;


  double alfa;
  DMatrixRMaj X, Xnew;
  DMatrixRMaj interX, U, rez;

  public RoboMovmentSimulator() {

    U = new DMatrixRMaj(new double[][] {
        { 0.0 },
        { 0.0 },
    });
    rez = new DMatrixRMaj(U);
    X = new DMatrixRMaj(new double[][] {
        { 0.0 },
        { 0.0 },
        { 0.0 },
        { 0.0 },
    });
    Xnew = new DMatrixRMaj(X);
    interX = new DMatrixRMaj(X);
    x = 0.0;
    y = 0.0;
    alfa = 0.0;

  }

  public void simulateTimeUnit() {

    mult(A, X, interX);
    mult(B, U, Xnew);
    add(interX, Xnew, Xnew);

    mult(C, X, rez);

    double alfa_new = alfa + rez.get(1, 0) * dt;
    double vx = rez.get(0, 0) * sin(alfa);
    double vy = rez.get(0, 0) * cos(alfa);

    x = x + vx * dt;
    y = y + vy * dt;
    alfa = alfa_new;
    X.set(Xnew);

  }

  public void setLeftCommand(double d) {
    U.set(0, 0, d);
  }

  public void setRightCommand(double d) {
    U.set(1, 0, d);
  }


  @Override
  public double getX() {
    return x;
  }

  @Override
  public double getY() {
    return y;
  }

  @Override
  public double getAlfa() {
    return alfa;
  }
}
