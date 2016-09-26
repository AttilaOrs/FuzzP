package core.FuzzyPetriLogic.Tables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.TableParser;
import core.FuzzyPetriLogic.FuzzyToken;

public class OneXOneTableTest {
	TableParser parserWithPhi;

  @Before
  public void setUp() throws Exception {
		parserWithPhi = new TableParser(true);
  }

  @Test
  public void default_test() {
    OneXOneTable def = OneXOneTable.defaultTable();
    FuzzyToken token = new FuzzyToken(0.1, 0.6, 0.0, 0.0, 0.3);
    FuzzyToken[] res = def.execute(new FuzzyToken[] { token });
    assertTrue(res.length == 1);
    assertEquals(res[0].shortString(), "<0.10,0.60,0.00,0.00,0.30>");
  }

  @Test
  public void inverse_test() {
		OneXOneTable def = parserWithPhi.parseOneXOneTable("{[<PL><PM><ZR><NM><NL><FF>]}");
    FuzzyToken token = new FuzzyToken(0.1, 0.6, 0.0, 0.0, 0.3);
    FuzzyToken[] res = def.execute(new FuzzyToken[] { token });
    assertTrue(res.length == 1);
    assertEquals("<0.30,0.00,0.00,0.60,0.10>", res[0].shortString());
  }

  @Test
  public void nothing_gives_nothing() {
		OneXOneTable def = parserWithPhi.parseOneXOneTable("{[<PL><PM><ZR><NM><NL><FF>]}");
    FuzzyToken token = new FuzzyToken();
    FuzzyToken[] res = def.execute(new FuzzyToken[] { token });
    assertTrue(res.length == 1);
    assertEquals("<phi>", res[0].shortString());
  }

  @Test
  public void no_rule_gives_nothing() {
		OneXOneTable def = parserWithPhi.parseOneXOneTable("{[<PL><FF><ZR><NM><FF><FF>]}");
    FuzzyToken token = new FuzzyToken(0.0, 0.6, 0.0, 0.0, 0.4);
    FuzzyToken[] res = def.execute(new FuzzyToken[] { token });
    assertTrue(res.length == 1);
    assertEquals("<phi>", res[0].shortString());
  }

  @Test
  public void inhibition() {
		OneXOneTable def = parserWithPhi.parseOneXOneTable("{[<FF><FF><FF><FF><FF><ZR>]}");
    FuzzyToken token = new FuzzyToken();
    FuzzyToken[] res = def.execute(new FuzzyToken[] { token });
    assertTrue(res.length == 1);
    assertEquals("<0.00,0.00,1.00,0.00,0.00>", res[0].shortString());
  }

  @Test
  public void executable_test() {
    OneXOneTable tt = OneXOneTable.defaultTable();
    assertFalse(tt.executable(new FuzzyToken[] { new FuzzyToken() }));
    assertTrue(tt.executable(new FuzzyToken[] { new FuzzyToken(1.0, 0.0, 0.0, 0.0, 0.0) }));

		OneXOneTable otherTable = parserWithPhi.parseOneXOneTable("{[<FF><ZR><FF><ZR><FF><ZR>]}");

    assertTrue(otherTable.executable(new FuzzyToken[] { new FuzzyToken() }));
    assertFalse(otherTable.executable(new FuzzyToken[] { new FuzzyToken(0.5, 0.0, 0.5, 0.0, 0.0) }));
    assertTrue(otherTable.executable(new FuzzyToken[] { new FuzzyToken(0.3, 0.3, 0.0, 0.0, 0.0) }));

  }

}
