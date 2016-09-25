package Controller;

import java.util.ArrayList;

import Model.FuzzyPVizualModel;
import View.IView;

public class GlobalController implements IGlobalController {

  private FuzzyPVizualModel model;
  private ArrayList<IView> views;
  private int currentlySelectedTransition;

  public GlobalController(FuzzyPVizualModel model) {
    this.model = model;
    views = new ArrayList<>();
    currentlySelectedTransition = -1;
  }

  @Override
  public void addView(IView view) {
    this.views.add(view);
  }

  @Override
  public void placeSelectionReqiest(int placeId) {
    views.forEach(v -> v.placeSelected(placeId));
  }

  @Override
  public void placeDeselectionReqiest(int plId) {
    views.forEach(v -> v.placeDeselect(plId));
  }

  @Override
  public void transitionSelectionRequest(int trId) {
    if (currentlySelectedTransition != -1 && currentlySelectedTransition != trId) {
      views.forEach(v -> v.transitionDeselected(currentlySelectedTransition));
    }
    if (currentlySelectedTransition != trId) {
      currentlySelectedTransition = trId;
      views.forEach(v -> v.transitionSelected(trId));
    }
  }

  @Override
  public void transitionDeselectionRequest(int trId) {
    if (currentlySelectedTransition == trId) {
      views.forEach(v -> v.transitionDeselected(currentlySelectedTransition));
    }
    currentlySelectedTransition = -1;

  }

  @Override
  public void globalModelUpdate() {
    views.forEach(v -> v.reset());
  }

}
