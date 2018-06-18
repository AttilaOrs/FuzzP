package UnifiedGpProblmes.Robo.Behaviour;

import java.util.BitSet;

import UnifiedGp.Behaviour.BinaryDescrition;

public class RoboBihaviourBinaryDescription extends BinaryDescrition{
  

  private int segmentsTouched;
  private int segmentsTouchedInOrder;
  private int wallsTouched;
  private int size;

  public RoboBihaviourBinaryDescription(BitSet bits, int logicalLength) {
    super(bits, logicalLength);
  }
  
  public void setSegmentTouchedData(int segmentsTouched, int segmentsTouchedInOrder, int wallsTouched){
    this.segmentsTouched = segmentsTouched;
    this.segmentsTouchedInOrder =segmentsTouchedInOrder;
    this.wallsTouched = wallsTouched;
  }
  
  public void setSize(int size) {
    this.size = size;
  }
  public int getSegmentsTouched() {
    return segmentsTouched;
  }

  public int getSegmentsTouchedInOrder() {
    return segmentsTouchedInOrder;
  }

  public int getWallsTouched() {
    return wallsTouched;
  }

  public int getSize() {
    return size;
  }
}
