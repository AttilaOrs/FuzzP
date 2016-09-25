package View;

import Controller.IGlobalController;

public interface IView {
  default void placeSelected(int plId) {
  }

  default void placeDeselect(int plId) {
  }

  default void transitionSelected(int trId) {
  }

  default void transitionDeselected(int currentlySelectedTransition) {
  }

  void reset();

  void setController(IGlobalController controller);

}
