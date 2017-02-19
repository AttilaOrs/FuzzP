package core.FuzzyPetriLogic.Tables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.FuzzyPetriLogic.FuzzyTableParser;
import core.FuzzyPetriLogic.FuzzyToken;

public class TwoXTwoTableTest {


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
		FuzzyTableParser parser = new FuzzyTableParser(true);
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

}
