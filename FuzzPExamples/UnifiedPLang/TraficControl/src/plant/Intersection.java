package plant;

public class Intersection {
  
  static final double MAX_CROSS = 10; 
  static final double CAPACITY = 50.0;
  
  
  Lane inpLanes[];
  Lane outLanes[];
  private InputToIntersection currentInp;
  private boolean openPhaseOne;
  private boolean openPhaseTwo;
  private boolean recentFirstClose;
  private boolean recentFirstOpen;
  private boolean recentSecondClose;
  private boolean recentSecondOpen;


  public Intersection(double initInpLanes[], double initOutLanes[]) {
    inpLanes = new Lane[4];
    outLanes = new Lane[4];
    for (int i = 0; i < 4; i++) {
      inpLanes[i] = new Lane(initInpLanes[i]);
      outLanes[i] = new Lane(initOutLanes[i]);
    }
    openPhaseOne = false;
    openPhaseTwo = false;
    recentFirstClose = false;
    recentSecondClose = false;
  }

  public void setInput(InputToIntersection inp) {
    this.currentInp = inp;
  }

  public void setOpenPhaseOne() {
    openPhaseOne = true;
    recentFirstOpen = true;
  }

  public void setClosePhaseOne() {
    openPhaseOne = false;
    recentFirstClose = true;
  }

  public void setOpenPhaseTwo() {
    openPhaseTwo = true;
    recentSecondOpen = true;
  }

  public void setClosePhaseTwo() {
    openPhaseTwo = false;
    recentSecondClose = true;
  }
  
  public IntersectionOut runTick() {
    for(int i = 0; i < 4; i++){
      inpLanes[i].enter(currentInp.inpLaneEnter[i]);
      outLanes[i].exit(currentInp.outLaneExit[i]);
    }
    if ((openPhaseOne && !recentFirstOpen) || recentFirstClose) {
      crossMAchines(0, 2, 3);
      crossMAchines(2, 0, 1);
    }
    if ((openPhaseTwo && !recentSecondOpen) || recentSecondClose) {
      crossMAchines(1, 3, 0);
      crossMAchines(3, 1, 2);
    }
    recentFirstClose = false;
    recentSecondClose = false;
    recentFirstOpen = false;
    recentSecondOpen = false;
    IntersectionOut intersectionOut = new IntersectionOut();
    for (int i = 0; i < 4; i++) {
      intersectionOut.inpLaneDemand[i] = inpLanes[i].getMachines();
      intersectionOut.outLaneCapacity[i] = CAPACITY - outLanes[i].getMachines();
      intersectionOut.outLaneState[i] = outLanes[i].getMachines();
    }

    return intersectionOut;
  }

  private void crossMAchines(int openLane, int outLane1, int outLane2) {
    double masines = inpLanes[openLane].isOpen();
    outLanes[outLane1].enter(masines * currentInp.currentSplit[openLane]);
    outLanes[outLane2].enter(masines * (1 - currentInp.currentSplit[openLane]));
  }

  public static final class IntersectionOut {
    double inpLaneDemand[];
    double outLaneCapacity[];
    double outLaneState[];

    public IntersectionOut() {
      inpLaneDemand = new double[4];
      outLaneCapacity = new double[4];
      outLaneState = new double[4];

    }
  }
  

  public static final class InputToIntersection {
    double inpLaneEnter[];
    double outLaneExit[];
    double currentSplit[];
  }

  private final static class Lane {
    private double currentMachines;

    Lane(Double currentMachines) {
      this.currentMachines = currentMachines;
    }

    public double isOpen() {
      if (currentMachines > MAX_CROSS) {
        currentMachines -= MAX_CROSS;
        return MAX_CROSS;
      } else {
        double temp = currentMachines;
        currentMachines = 0.0;
        return temp;
      }

    }

    void enter(double d) {
      currentMachines += d;
    }

    void exit(double d) {
      currentMachines -= d;
    }

    double getMachines() {
      return currentMachines;
    }
  }

}
