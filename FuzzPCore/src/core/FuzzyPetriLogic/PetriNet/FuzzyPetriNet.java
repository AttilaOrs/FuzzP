package core.FuzzyPetriLogic.PetriNet;

import java.util.HashMap;
import java.util.Map;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.common.AbstractPetriNet;

public class FuzzyPetriNet extends AbstractPetriNet<FuzzyToken, ITable, OneXOneTable>
{

  private Map<Integer, Map<Integer, Double>> weights;


  public FuzzyPetriNet() {
    super(FuzzyToken::new);
    weights = new HashMap<>();
  }


  public int addPlace() {
    int toRet = super.addPlace();
    weights.put(toRet, new HashMap<>());
    return toRet;
  }

  public int addInputPlace() {
    int placeId = addPlace();
    isInputPlaces.set(placeId, true);
    return placeId;
  }

  public void addArcFromPlaceToTransition(int place, int transition, double weight) {
    if (fromPlaceToTrans.size() > place) {
      fromPlaceToTrans.get(place).add(transition);
      placesNeededForTrans.get(transition).add(place);
      weights.get(place).put(transition, weight);
    } else {
      throw new RuntimeException(
          "No place with nr " + place + " exists. There are only : " + fromPlaceToTrans.size() + " places");
    }
  }


  public double getWeigth(int placeId, int trId) {
    return weights.get(placeId).get(trId);
  }

}