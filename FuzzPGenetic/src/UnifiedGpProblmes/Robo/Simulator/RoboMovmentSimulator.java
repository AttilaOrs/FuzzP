package UnifiedGpProblmes.Robo.Simulator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.ejml.dense.row.CommonOps_DDRM.add;
import static org.ejml.dense.row.CommonOps_DDRM.mult;

import org.ejml.data.DMatrixRMaj;



public class RoboMovmentSimulator {
  private static final DMatrixRMaj A = new DMatrixRMaj(new double[][] {
      { -3.78846264e-03, 5.08458745e-04, -4.36642603e-02, 3.66707433e-03 },
      { 5.08458745e-04, -3.78846264e-03, 3.66707433e-03, -4.36642603e-02 },
      { 8.60566252e-02, -6.07025184e-03, 9.46992583e-01, 8.05998969e-03 },
      { -6.07025184e-03, 8.60566252e-02, 8.05998969e-03, 9.46992583e-01 },
  });
  private static final DMatrixRMaj B = new DMatrixRMaj(new double[][] {
      { 4.45691372, -0.3986134 },
      { -0.3986134, 4.45691372 },
      { 3.12397131, -0.19517849 },
      { -0.19517849, 3.12397131 },
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


  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getAlfa() {
    return alfa;
  }
}
