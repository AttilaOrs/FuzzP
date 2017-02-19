package core.UnifiedPetriLogic.tables;

import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.function.BiFunction;

public enum Operator {
  PLUS, MINUS, MULT, DIV, None;
  
  public static final EnumMap<Operator, BiFunction<Double, Double, Double>> opMap = new EnumMap<>(Operator.class);
  private static final EnumMap<Operator, String> sign = new EnumMap<>(Operator.class);
  static {
    opMap.put(PLUS, (a, b) -> a + b);
    opMap.put(MINUS, (a, b) -> a - b);
    opMap.put(MULT, (a, b) -> a * b);
    opMap.put(DIV, (a, b) -> a / b);
    sign.put(PLUS, "+");
    sign.put(MINUS, "-");
    sign.put(MULT, "*");
    sign.put(DIV, "/");
    sign.put(None, "");
  }

  public String getSign() {
    return sign.get(this);
  }

  public static Operator parse(String what) {
    for (Entry<Operator, String> e : sign.entrySet()) {
      if (e.getValue().equals(what.trim())) {
        return e.getKey();
      }
    }
    return Operator.None;

  }

}
