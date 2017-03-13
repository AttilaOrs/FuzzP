package core.UnifiedPetriLogic;

import java.util.Map;

public class ScaleDeducer {
  Map<Integer, PlaceScale> currentIdea;
  UnifiedPetriNet net;
  
  public ScaleDeducer(UnifiedPetriNet net) {
    this.net = net;
  }
  
  
  
  
  
  private static class PlaceScale{
    Boolean fixed, hasClue;
    Double scale;
    Integer updated;
    
    public PlaceScale(Boolean fixed, Boolean hasClue ,Double scale, Integer updated) {
      this.fixed = fixed;
      this.scale = scale;
      this.updated = updated;
      this.hasClue = hasClue;
    }
  }
  
 private static PlaceScale inputPlaceScale(Double scale) {
   return new PlaceScale(true, true, scale, -1);
 }
 
 private static PlaceScale initalToken(Double scale) {
   return new PlaceScale(false, true, scale, 0);
 }
 
 private static PlaceScale uknown() {
   return new PlaceScale(false, false, -1.0, 0);
 }
  
}
  
  
  
  

