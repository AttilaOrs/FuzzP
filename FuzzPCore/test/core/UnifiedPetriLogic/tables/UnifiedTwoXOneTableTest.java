package core.UnifiedPetriLogic.tables;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.UnifiedPetriLogic.UnifiedTableParser;

public class UnifiedTwoXOneTableTest {

  private UnifiedTableParser parser;

  @Before
  public void setUp() {
    parser = new UnifiedTableParser(true);
  }

  String maxFinder = "{" +
      "[<-2><-1>< 0>< 1>< 2><FF>]" + //
      "[<-1><-1>< 0>< 1>< 2><FF>]" + //
      "[< 0>< 0>< 0>< 1>< 2><FF>]" + //
      "[< 1>< 1>< 1>< 1>< 2><FF>]" + //
      "[< 2>< 2>< 2>< 2>< 2><FF>]" + //
      "[<FF><FF><FF><FF><FF><FF>]" + //
      "}";

  @Test
  public void maybe_test() {
    UnifiedTwoXOneTable table = parser.parseTwoXOneTable(maxFinder);
    assertTrue(table.maybeExecutable(new boolean[] { true, true }));
    assertFalse(table.maybeExecutable(new boolean[] { true, false }));
    assertFalse(table.maybeExecutable(new boolean[] { false, true }));
    assertFalse(table.maybeExecutable(new boolean[] { false, false }));

  }

}
