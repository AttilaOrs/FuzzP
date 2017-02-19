package core.UnifiedPetriLogic;

import core.FuzzyPetriLogic.FuzzyToken;

public interface IContex {

  FuzzyToken fuzzyfieFirstInp(UnifiedToken tk);

  FuzzyToken fuzzyfieSecondInp(UnifiedToken tk);

  UnifiedToken defuzzyfieFirstOutput(FuzzyToken tk);

  UnifiedToken defuzzyfieSecondOuput(FuzzyToken tk);



}
