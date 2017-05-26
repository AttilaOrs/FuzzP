package config;

import java.util.function.BiFunction;
import java.util.function.Function;

import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.FuzzyValue;
import core.common.AbstractPetriNet;
import core.common.generaltable.IGeneralTable;
import core.common.recoder.FullRecordable;
import structure.DrawableNet;

public interface IConfigurator<TTokenType extends FullRecordable<TTokenType>, TTableType extends IGeneralTable, TOuTableType extends TTableType, TPetriNetType extends AbstractPetriNet<TTokenType, TTableType, TOuTableType>> {

  Function<FuzzyValue, String> getFuzzyToString();

  Function<Integer, String> transitionCommentSupply();

  BiFunction<TPetriNetType, TransitionPlaceNameStore, DrawableNet> getDrawableNetFactory();

  Function<TTokenType, Double> getDoubleConverter();

  Function<String, TTokenType> getStringConverter();

  Class<TPetriNetType> getPetriClass();

}
