package core.FuzzyPetriLogic.Tables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.FuzzyPetriLogic.FuzzyToken;

public class OneXTwoTableTest {

  @Before
  public void setUp() throws Exception {
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
    OneXTwoTable table = OneXTwoTable.buildFromString("[NL;NM;ZR;PM;PL;FF;PL; PL; ZR;NL;NL;FF ]");
    FuzzyToken ll = new FuzzyToken(0.1, 0.2, 0.3, 0.35, 0.05);
    FuzzyToken[] res = table.execute(new FuzzyToken[] { ll });
    assertTrue(res.length == 2);

    assertEquals("<0.10,0.20,0.30,0.35,0.05>", res[0].shortString());
    assertEquals("<0.40,0.00,0.30,0.00,0.30>", res[1].shortString());

  }

  @Test
  public void selector_test() {
    OneXTwoTable table = OneXTwoTable.buildFromString("[NL;NM;ZR;PM;PL;FF; FF; FF; FF;FF;FF;ZR ]");
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

}
