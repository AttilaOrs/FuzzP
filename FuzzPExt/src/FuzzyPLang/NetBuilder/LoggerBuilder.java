package FuzzyPLang.NetBuilder;

import core.FuzzyPetriLogic.ITable;
import core.TableParser;

/**
 * Created by ors on 10/4/16.
 */
public class LoggerBuilder implements FuzzyPLangNetBuilder  {
    StringBuilder bld;
    TableParser parser ;

    public LoggerBuilder(){
        bld = new StringBuilder();
        parser = new TableParser();

    }
    public String getLog(){
        return  bld.toString();
    }
    public  void reset(){
        bld = new StringBuilder();
    }

    @Override
    public void addPlace(String str) {
        bld.append("Place added " + str + "\n");
    }

    @Override
    public void addInpPlace(String str) {
        bld.append("Input place added " + str+ "\n");
    }

    @Override
    public void addTransition(String trName) {
        bld.append("Transition added " + trName+ "\n");
    }

    @Override
    public void addOutTransition(String trName) {
        bld.append("Output transition added " + trName+ "\n");
    }

    @Override
    public void addArc(String firsNodeName, String secondNodeName, double weigth) {
        bld.append("Add arc from " + firsNodeName + " to " + secondNodeName + " " +weigth + "\n");
    }

    @Override
    public void addArc(String firsNodeName, String secondNodeName) {
        bld.append("Add arc from " + firsNodeName + " to " + secondNodeName + "\n");
    }

    @Override
    public void setDelayForTransition(String trName, int delay) {
        bld.append("set delay for transitin: " + delay + "\n");
    }

    @Override
    public void setNamedTableForTransition(String trName, String nameOfTable) {
        bld.append("set table for transitin: " + trName  + " " + nameOfTable+ "\n");
    }

    @Override
    public void addTableWithName(String nameOfTable, ITable table) {
        bld.append("add named table: " + nameOfTable  + " \n" + parser.createString(table)+ "\n");
    }

    @Override
    public void addInitialTokenInPlace(String prevNode, String token) {
        bld.append("set initial token :" + token  + " for " + prevNode+ "\n");
    }
}
