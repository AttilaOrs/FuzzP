package core.FuzzyPetriLogic.Tables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.FuzzyPetriLogic.FuzzyTableParser;
import core.FuzzyPetriLogic.FuzzyToken;

public class OneXTwoTableTest {
	FuzzyTableParser parser;

  @Before
  public void setUp() throws Exception {
		parser = new FuzzyTableParser(true);
  }

  @Test
  public void default_distributes() {
    FuzzyToken zz = FuzzyToken.zeroToken();
    OneXTwoTable table = OneXTwoTable.defaultTable();

    FuzzyToken[] res = table.execute(new FuzzyToken[] { zz });
    assertTrue(res.length == 2);
    assertEquals("<0.00,0.00,1.00,0.00,0.00>", res[0].shortString());
    assertEquals("<0.00,0.00,1.00,0.00,0.00>", res[1].shortString());

    FuzzyToken ll = new FuzzyToken(0.1, 0.2, 0.3, 0.35, 0.05);
    res = table.execute(new FuzzyToken[] { ll });

    assertTrue(res.length == 2);
    assertEquals("<0.10,0.20,0.30,0.35,0.05>", res[0].shortString());
    assertEquals("<0.10,0.20,0.30,0.35,0.05>", res[1].shortString());

    FuzzyToken nullToken = new FuzzyToken();

    res = table.execute(new FuzzyToken[] { nullToken });
    assertTrue(res.length == 2);
    assertEquals("<phi>", res[0].shortString());
    assertEquals("<phi>", res[1].shortString());
  }

  @Test
  public void inverse_test() {
		OneXTwoTable table = parser.parseOneXTwoTable("{[<NL,PL><NM,PL><ZR,ZR><PM,NL><PL,NL><FF,FF>]}");
    
    FuzzyToken ll = new FuzzyToken(0.1, 0.2, 0.3, 0.35, 0.05);
    FuzzyToken[] res = table.execute(new FuzzyToken[] { ll });
    assertTrue(res.length == 2);

    assertEquals("<0.10,0.20,0.30,0.35,0.05>", res[0].shortString());
    assertEquals("<0.40,0.00,0.30,0.00,0.30>", res[1].shortString());

  }

  @Test
  public void selector_test() {
		OneXTwoTable table = parser.parseOneXTwoTable("{[<NL,FF><NM,FF><ZR,FF><PM,FF><PL,FF><FF,ZR>]}");
    FuzzyToken ll = new FuzzyToken(0.1, 0.2, 0.3, 0.35, 0.05);
    FuzzyToken[] res = table.execute(new FuzzyToken[] { ll });

    assertTrue(res.length == 2);
    assertEquals("<0.10,0.20,0.30,0.35,0.05>", res[0].shortString());
    assertEquals("<phi>", res[1].shortString());

    FuzzyToken phi = new FuzzyToken();
    res = table.execute(new FuzzyToken[] { phi });

    assertTrue(res.length == 2);
    assertEquals("<phi>", res[0].shortString());
    assertEquals("<0.00,0.00,1.00,0.00,0.00>", res[1].shortString());
  }

  @Test
  public void never_executable() {
    OneXTwoTable tableOnlyPhi = parser.parseOneXTwoTable("{[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]}");
    FuzzyToken ll = new FuzzyToken(0.1, 0.2, 0.3, 0.35, 0.05);
    assertFalse(tableOnlyPhi.executable(new FuzzyToken[] { ll }));
    FuzzyToken phi = new FuzzyToken();
    assertFalse(tableOnlyPhi.executable(new FuzzyToken[] { phi }));

  }

  @Test
  public void maybe_executable() {
    OneXTwoTable table = parser.parseOneXTwoTable("{[<FF,FF><FF,FF><FF,FF><FF,FF><PL,FF><FF,FF>]}");
    assertTrue(table.maybeExecutable(new boolean[] { true }));
    assertFalse(table.maybeExecutable(new boolean[] { false }));

    OneXTwoTable tableOtherChannel = parser.parseOneXTwoTable("{[<FF,FF><FF,FF><FF,FF><FF,FF><FF,PL><FF,FF>]}");
    assertTrue(tableOtherChannel.maybeExecutable(new boolean[] { true }));
    assertFalse(tableOtherChannel.maybeExecutable(new boolean[] { false }));

    OneXTwoTable tableOnlyPhi = parser.parseOneXTwoTable("{[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]}");
    assertFalse(tableOnlyPhi.maybeExecutable(new boolean[] { true }));
    assertFalse(tableOnlyPhi.maybeExecutable(new boolean[] { false }));

    OneXTwoTable tablePhiColomHasValues = parser.parseOneXTwoTable("{[<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF><ZR,FF>]}");
    assertFalse(tablePhiColomHasValues.maybeExecutable(new boolean[] { true }));
    assertTrue(tablePhiColomHasValues.maybeExecutable(new boolean[] { false }));

  }
}
