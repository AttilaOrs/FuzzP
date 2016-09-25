package core.FuzzyPetriLogic.PetriNet;

import java.util.stream.Collectors;

import core.FuzzyPetriLogic.ExecutableFuzzyPetriNet;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

public class FuzzyPetriNetChecker {
  String potentialErrors;

  public FuzzyPetriNetChecker() {
    potentialErrors = "";
  }

  public boolean checkPetriNet(ExecutableFuzzyPetriNet net) {
    potentialErrors = "";
    boolean tablesCorrect = checkTables(net);
    return tablesCorrect;
  }

  public String getErrorMsg() {
    return potentialErrors;
  }

  private boolean checkTables(ExecutableFuzzyPetriNet net) {

    boolean correct = true;
    for (int trIndex = 0; trIndex < net.getNrOfTransition(); trIndex++) {
      boolean currentTrCorrect = true;

      int placesNeeded = net.getPlacesNeededForTransition(trIndex).size();
      if (placesNeeded == 0) {
        String errorMsg = "Transition " + trIndex + " has no input places. It is not connected to the Petri net.\n";
        potentialErrors += errorMsg;
        currentTrCorrect = false;

      } else if (placesNeeded > 2) {
        String errorMsg = "Transition " + trIndex + " has more than two input places. These are the following: [";
        errorMsg += net.getPlacesNeededForTransition(trIndex).stream().map(plId -> "P" + plId)
            .collect(Collectors.joining(","));
        errorMsg += "]\n";
        potentialErrors += errorMsg;
        currentTrCorrect = false;
      }

      int outPlacesTransition = net.getOutputPlacesForTransition(trIndex).size()
          + ((net.isOuputTransition(trIndex)) ? 1 : 0);
      if (outPlacesTransition == 0) {
        String errorMsg = "Transition " + trIndex + " has no ouput places. It is not connected to the Petri net.\n";
        potentialErrors += errorMsg;
        currentTrCorrect = false;
      } else if ((outPlacesTransition > 2)) {
        String errorMsg = "Transition " + trIndex + " has more than two output places. These are the following: [";
        errorMsg += net.getOutputPlacesForTransition(trIndex).stream().map(plId -> "P" + plId)
            .collect(Collectors.joining(","));
        errorMsg += "]";
        if (net.isOuputTransition(trIndex)) {
          errorMsg += " and it is ouput transition (which means an additional arc)";
        }
        errorMsg += "\n";
        potentialErrors += errorMsg;
        currentTrCorrect = false;
      }


      if (currentTrCorrect) { // Table type check has meaning only ifeverything
                              // else is correct;
        currentTrCorrect &= checkTableType(placesNeeded, outPlacesTransition, trIndex,
            net.getTableForTransition(trIndex));
      }
      correct &= currentTrCorrect;
    }
    return correct;

  }

  private boolean checkTableType(int allInps, int allOuts, int trIndex, ITable table) {
    String errorMsg = "Transitoin " + trIndex + " has incorporated table : " + table.getClass().getSimpleName()
        + " but it has to be ";
    boolean correct = true;

    if (allInps == 1 && allOuts == 1 && !(table instanceof OneXOneTable)) {
      errorMsg += "OneXOneTable";
      correct = false;
    }
    else if (allInps == 2 && allOuts == 1 && !(table instanceof TwoXOneTable)) {
      errorMsg += "TwoXOneTable";
      correct = false;
    }

    else if (allInps == 1 && allOuts == 2 && !(table instanceof OneXTwoTable)) {
      errorMsg += "OneXTwoTable";
      correct = false;
    }
    else if (allInps == 2 && allOuts == 2 && !(table instanceof TwoXTwoTable)) {
      errorMsg += "TwoXTwoTable";
      correct = false;
    } else {
      errorMsg += " [shoudn't heapean " + allInps + " " + allOuts + "]";
    }
    if (!correct) {
      potentialErrors += errorMsg + "\n";
    }

    return correct;
  }

}
