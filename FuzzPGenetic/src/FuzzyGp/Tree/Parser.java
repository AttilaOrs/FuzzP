package FuzzyGp.Tree;

import java.util.HashMap;
import java.util.Map.Entry;

import FuzzyGp.Tree.Nodes.DelayLeaf;
import FuzzyGp.Tree.Nodes.FullRuleLeaf;
import FuzzyGp.Tree.Nodes.ILeaf;
import FuzzyGp.Tree.Nodes.InputLeaf;
import FuzzyGp.Tree.Nodes.InputType;
import FuzzyGp.Tree.Nodes.InversionLeaf;
import FuzzyGp.Tree.Nodes.Operator;
import FuzzyGp.Tree.Nodes.OperatorType;
import FuzzyGp.Tree.Nodes.OutLeaf;
import FuzzyGp.Tree.Nodes.OutType;
import FuzzyGp.Tree.Nodes.ZeroEventInput;
import FuzzyGp.Tree.Visitors.ToSlispExpression;

public class Parser {
  public static final HashMap<NodeDecider, Class<? extends ILeaf>> leafDeaciders;
  public static final HashMap<Class<? extends ILeaf>, NodeParser> leafParsers;

  static {
    leafDeaciders = new HashMap<>();
    for (Entry<Class<? extends ILeaf>, String> ee : ToSlispExpression.leafStarters.entrySet()) {
      leafDeaciders.put(st -> st.startsWith(ee.getValue()), ee.getKey());

    }
    leafParsers = new HashMap<>();
    leafParsers.put(InputLeaf.class, Parser::parseInput);
    leafParsers.put(OutLeaf.class, Parser::parseOutput);
    leafParsers.put(DelayLeaf.class, st -> new DelayLeaf(extractInt(st, DelayLeaf.class)));

    leafParsers.put(ZeroEventInput.class, st -> new ZeroEventInput());
    leafParsers.put(InversionLeaf.class, st -> new InversionLeaf());
    leafParsers.put(FullRuleLeaf.class, Parser::parseFullRuleLeaf);
  }

  private static FullRuleLeaf parseFullRuleLeaf(String st) {
    String[] splitted = st.split(ToSlispExpression.leafSeparator);
    return new FullRuleLeaf(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]));
  }

  private static InputLeaf parseInput(String st) {
    if (!st.contains(ToSlispExpression.leafSeparator)) {
      String nr = st.replace(ToSlispExpression.leafStarters.get(InputLeaf.class), "").trim();
      return new InputLeaf(Integer.parseInt(nr));
    }
    String[] splitted = st.split(ToSlispExpression.leafSeparator);
    InputType type = InputType.parseType(splitted[1]);
    if (type == null) {
      throw new RuntimeException("invalid input type in s-lisp expression: " + splitted[1]);
    }
    return new InputLeaf(Integer.parseInt(splitted[2]), type);
  }

  private static OutLeaf parseOutput(String st) {
    if (!st.contains(ToSlispExpression.leafSeparator)) {
      String nr = st.replace(ToSlispExpression.leafStarters.get(OutLeaf.class), "").trim();
      return new OutLeaf(Integer.parseInt(nr));
    }
    String[] splitted = st.split(ToSlispExpression.leafSeparator);
    OutType type = OutType.parseType(splitted[1]);
    if (type == null) {
      throw new RuntimeException("invalid input type in s-lisp expression: " + splitted[1]);
    }
    return new OutLeaf(Integer.parseInt(splitted[2]), type);
  }

  public static final HashMap<NodeDecider, OperatorType> opDeciders;
  public static final HashMap<OperatorType, NodeParser> opParsers;

  static {
    opDeciders = new HashMap<>();
    opDeciders.put(st -> macthes(st, OperatorType.CONC.OpReprezantaion), OperatorType.CONC);
    opDeciders.put(st -> macthes(st, OperatorType.LOOP.OpReprezantaion), OperatorType.LOOP);
    opDeciders.put(st -> macthes(st, OperatorType.SELCT.OpReprezantaion), OperatorType.SELCT);
    opDeciders.put(st -> macthes(st, OperatorType.SEQ.OpReprezantaion), OperatorType.SEQ);
    opParsers = new HashMap<>();
    opParsers.put(OperatorType.SEQ, st -> paresOp(OperatorType.SEQ, st));
    opParsers.put(OperatorType.LOOP, st -> paresOp(OperatorType.LOOP, st));
    opParsers.put(OperatorType.SELCT, st -> paresOp(OperatorType.SELCT, st));
    opParsers.put(OperatorType.CONC, st -> paresOp(OperatorType.CONC, st));

  }

  public static Operator paresOp(OperatorType tt, String fullStr) {
    String temp = fullStr.replaceFirst("\\(", "").trim().substring(1);
    temp = temp.substring(0, temp.length() - 1);
    String[] sp = splitInTwo(temp);
    INode ff = parse(sp[0]);
    INode ss = parse(sp[1]);
    return new Operator(tt, ff, ss);
  }

  public static Integer extractInt(String st, Class<? extends ILeaf> leafClass) {
    Integer ll = Integer.parseInt(st.replace(ToSlispExpression.leafStarters.get(leafClass), "").trim());
    return ll;
  }

  public static boolean macthes(String big, String opst) {
    return big.replaceFirst("\\(", "").trim().startsWith(opst);
  }

  public interface NodeDecider {
    public boolean isMyString(String ll);
  }

  public interface NodeParser {
    public INode parse(String ll);
  }

  public static INode parse(String mm) {
    if (mm.trim().startsWith("(")) {
      for (Entry<NodeDecider, OperatorType> decider : opDeciders.entrySet()) {
        if (decider.getKey().isMyString(mm)) {
          return opParsers.get(decider.getValue()).parse(mm);
        }
      }
    }
    for (Entry<NodeDecider, Class<? extends ILeaf>> decider : leafDeaciders.entrySet()) {
      if (decider.getKey().isMyString(mm)) {
        return leafParsers.get(decider.getValue()).parse(mm);
      }
    }
    return null;

  }

  private static String[] splitInTwo(String temp) {
    return core.FuzzyPetriLogic.Util.splitToChildren(temp);
  }

}
