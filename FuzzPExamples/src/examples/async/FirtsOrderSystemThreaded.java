package examples.async;

public class FirtsOrderSystemThreaded implements Runnable {

	double a, b, c, d;
	double x;
	private double currentStatus;

	private volatile double command;
	private boolean stop;
	private long period;

	FirtsOrderSystemThreaded(double a, double b, double c, double d, long period) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		command = 0.0;
		x = 0.0;
		stop = false;
		this.period = period;
	}

	public void setCommand(double cmd) {
		command = cmd;
	}

	public void stop() {
		stop = true;
	}

	public double curentStatus() {
		return currentStatus;
	}

	private void executeSystem() {
		double xNew = a * x + b * command;
		currentStatus = c * x + d * command;
		x = xNew;
	}

	@Override
	public void run() {
		while (!stop) {
			executeSystem();
			try {
				Thread.sleep(period);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
