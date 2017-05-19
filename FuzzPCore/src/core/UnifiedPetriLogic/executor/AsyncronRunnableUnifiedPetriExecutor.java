package core.UnifiedPetriLogic.executor;

import static java.lang.System.currentTimeMillis;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import core.UnifiedPetriLogic.ReadableUnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;

public class AsyncronRunnableUnifiedPetriExecutor extends UnifiedAbstactExecutor implements Runnable {
  private static final long precision = 2;
  private long period;
  private boolean stop;
  private long nextWakeUp;
  private boolean firstTick;
  ConcurrentLinkedQueue<Map<Integer, UnifiedToken>> inputsWaitingToProcess;

  public AsyncronRunnableUnifiedPetriExecutor(ReadableUnifiedPetriNet net, long period) {
    this(net, period, true);
  }

  public AsyncronRunnableUnifiedPetriExecutor(ReadableUnifiedPetriNet net, long period, boolean enableChecking) {
    super(net, enableChecking);
    this.period = period;
    stop = false;
    firstTick = true;
    inputsWaitingToProcess = new ConcurrentLinkedQueue<>();
  }

  public void stop() {
    this.stop = true;
  }

  public void putTokenInInputPlace(Map<Integer, UnifiedToken> token) {
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
      Map<Integer, UnifiedToken> inp = inputsWaitingToProcess.poll();
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
