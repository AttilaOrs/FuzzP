package core.FuzzyPetriLogic.Tables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.FuzzyPetriLogic.FuzzyTableParser;
import core.FuzzyPetriLogic.FuzzyToken;

public class TwoXTwoTableTest {

  private FuzzyTableParser parser;
  @Before
  public void setUp() {

    parser = new FuzzyTableParser(true);
  }


  @Test
  public void default_test() {
    TwoXTwoTable def = TwoXTwoTable.defaultTable();
    FuzzyToken firstToken = new FuzzyToken(0.0, 0.5, 0.0, 0.0, 0.5);
    FuzzyToken secondToken = new FuzzyToken(0.5, 0.0, 0.0, 0.5, 0.0);
    FuzzyToken[] input = new FuzzyToken[] { firstToken, secondToken };
    assertTrue(def.executable(input));
    FuzzyToken[] output = def.execute(input);
    assertEquals("<0.25,0.00,0.50,0.00,0.25>", output[0].shortString());
    assertEquals("<0.25,0.00,0.50,0.00,0.25>", output[1].shortString());

    input = new FuzzyToken[] { firstToken, new FuzzyToken() };
    assertFalse(def.executable(input));
    input = new FuzzyToken[] { new FuzzyToken(), firstToken };
    assertFalse(def.executable(input));
    input = new FuzzyToken[] { new FuzzyToken(), new FuzzyToken() };
    assertFalse(def.executable(input));
  }

  String fuzzTable = ""//
	    + "{[<NL,FF>;<NL,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]" //
	    + "[<NL,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]" //
	    + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]"//
	    + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,PL>;<FF,FF>]"//
	    + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,PL>;<FF,PL>;<FF,FF>]"//
	    + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]}";

  @Test
  public void intersting_table_test() {
		TwoXTwoTable intersting = parser.parseTwoXTwoTable(fuzzTable);
    FuzzyToken firstToken = new FuzzyToken(0.0, 0.5, 0.0, 0.0, 0.5);
    FuzzyToken secondToken = new FuzzyToken(0.5, 0.0, 0.0, 0.5, 0.0);
    FuzzyToken[] input = new FuzzyToken[] { firstToken, secondToken };

    assertTrue(intersting.executable(input));
    FuzzyToken[] output = intersting.execute(input);
    assertEquals("<1.00,0.00,0.00,0.00,0.00>", output[0].shortString());
    assertEquals("<0.00,0.00,0.00,0.00,1.00>", output[1].shortString());

    input = new FuzzyToken[] { firstToken, FuzzyToken.zeroToken() };
    assertFalse(intersting.executable(input));
    input = new FuzzyToken[] { FuzzyToken.zeroToken(), secondToken };
    assertFalse(intersting.executable(input));

  }

  String all_phi = ""//
      + "{[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]" //
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]" //
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]}";

  String all_phi_except_phi_colom = ""//
      + "{[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]" //
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]" //
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,ZR>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]}";

  String all_phi_exept_phi_row = ""//
      + "{[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]" //
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]" //
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,ZR>;<FF,FF>;<FF,FF>]}";

  String all_phi_exept_midle = ""//
      + "{[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]" //
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]" //
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,ZR>;<FF,FF>;<FF,FF>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]"//
      + "[<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>;<FF,FF>]}";

  public void maybe_executavle_test() {
    TwoXTwoTable c = parser.parseTwoXTwoTable(all_phi);
    assertFalse(c.maybeExecutable(new boolean[] { true, true }));
    assertFalse(c.maybeExecutable(new boolean[] { false, true }));
    assertFalse(c.maybeExecutable(new boolean[] { true, false }));
    assertFalse(c.maybeExecutable(new boolean[] { false, false }));

    c = parser.parseTwoXTwoTable(all_phi_except_phi_colom);
    assertFalse(c.maybeExecutable(new boolean[] { true, true }));
    assertTrue(c.maybeExecutable(new boolean[] { false, true }));
    assertFalse(c.maybeExecutable(new boolean[] { true, false }));
    assertFalse(c.maybeExecutable(new boolean[] { false, false }));

    c = parser.parseTwoXTwoTable(all_phi_exept_midle);
    assertTrue(c.maybeExecutable(new boolean[] { true, true }));
    assertFalse(c.maybeExecutable(new boolean[] { false, true }));
    assertFalse(c.maybeExecutable(new boolean[] { true, false }));
    assertFalse(c.maybeExecutable(new boolean[] { false, false }));


  }


}
