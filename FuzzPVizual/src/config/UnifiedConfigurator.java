package config;

import java.util.function.BiFunction;
import java.util.function.Function;

import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.FuzzyValue;
import core.UnifiedPetriLogic.DrawableUnifiedPetriNetWithExternalNames;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.tables.Operator;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import structure.DrawableNet;

public class UnifiedConfigurator implements
    IConfigurator<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable, UnifiedPetriNet> {

  private UnifiedPetriNet myNet;

  public UnifiedConfigurator(UnifiedPetriNet petri) {
    this.myNet = petri;
  }

  @Override
  public Function<FuzzyValue, String> getFuzzyToString() {
    return FuzzyValue::unifiedStr;
  }

  @Override
  public Function<Integer, String> transitionCommentSupply() {
    return (trId -> {
      IUnifiedTable table = myNet.getTableForTransition(trId);
      if (table instanceof UnifiedTwoXOneTable) {
        UnifiedTwoXOneTable cast = (UnifiedTwoXOneTable) table;
        return commentForOp(cast.getOpertaor());
      }
      return "";
    });
  }

  private String commentForOp(Operator opertaor) {
    if (opertaor.equals(Operator.None)) {
      return "";
    }
    return "has incorporated Opeartor: " + opertaor.getSign();
  }

  @Override
  public BiFunction<UnifiedPetriNet, TransitionPlaceNameStore, DrawableNet> getDrawableNetFactory() {
    return (upn, tps) -> new DrawableUnifiedPetriNetWithExternalNames(upn, tps);
  }

  @Override
  public Function<UnifiedToken, Double> getDoubleConverter() {
    return UnifiedToken::getValue;
  }

  @Override
  public Function<String, UnifiedToken> getStringConverter() {
    return UnifiedToken::buildFromString;
  }

  @Override
  public Class<UnifiedPetriNet> getPetriClass() {
    return UnifiedPetriNet.class;
  }

}
