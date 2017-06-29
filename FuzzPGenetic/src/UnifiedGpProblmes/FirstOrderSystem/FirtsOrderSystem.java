package UnifiedGpProblmes.FirstOrderSystem;

import java.util.ArrayList;

public class FirtsOrderSystem {

  final double a, b, c, d;
	double x;
	private double currentStatus;
  private ArrayList<Double> evolution;

  private Double command;


  public FirtsOrderSystem(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
    command = null;
		x = 0.0;
    evolution = new ArrayList<>(200);
	}

	public void setCommand(double cmd) {
    if (command == null) {
      command = cmd;
    }
	}


	public double curentStatus() {
		return currentStatus;
	}

	private void executeSystem() {
    if (command == null) {
      command = 0.0;
    }
		double xNew = a * x + b * command;
		currentStatus = c * x + d * command;
		x = xNew;
    command = null;
	}

  public void runTick() {
    executeSystem();
    evolution.add(currentStatus);
	}

  public ArrayList<Double> getEvolution() {
    return evolution;
  }


}
