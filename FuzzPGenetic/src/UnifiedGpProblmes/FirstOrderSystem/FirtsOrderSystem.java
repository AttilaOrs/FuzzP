package UnifiedGpProblmes.FirstOrderSystem;

import java.util.ArrayList;

public class FirtsOrderSystem {

  final double a, b, c, d;
	double x;
	private double currentStatus;
  private ArrayList<Double> evolution;

  private double command;


  public FirtsOrderSystem(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		command = 0.0;
		x = 0.0;
    evolution = new ArrayList<>(200);
	}

	public void setCommand(double cmd) {
		command = cmd;
	}


	public double curentStatus() {
		return currentStatus;
	}

	private void executeSystem() {
		double xNew = a * x + b * command;
		currentStatus = c * x + d * command;
		x = xNew;
	}

  public void runTick() {
    executeSystem();
    evolution.add(currentStatus);
	}

  public ArrayList<Double> getEvolution() {
    return evolution;
  }


}
