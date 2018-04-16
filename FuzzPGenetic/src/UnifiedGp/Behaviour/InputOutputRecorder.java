package UnifiedGp.Behaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.IGeneralPetriBehavoiurRecorder;

public class InputOutputRecorder implements IGeneralPetriBehavoiurRecorder<UnifiedToken> {
  List<Map<Integer, UnifiedToken>> inps;
  List<Map<Integer, UnifiedToken>> outs;
  Map<Integer, UnifiedToken> currentInp;
  Map<Integer, UnifiedToken> currentOut;

  public InputOutputRecorder() {
    inps = new ArrayList<>();
    outs = new ArrayList<>();
    currentInp = new HashMap<>();
    currentOut = new HashMap<>();
  }

  @Override
  public void tickFinished(List<Integer> delayStateOfTransition, List<UnifiedToken> placeState) {
    inps.add(currentInp);
    outs.add(currentOut);
    currentInp = new HashMap<>();
    currentOut = new HashMap<>();
  };

  @Override
  public void ouputTransitionFired(int trId, UnifiedToken tk) {
    currentOut.put(trId, tk.myClone());
  };

  @Override
  public void inputPuttedInPlace(int placeId, UnifiedToken tk) {
    currentInp.put(placeId, tk.myClone());
  };
}
