package UnifiedGpProblmes.RoomHeatControl.Behavour.BinaryDesc;

import java.util.BitSet;

import UnifiedGp.Behaviour.BinaryDescrition;
import UnifiedGpProblmes.RoomHeatControl.Behavour.FullHeatControllSimpleDescription;

public class FullHeatBinaryDescripton extends BinaryDescrition {


  FullHeatControllSimpleDescription originalDesc;

  public FullHeatBinaryDescripton(BitSet bits, int logicalLength) {
    super(bits, logicalLength);
  }

  public FullHeatControllSimpleDescription getOriginalDesc() {
    return originalDesc;
  }

  public void setOriginalDesc(FullHeatControllSimpleDescription originalDesc) {
    this.originalDesc = originalDesc;
  }

}
