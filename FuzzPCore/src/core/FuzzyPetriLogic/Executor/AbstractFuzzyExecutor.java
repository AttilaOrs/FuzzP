package core.FuzzyPetriLogic.Executor;

import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.ReadableFuzzyPetriNet;
import core.FuzzyPetriLogic.Fuzzifiers.TriangleFuzzyfier;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNetChecker;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.common.AbstractExecutor;

public abstract class AbstractFuzzyExecutor
    extends AbstractExecutor<FuzzyToken, ITable, OneXOneTable, ReadableFuzzyPetriNet> {


  private TriangleFuzzyfier defultFuzzificator = TriangleFuzzyfier.withBorderVales(-1.0, -0.5, 0.0, 0.5, 1.0);


  public AbstractFuzzyExecutor(ReadableFuzzyPetriNet net, boolean enablechecking) {
    super(net, enablechecking);
  }

  public AbstractFuzzyExecutor(ReadableFuzzyPetriNet net) {
    super(net, true);
  }

  @Override
  protected void checkPetriNet() {
    FuzzyPetriNetChecker ch = new FuzzyPetriNetChecker();
    if (!ch.checkPetriNet(myNet)) {
      throw new RuntimeException(ch.getErrorMsg());
    }

  }

  @Override
  protected FuzzyToken[] emptyTokenArray() {
    return new FuzzyToken[0];
  }

  @Override
  protected FuzzyToken[] tokenArray(int size) {
    return new FuzzyToken[size];
  }

  @Override
  protected FuzzyToken phi() {
    return new FuzzyToken();
  }

  @Override
  protected FuzzyToken transformOnArc(FuzzyToken toke, int trId, int plId) {
			if (!toke.isPhi()) {
      Double val = (defultFuzzificator.defuzzify(toke)) * myNet.getWeigth(plId, trId);
				FuzzyToken transformed = defultFuzzificator.fuzzifie(val);
				return transformed;
			} else {
      return new FuzzyToken();
			}
  }

  @Override
  protected boolean executable(FuzzyToken[] inps, int trId) {
    return myNet.getTableForTransition(trId).executable(inps);
  }

  @Override
  protected FuzzyToken[] executaTable(int trId, FuzzyToken[] inps) {
    return myNet.getTableForTransition(trId).execute(inps);
  }

  @Override
  protected int calcMultiDelay(FuzzyToken token, Double multi) {
    return (int) Math.floor(defultFuzzificator.defuzzify(token) * multi);
  }

}
