package FuzzyPLang.NetBuilder;

import java.util.HashMap;
import java.util.Map;

import core.UnifiedPetriLogic.IUnifiedTable;

public class HiearchicalIntermediateUnifiedNet
    extends AbstactHierachicalIntermediateNet<IUnifiedTable, HiearchicalIntermediateUnifiedNet> {

  private Map<String, Double> inpScale;

  public HiearchicalIntermediateUnifiedNet() {
    super(HiearchicalIntermediateUnifiedNet::new);
    inpScale = new HashMap<>();

  }


  public void addInpPlace(StaticScope sub, String str, double scale) {
    if (sub.current()) {
      if (!inpPlaces.contains(str)) {
        inpPlaces.add(str);
        inpScale.put(str, scale);
      }
    } else {
      String subName = sub.removeFirstSub();
      getDeclarations().get(subName).addInpPlace(sub, str, scale);
    }
  }

}
