package core.FuzzyPetriLogic;

import core.FuzzyPetriLogic.Fuzzifiers.TriangleFuzzyfier;

public interface FuzzyDriver extends IFuzzyfier, IDefuzzifier {

  public static FuzzyDriver createDriverFromMinMax(double min, double max) {
    double dsitStep = (max - min) / 4.0;
    return TriangleFuzzyfier.withBorderVales(min, min + dsitStep, min + 2 * dsitStep, min + 3 * dsitStep, max);
  }

}
