package examples.syncronous;

public class FirstOrderSystem {

	double a, b, c, d;
	double x;

	FirstOrderSystem(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		x = 0.0;
	}

	double executeSystem(double command) {
		double xNew = a * x + b * command;
		double toRet = c * x + d * command;
		x = xNew;
		return toRet;
	}
}
