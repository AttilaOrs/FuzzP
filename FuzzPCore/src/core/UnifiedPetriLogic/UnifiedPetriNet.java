package core.UnifiedPetriLogic;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import core.FuzzyPetriLogic.FuzzyToken;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.common.AbstractPetriNet;

public class UnifiedPetriNet extends AbstractPetriNet<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable> {

  private transient HashMap<Integer, IContex> storedContexts;
  protected List<Double> scaleForPlace;

  public UnifiedPetriNet() {
    super(UnifiedToken::new);
    scaleForPlace = new ArrayList<>();
    storedContexts = new HashMap<>();
  }


  public IContex getContextForTransition(int trId) {
    if (!storedContexts.containsKey(trId)) {
      storedContexts.put(trId,
          new MyContext(placesNeededForTrans.get(trId).stream().map(scaleForPlace::get),
              fromTransToPlace.get(trId).stream().map(scaleForPlace::get)));
    }
    return storedContexts.get(trId);

  }

  public double getScale(int placeId) {
    return this.scaleForPlace.get(placeId);
  }



  public int addPlace(Double scale) {
    int toRet = super.addPlace();
    scaleForPlace.add(scale);
    return toRet;
  }

  public int addInputPlace(Double scale) {
    int placeId = addPlace(scale);
    isInputPlaces.set(placeId, true);
    return placeId;
  }

  public void addArcFromPlaceToTransition(int place, int transition) {
    if (fromPlaceToTrans.size() > place) {
      fromPlaceToTrans.get(place).add(transition);
      placesNeededForTrans.get(transition).add(place);
    } else {
      throw new RuntimeException(
          "No place with nr " + place + " exists. There are only : " + fromPlaceToTrans.size() + " places");
    }
  }






  static class MyContext implements IContex {
    List<TokenConverter> inpDrivers, outDrivers;


    public MyContext(Stream<Double> inpScales, Stream<Double> outScales) {
      inpDrivers = inpScales.map(TokenConverter::new).collect(toList());
      outDrivers = outScales.map(TokenConverter::new).collect(toList());
    }

    @Override
    public FuzzyToken fuzzyfieFirstInp(UnifiedToken utk) {
      return inpDrivers.get(0).conver(utk);
    }

    @Override
    public FuzzyToken fuzzyfieSecondInp(UnifiedToken utk) {
      return inpDrivers.get(1).conver(utk);
    }

    TokenConverter defaultConv;

    @Override
    public UnifiedToken defuzzyfieFirstOutput(FuzzyToken tk) {
      if (outDrivers.isEmpty()) {
        if (defaultConv == null) {
          defaultConv = TokenConverter.defaultConverter();
        }
        return defaultConv.convert(tk);
      }
      return outDrivers.get(0).convert(tk);
    }

    @Override
    public UnifiedToken defuzzyfieSecondOuput(FuzzyToken tk) {
      return outDrivers.get(1).convert(tk);
    }

    @Override
    public UnifiedToken createScaleMamiximexForFirstOutput(Double d) {
      return outDrivers.get(0).createMaxedScale(d);
    }

    @Override
    public UnifiedToken createScaleMamiximexForSecondOuput(Double d) {
      return outDrivers.get(1).createMaxedScale(d);
    }

  }

}
