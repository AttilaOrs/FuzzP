package UnifiedGpProblmes.ArtificalAnt.Behaviour;

import java.util.BitSet;

import UnifiedGp.Behaviour.BinaryDescrition;

public class AntBinaryDescription extends BinaryDescrition {

  int nrOfFoodEaten;
  double multi;
  private int size;


  public AntBinaryDescription(BitSet bits, int logicalLength) {
    super(bits, logicalLength);
    nrOfFoodEaten = -1;
    multi = 1.0;
  }

  public int getNrOfFoodEaten() {
    return nrOfFoodEaten;
  }
  public double getMulti() {
    return this.multi;
  }

  public int getSize() {
    return this.size;
  }

  public void setPropiertise(int nrOfFoodEaten, double multi, int size) {
    this.nrOfFoodEaten = nrOfFoodEaten;
    this.multi = multi;
    this.size = size;
  }
}
