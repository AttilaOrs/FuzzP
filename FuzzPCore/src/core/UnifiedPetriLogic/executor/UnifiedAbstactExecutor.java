package core.UnifiedPetriLogic.executor;

import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedPetriNetChecker;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.common.AbstractExecutor;

public abstract class UnifiedAbstactExecutor
    extends AbstractExecutor<UnifiedToken, IUnifiedTable, UnifiedOneXOneTable, UnifiedPetriNet> {



  public UnifiedAbstactExecutor(UnifiedPetriNet net, boolean enablechecking) {
    super(net, enablechecking);
  }

  public UnifiedAbstactExecutor(UnifiedPetriNet net) {
    super(net, true);
  }


  @Override
  protected void checkPetriNet() {
    UnifiedPetriNetChecker checker = new UnifiedPetriNetChecker();
    if (!checker.checkPetriNet(myNet)) {
      throw new RuntimeException(checker.getErrorMsg());
    }

  }

  @Override
  protected UnifiedToken[] emptyTokenArray() {
    return new UnifiedToken[0];
  }

  @Override
  protected UnifiedToken[] tokenArray(int size) {
    return new UnifiedToken[size];
  }

  @Override
  protected UnifiedToken phi() {
    return new UnifiedToken();
  }

  @Override
  protected UnifiedToken transformOnArc(UnifiedToken toke, int trId, int plId) {
    return toke;
  }

  @Override
  protected boolean executable(UnifiedToken[] inps, int trId) {
    return myNet.getTableForTransition(trId).executable(inps,
        myNet.getContextForTransition(trId));
  }

  @Override
  protected UnifiedToken[] executaTable(int trId, UnifiedToken[] inps) {
    return myNet.getTableForTransition(trId).execute(inps, myNet.getContextForTransition(trId));
  }

  @Override
  protected int calcMultiDelay(UnifiedToken token, Double multi) {
    return (int) Math.floor(multi * token.getValue());
  }

}
