package FuzzyGp.Tree.Nodes;

public enum OperatorType {
  SEQ("*"), LOOP("#"), CONC("&"), SELCT("+");

  OperatorType(String repreztenation) {
    OpReprezantaion = repreztenation;
  }

  public final String OpReprezantaion;

}
