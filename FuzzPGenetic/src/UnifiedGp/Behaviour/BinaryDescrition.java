package UnifiedGp.Behaviour;

import java.util.BitSet;

public class BinaryDescrition {

  private final BitSet bits;
  private final int logicalLength;

  public BinaryDescrition(BitSet bits, int logicalLength) {
    this.bits = bits;
    this.logicalLength = logicalLength;
  }

  public BitSet getBits() {
    return this.bits;
  }

  public int getLength() {
    return this.logicalLength;
  }
}
