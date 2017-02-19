package config;

import java.util.function.BiFunction;
import java.util.function.Function;

import core.Drawable.DrawableNetWithExternalNames;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import structure.DrawableNet;

public class FuzzyConfigurator implements
    IConfigurator<FuzzyToken, ITable, OneXOneTable, FuzzyPetriNet> {

  @Override
  public Function<FuzzyValue, String> getFuzzyToString() {
    return FuzzyValue::name;
  }

  @Override
  public Function<Integer, String> transitionCommentSupply() {
    return (i -> "");
  }

  @Override
  public BiFunction<FuzzyPetriNet, TransitionPlaceNameStore, DrawableNet> getDrawableNetFactory() {
    return (fn, ns) -> new DrawableNetWithExternalNames(fn, ns);
  }

  FuzzyDriver defaultDriver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);

  @Override
  public Function<FuzzyToken, Double> getDoubleConverter() {
    return tk -> defaultDriver.defuzzify(tk);
  }

  @Override
  public Function<String, FuzzyToken> getStringConverter() {
    return FuzzyToken::buildFromString;
  }

  @Override
  public Class<FuzzyPetriNet> getPetriClass() {
    return FuzzyPetriNet.class;
  }

}
