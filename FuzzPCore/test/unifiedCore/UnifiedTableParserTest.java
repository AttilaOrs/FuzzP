package unifiedCore;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;

public class UnifiedTableParserTest {

  @Test
  public void test() {
    String testStr = "@+@" +
        "{[< 0, 2><-1, 2><-2, 2><-2, 2><-2, 2>]" +
        " [< 1, 2>< 0, 2><-1, 2><-2, 2><-2, 2>]" +
        " [< 2, 2>< 1, 2>< 0, 2><-1, 2><-2, 2>]" +
        " [< 2, 2>< 2, 2>< 1, 2>< 0, 2><-1, 2>]" +
        " [< 2, 2>< 2, 2>< 2, 2>< 1, 2>< 0, 2>]}";
    UnifiedTableParser tableParser = new UnifiedTableParser();
    UnifiedTwoXTwoTable table = tableParser.parseTwoXTwoTable(testStr);
    String rr = tableParser.createString(table);
    assertEquals(testStr.replaceAll("\\s+", ""), rr.replaceAll("\\s+", ""));
  }

}
