package core.FuzzyPetriLogic.Executor;

import static java.lang.System.currentTimeMillis;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.ReadableFuzzyPetriNet;

public class AsyncronRunnableExecutor extends AbstractFuzzyExecutor implements Runnable {
	private static final long precision = 2;
	private long period;
	private boolean stop;
	private long nextWakeUp;
	private boolean firstTick;

	ConcurrentLinkedQueue<Map<Integer, FuzzyToken>> inputsWaitingToProcess;

  public AsyncronRunnableExecutor(ReadableFuzzyPetriNet net, long period) {
		this(net, true, period);
	}

  public AsyncronRunnableExecutor(ReadableFuzzyPetriNet net, boolean enableChecking, long period) {
		super(net, enableChecking);
		this.period = period;
		stop = false;
		firstTick = true;
		inputsWaitingToProcess = new ConcurrentLinkedQueue<>();
	}

	public void stop() {
		this.stop = true;
	}

	public void putTokenInInputPlace(Map<Integer, FuzzyToken> token) {
		inputsWaitingToProcess.add(token);
		synchronized (this) {
			notify();
		}
	}

  @Override
  public void run() {
    startTick();
    while (!stop) {

      if (currentTimeMillis() > nextWakeUp) {
        startTick();
      }
      reackToExistingInpus();
      long timeToWait = nextWakeUp - currentTimeMillis();
      if (timeToWait > precision)
        try {
          synchronized (this) {
            wait(nextWakeUp - currentTimeMillis());
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
    }
  }

	private void reackToExistingInpus() {
		while (inputsWaitingToProcess.size() != 0) {
			Map<Integer, FuzzyToken> inp = inputsWaitingToProcess.poll();
			setInputPlacesWithToken(inp);
			executeFirableTransitions();
		}
	}

	private void startTick() {
		if (firstTick) {
			this.nextWakeUp = System.currentTimeMillis();
			firstTick = false;
		} else {
			recorder.tickFinished(delayStateOfTransitions, stateOfPlaces);
		}
		super.updateDelayStateOfTransitionsAllreadyInFire();
		super.executeFirableTransitions();
		sheduleNextWakeUp();

	}

	private void sheduleNextWakeUp() {
		this.nextWakeUp += period;
	}

}
