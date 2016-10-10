package FuzzyPLang.NetBuilder;

import core.FuzzyPetriLogic.ITable;

/**
 * Created by ors on 10/4/16.
 */
public interface FuzzyPLangNetBuilder {
    void addPlace(String str);
    void addInpPlace(String str);
    void addTransition(String trName);
    void addOutTransition(String trName);
    void addArc(String firsNodeName, String secondNodeName, double weigth);
    void addArc(String firsNodeName, String secondNodeName);

    void setDelayForTransition(String trName, int delay);
    void setNamedTableForTransition(String trName, String nameOfTable);

    void addTableWithName(String nameOfTable, ITable table);
    void addInitialTokenInPlace(String prevNode, String token);
}
