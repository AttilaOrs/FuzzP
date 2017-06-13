package FuzzyGp.Tree.Nodes;

import java.util.EnumMap;

import core.FuzzyPetriLogic.FuzzyTableParser;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;

public enum OutType {
  W("w"), WZ("wz"), EIP("eip"), EIN("ein");

  OutType(String splispName) {
    this.typeNameInSlisp = splispName;
  }

  public final String typeNameInSlisp;
  public static final OutType defultOutType = W;

  public static OutType parseType(String toParse) {
    for (OutType type : OutType.values()) {
      if (type.typeNameInSlisp.equals(toParse) || type.name().equals(toParse)) {
        return type;
      }
    }
    return null;
  }

  public OneXTwoTable geTable() {
    return tableOfType.get(this);
  }

  private static final EnumMap<OutType, OneXTwoTable> tableOfType;
  static {
    tableOfType = new EnumMap<>(OutType.class);
    FuzzyTableParser parser = new FuzzyTableParser(true);
    tableOfType.put(OutType.W, OneXTwoTable.defaultTable());
    String writeZero = "{[<NL,ZR><NM,ZR><ZR,ZR><PM,ZR><PL,ZR><FF,FF>]}";
    tableOfType.put(OutType.WZ, parser.parseOneXTwoTable(writeZero));
    String eventIfPsoitive = "{[<NL,FF><NM,FF><ZR,FF><PM,ZR><PL,ZR><FF, FF>]}";
    tableOfType.put(OutType.EIP, parser.parseOneXTwoTable(eventIfPsoitive));
    String eventIfNegative = "{[<NL,ZR><NM,ZR><ZR,FF><PM,FF><PL,FF><FF, FF>]}";
    tableOfType.put(OutType.EIN, parser.parseOneXTwoTable(eventIfNegative));
  }
}
