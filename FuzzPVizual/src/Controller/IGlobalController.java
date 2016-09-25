package Controller;

import View.IView;

public interface IGlobalController {

  public void addView(IView view);

  public void placeSelectionReqiest(int placeId);

  public void placeDeselectionReqiest(int plnr);

  public void transitionSelectionRequest(int trId);

  public void transitionDeselectionRequest(int trId);

  public void globalModelUpdate();
}
