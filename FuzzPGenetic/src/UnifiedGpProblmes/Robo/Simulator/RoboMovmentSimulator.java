package UnifiedGpProblmes.Robo.Simulator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.ejml.dense.row.CommonOps_DDRM.add;
import static org.ejml.dense.row.CommonOps_DDRM.mult;

import org.ejml.data.DMatrixRMaj;



public class RoboMovmentSimulator implements IRoboMoovmentDescritions {
  private static final DMatrixRMaj A = new DMatrixRMaj(new double[][] {
      { 3.33310176e-01, -3.32858112e-02, -3.02867020e-02, 1.22824033e-03 },
      { -3.32858112e-02, 3.33310176e-01, 1.22824033e-03, -3.02867020e-02 },
      { 5.97262866e-02, -1.61517436e-03, 9.95448987e-01, 8.16119537e-04 },
      { -1.61517436e-03, 5.97262866e-02, 8.16119537e-04, 9.95448987e-01 },
  });
  private static final DMatrixRMaj B = new DMatrixRMaj(new double[][] {
      { 3.03370358e+00, -1.24380753e-01 },
      { -1.24380753e-01, 3.03370358e+00 },
      { 1.76265092e-01, -2.44947903e-03 },
      { -2.44947903e-03, 1.76265092e-01 },
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
