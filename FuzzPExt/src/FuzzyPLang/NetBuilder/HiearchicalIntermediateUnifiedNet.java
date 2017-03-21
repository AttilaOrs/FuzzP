package FuzzyPLang.NetBuilder;

import java.util.HashMap;
import java.util.Map;

import core.UnifiedPetriLogic.IUnifiedTable;

public class HiearchicalIntermediateUnifiedNet
    extends AbstactHierachicalIntermediateNet<IUnifiedTable, HiearchicalIntermediateUnifiedNet> {

  private Map<String, Double> scaleMap;

  public HiearchicalIntermediateUnifiedNet() {
    super(HiearchicalIntermediateUnifiedNet::new);
    scaleMap = new HashMap<>();

  }


  public void addInpPlace(StaticScope sub, String str, double scale) {
    if (sub.current()) {
      if (!inpPlaces.contains(str)) {
        inpPlaces.add(str);
        scaleMap.put(str, scale);
      }
    } else {
      String subName = sub.removeFirstSub();
      getDeclarations().get(subName).addInpPlace(sub, str, scale);
    }
  }
  
  public Double getScales(String inpPlaceName) {
    return scaleMap.get(inpPlaceName);
  }

  public void addPlaceWithScale(StaticScope sub, String str, double scale) {
    if (sub.current()) {
      if (!places.contains(str)) {
        places.add(str);
        scaleMap.put(str, scale);
      }
    } else {
      String subName = sub.removeFirstSub();
      getDeclarations().get(subName).addPlaceWithScale(sub, str, scale);
    }


  }

}
