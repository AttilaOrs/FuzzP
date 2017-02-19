package core.FuzzyPetriLogic.Tables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.FuzzyPetriLogic.FuzzyTableParser;
import core.FuzzyPetriLogic.FuzzyToken;

public class TwoXOneTableTest {

  @Test
  public void default_test() {
    FuzzyToken ff1 = new FuzzyToken(0.0, 0.4, 0.0, 0.3, 0.0);
    FuzzyToken ff2 = new FuzzyToken(0.1, 0.0, 0.0, 0.0, 0.2);

    TwoXOneTable table = TwoXOneTable.defaultTable();
    FuzzyToken[] res = table.execute(new FuzzyToken[] { ff1, ff2 });

    assertEquals(1, res.length);
    assertEquals("<0.19,0.14,0.00,0.38,0.29>", res[0].shortString());

    FuzzyToken phi = new FuzzyToken();
    res = table.execute(new FuzzyToken[] { ff1, phi });
    assertEquals(1, res.length);
    assertEquals("<phi>", res[0].shortString());

    res = table.execute(new FuzzyToken[] { phi, ff1 });
    assertEquals(1, res.length);
    assertEquals("<phi>", res[0].shortString());

  }

	String changerStr = "" +
	    "{[<FF><FF><FF><FF><FF><PL>]" +
	    " [<FF><FF><FF><FF><FF><PL>]" +
	    " [<FF><FF><FF><FF><FF><PL>]" +
	    " [<FF><FF><FF><FF><FF><PL>]" +
	    " [<FF><FF><FF><FF><FF><PL>]" +
	    " [<NL><NL><NL><NL><NL><ZR>]}";

  @Test
  public void changer_test() {
		FuzzyTableParser parser = new FuzzyTableParser(true);
		TwoXOneTable changer = parser.parseTwoXOneTable(changerStr);
    FuzzyToken phi = new FuzzyToken();
    FuzzyToken nonPhi = new FuzzyToken(1.0, 0.0, 0.0, 0.0, 0.0);
    FuzzyToken[] res = changer.execute(new FuzzyToken[] { phi, nonPhi });

    assertEquals(1, res.length);
    assertEquals("<1.00,0.00,0.00,0.00,0.00>", res[0].shortString());

    res = changer.execute(new FuzzyToken[] { nonPhi, phi });
    assertEquals("<0.00,0.00,0.00,0.00,1.00>", res[0].shortString());

    res = changer.execute(new FuzzyToken[] { phi, phi });
    assertEquals("<0.00,0.00,1.00,0.00,0.00>", res[0].shortString());

    res = changer.execute(new FuzzyToken[] { nonPhi, nonPhi });
    assertEquals("<phi>", res[0].shortString());

		assertFalse(changer.executable(new FuzzyToken[] { nonPhi, nonPhi }));
		assertTrue(changer.executable(new FuzzyToken[] { nonPhi, phi }));
		assertTrue(changer.executable(new FuzzyToken[] { phi, nonPhi }));
		assertTrue(changer.executable(new FuzzyToken[] { phi, phi }));
  }

	String c_test = "PL, PM, ZR, FF, FF, FF "
	    + ",PM, PM, ZR, FF, FF, FF,"
	    + "FF, FF, ZR, FF, FF, ZR,"
	    + "FF, FF, ZR, NM, NM, FF,"
	    + "FF, FF, ZR, NM, NL, FF,FF, FF, ZR, FF, FF, FF";

	String c_tets_str = "" +
	    "{[<PL><PM><ZR><FF><FF><FF>]" +
	    " [<PM><PM><ZR><FF><FF><ff>]" +
	    " [<FF><FF><ZR><FF><FF><ZR>]" +
	    " [<FF><FF><ZR><NM><NM><FF>]" +
	    " [<FF><FF><FF><NM><NL><FF>]" +
	    " [<FF><FF><ZR><FF><FF><FF>]}";
  @Test
  public void direction_test() {
		FuzzyTableParser parser = new FuzzyTableParser(true);
		TwoXOneTable c = parser.parseTwoXOneTable(c_tets_str);
    FuzzyToken NLZR = new FuzzyToken(0.5, 0.0, 0.5, 0.0, 0.0);
    FuzzyToken PLPM = new FuzzyToken(0.0, 0.0, 0.0, 0.5, 0.5);

    assertFalse(c.executable(new FuzzyToken[] { NLZR, PLPM }));
    assertTrue(c.executable(new FuzzyToken[] { PLPM, NLZR }));
		assertFalse(c.executable(new FuzzyToken[] { new FuzzyToken(), new FuzzyToken() }));
		assertTrue(c.executable(new FuzzyToken[] { FuzzyToken.zeroToken(), new FuzzyToken() }));
  }

}
