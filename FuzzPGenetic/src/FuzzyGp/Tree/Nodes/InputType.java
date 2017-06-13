package FuzzyGp.Tree.Nodes;

import java.util.EnumMap;

import core.FuzzyPetriLogic.FuzzyTableParser;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;

public enum InputType {
  BM("bm"), BR("br"), BE("be"), NR("nr"), NM("nm"), CPE("cpe"), CNE("cne"), SU("su"), SD("sd"), SZ("sz");

  InputType(String typeNameInSlips) {
    this.typeNameInSlisp = typeNameInSlips;
  }

  public final String typeNameInSlisp;
  public static final InputType defultInputType = BM;

  public static InputType parseType(String toParse) {
    for (InputType type : InputType.values()) {
      if (type.typeNameInSlisp.equals(toParse) || type.name().equals(toParse)) {
        return type;
      }
    }
    return null;
  }

  public TwoXOneTable getTable() {
    return tableOfType.get(this);
  }

  public boolean blocking() {
    return this == InputType.BE || this == InputType.BM || this == InputType.BR;
  }

  public boolean nonblocking() {
    return this == InputType.NM || this == InputType.NR;
  }

  public boolean continousToEventConverers() {
    return this == InputType.CPE || this == InputType.CNE;
  }

  public boolean eventStateModifiers() {
    return this == InputType.SD || this == InputType.SU || this == InputType.SZ;
  }

  private static final EnumMap<InputType, TwoXOneTable> tableOfType;
  static {
    tableOfType = new EnumMap<>(InputType.class);

    tableOfType.put(InputType.BM, TwoXOneTable.defaultTable());
    FuzzyTableParser parser = new FuzzyTableParser(true);

    String blocking_reader = String.join("\n", //
        "{[<NL><NM><ZR><PM><PL><FF>]", //
        " [<NL><NM><ZR><PM><PL><FF>]", //
        " [<NL><NM><ZR><PM><PL><FF>]", //
        " [<NL><NM><ZR><PM><PL><FF>]", //
        " [<NL><NM><ZR><PM><PL><FF>]", //
        " [<FF><FF><FF><FF><FF><FF>]}");
    tableOfType.put(InputType.BR, parser.parseTwoXOneTable(blocking_reader));

    String blocking_enabler = String.join("\n", //
        "{[<NL><NL><NL><NL><NL><FF>]", //
        " [<NM><NM><NM><NM><NM><FF>]", //
        " [<ZR><ZR><ZR><ZR><ZR><FF>]", //
        " [<PM><PM><PM><PM><PM><FF>]", //
        " [<PL><PL><PL><PL><PL><FF>]", //
        " [<FF><FF><FF><FF><FF><FF>]}");
    tableOfType.put(InputType.BE, parser.parseTwoXOneTable(blocking_enabler));

    String nonblocking_merger = String.join("\n", //
        "{[<NL><NL><NM><NM><ZR><NL>]", //
        " [<NL><NM><NM><ZR><PM><NM>]", //
        " [<NM><NM><ZR><PM><PM><ZR>]", //
        " [<NM><ZR><PM><PM><PL><PL>]", //
        " [<ZR><PM><PM><PL><PL><PM>]", //
        " [<FF><FF><FF><FF><FF><FF>]}"); //
    tableOfType.put(InputType.NM, parser.parseTwoXOneTable(nonblocking_merger));
    String nonblocking_reader = String.join("\n", //
        "{[<NL><NM><ZR><PM><PL><ZR>]", //
        " [<NL><NM><ZR><PM><PL><ZR>]", //
        " [<NL><NM><ZR><PM><PL><ZR>]", //
        " [<NL><NM><ZR><PM><PL><ZR>]", //
        " [<NL><NM><ZR><PM><PL><ZR>]", //
        " [<FF><FF><FF><FF><FF><FF>]}");
    tableOfType.put(InputType.NR, parser.parseTwoXOneTable(nonblocking_reader));
    String converter_negativ_enabled = String.join("\n", //
        "{[<NL><NL><FF><FF><FF><FF>]", //
        " [<NM><NM><FF><FF><FF><FF>]", //
        " [<ZR><ZR><FF><FF><FF><FF>]", //
        " [<PM><PM><FF><FF><FF><FF>]", //
        " [<PL><PL><FF><FF><FF><FF>]", //
        " [<FF><FF><FF><FF><FF><FF>]}"); //
    tableOfType.put(InputType.CNE, parser.parseTwoXOneTable(converter_negativ_enabled));
    String converter_pozitiv_enabled = String.join("\n", //
        "{[<FF><FF><FF><NL><NL><FF>]", //
        " [<FF><FF><FF><NM><NM><FF>]", //
        " [<FF><FF><FF><ZR><ZR><FF>]", //
        " [<FF><FF><FF><PM><PM><FF>]", //
        " [<FF><FF><FF><PL><PL><FF>]", //
        " [<FF><FF><FF><FF><FF><FF>]}"); //
    tableOfType.put(InputType.CPE, parser.parseTwoXOneTable(converter_pozitiv_enabled));
    String sift_up = String.join("\n", //
        "{[<NM><NM><NM><NM><NM><NL>]", //
        " [<ZR><ZR><ZR><ZR><ZR><NM>]", //
        " [<PM><PM><PM><PM><PM><ZR>]", //
        " [<PL><PL><PL><PL><PL><PM>]", //
        " [<PL><PL><PL><PL><PL><PL>]", //
        " [<FF><FF><FF><FF><FF><FF>]}");
    tableOfType.put(InputType.SU, parser.parseTwoXOneTable(sift_up));
    String sift_down = String.join("\n", //
        "{[<NL><NL><NL><NL><NL><NL>]", //
        " [<NL><NL><NL><NL><NL><NM>]", //
        " [<NM><NM><NM><NM><NM><ZR>]", //
        " [<ZR><ZR><ZR><ZR><ZR><PM>]", //
        " [<PM><PM><PM><PM><PM><PL>]", //
        " [<FF><FF><FF><FF><FF><FF>]}");
    tableOfType.put(InputType.SD, parser.parseTwoXOneTable(sift_down));
    String zero_transformer = String.join("\n", //
        "{[<ZR><ZR><ZR><ZR><ZR><NL>]", //
        " [<ZR><ZR><ZR><ZR><ZR><NM>]", //
        " [<ZR><ZR><ZR><ZR><ZR><ZR>]", //
        " [<ZR><ZR><ZR><ZR><ZR><PM>]", //
        " [<ZR><ZR><ZR><ZR><ZR><PL>]", //
        " [<FF><FF><FF><FF><FF><FF>]}");
    tableOfType.put(InputType.SZ, parser.parseTwoXOneTable(zero_transformer));

  }

}
