package core.FuzzyPetriLogic.Controller;

import java.util.Map;

public interface IControllerRecorder {
  default void addInputForController(Map<Integer, Double> inps) {
  }

  default void addOutOfController(Map<Integer, Double> out) {
  }

  public static IControllerRecorder empty() {
    return new IControllerRecorder() {
    };
  }

}
