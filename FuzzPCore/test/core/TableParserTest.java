package core;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

public class TableParserTest {

	String twoXOneDefaultStringWithoutPhiGenerated = "" +
	    "{[<NL><NL><NM><NM><ZR>]\n" +
	    "[<NL><NM><NM><ZR><PM>]\n" +
	    "[<NM><NM><ZR><PM><PM>]\n" +
	    "[<NM><ZR><PM><PM><PL>]\n" +
	    "[<ZR><PM><PM><PL><PL>]\n}";
	@Test
	public void toStringOneXOne() {
		TableParser underTest = new TableParser(false);
		String rez = underTest.createString(OneXOneTable.defaultTable());
		assertTrue(rez.equals("{[<NL><NM><ZR><PM><PL>]}"));

		rez = underTest.createString(OneXTwoTable.defaultTable());
		assertTrue(rez.equals("{[<NL,NL><NM,NM><ZR,ZR><PM,PM><PL,PL>]}"));

		rez = underTest.createString(TwoXOneTable.defaultTable());
		assertTrue(rez.equals(twoXOneDefaultStringWithoutPhiGenerated));
	}


	@Test
	public void correctType() {
		TableParser underTest = new TableParser(false);
		String oneXoneStr = "{[<NM>, <zr>, < zr  >, <ff>, <PL>]}";
		ITable oneXone = underTest.parseTable(oneXoneStr);
		assertTrue(oneXone instanceof OneXOneTable);

		String oneXtwoStr = "{[<NL,NL>,-anything- <NM,NM>; <ZR,ZR> <PM, PM>, <PL, ff>]}";
		ITable oneXtwo = underTest.parseTable(oneXtwoStr);
		assertTrue(oneXtwo instanceof OneXTwoTable);

		ITable twoXone = underTest.parseTable(twoXOneDefaultStringWithoutPhi);
		assertTrue(twoXone instanceof TwoXOneTable);

		ITable twoXtwo = underTest.parseTable(twoXTwoDefaultStringWithoutPhi);
		assertTrue(twoXtwo instanceof TwoXTwoTable);
	}

	@Test
	public void simpleTableParsing() {
		TableParser underTest = new TableParser(false);
		String testStr = "{[<NL>, <NM>, <ZR>, <PM>, <PL>]}";
		OneXOneTable rez = underTest.parseOneXOneTable(testStr);

		assertTrue(rez.getTable().equals(OneXOneTable.defaultTable().getTable()));
	}

	@Test
	public void simpleTableParsingWithPhi() {
		TableParser underTest = new TableParser(true);
		String testStr = "{[<nl>, <NM>, <zr>, <PM>, <PL>, <FF>]}";
		OneXOneTable rez = underTest.parseOneXOneTable(testStr);
		assertTrue(rez.getTable().equals(OneXOneTable.defaultTable().getTable()));
	}

	@Test
	public void OneXTwoTableWithoutPhi() {
		TableParser underTest = new TableParser(false);
		String testStr = "{[<NL,NL>, <NM,NM>, <ZR,ZR>, <PM, PM>, <PL, PL>]}";
		OneXTwoTable rez = underTest.parseOneXTwoTable(testStr);
		assertTrue(rez.getTables().equals(OneXTwoTable.defaultTable().getTables()));
	}

	@Test
	public void OneXTwoTableWithPhi() {
		TableParser underTest = new TableParser(true);
		String testStr = "{[<NL,NL>, <NM,nm>, <ZR,ZR>, <PM, PM>, <PL, PL>, <FF, FF>]}";
		OneXTwoTable rez = underTest.parseOneXTwoTable(testStr);
		assertTrue(rez.getTables().equals(OneXTwoTable.defaultTable().getTables()));
	}

	String twoXOneDefaultStringWithoutPhi = "" +
	    "{[<NL><NL><NM><NM><ZR>]" +
	    " [<NL><NM><NM><ZR><PM>]" +
	    " [<NM><NM><ZR><PM><PM>]" +
	    " [<NM><ZR><PM><PM><PL>]" +
	    " [<ZR><PM><PM><PL><PL>]}";


	@Test
	public void TwoXOneTableWithoutPhiTets() {
		TableParser underTest = new TableParser(false);
		TwoXOneTable rez = underTest.parseTwoXOneTable(twoXOneDefaultStringWithoutPhi);
		assertTrue(rez.getTable().equals(TwoXOneTable.defaultTable().getTable()));
	}

	String twoXOneDefaultStringWithtPhi = "" +
	    "{[<NL><NL><NM><NM><ZR><FF>]" +
	    " [<NL><NM><NM><ZR><PM><FF>]" +
	    " [<NM><NM><ZR><PM><PM><FF>]" +
	    " [<NM><ZR><PM><PM><PL><FF>]" +
	    " [<ZR><PM><PM><PL><PL><FF>]" +
	    " [<FF><FF><FF><FF><FF><FF>]}";

	@Test
	public void TwoXOneTableWithPhiTets() {
		TableParser underTest = new TableParser(true);
		TwoXOneTable rez = underTest.parseTwoXOneTable(twoXOneDefaultStringWithtPhi);
		assertTrue(rez.getTable().equals(TwoXOneTable.defaultTable().getTable()));
	}

	String twoXTwoDefaultStringWithoutPhi = "" +
	    "{[<NL,NL><NL,NL><NM,NM><NM,NM><ZR,ZR>]" +
	    " [<NL,NL><NM,NM><NM,NM><ZR,ZR><PM,PM>]" +
	    " [<NM,NM><NM,NM><ZR,ZR><PM,PM><PM,PM>]" +
	    " [<NM,NM><ZR,ZR><PM,PM><PM,PM><PL,PL>]" +
	    " [<ZR,ZR><PM,PM><PM,PM><PL,PL><PL,PL>]}";

	@Test
	public void TwoXTwoTableWithoutPhiTets() {
		TableParser underTest = new TableParser(false);
		TwoXTwoTable rez = underTest.parseTwoXTwoTable(twoXTwoDefaultStringWithoutPhi);
		assertTrue(rez.getTables().equals(TwoXTwoTable.defaultTable().getTables()));
	}


	String twoXTwoDefaultStringWithoutPhiIncorrect = "" +
	    "{[<NL><NL><NM><NM><ZR>]" +
	    " [<NL><NM><NM><ZR><PM>]" +
	    " [<NM><NM><ZR><PM><PM>]" +
	    " [<NM><ZR><PM><kacsa><PL>]" +
	    " [<ZR><PM><PM><PL><PL>]}";

	@Test(expected = TableParser.TablePareserExcetion.class)
	public void TwoXOneTableWithoutError() {
		TableParser underTest = new TableParser(false);
		underTest.parseTwoXOneTable(twoXTwoDefaultStringWithoutPhiIncorrect);
	}
	@Test(expected = TableParser.TablePareserExcetion.class)
	public void notCorrectTableStarter() {
		TableParser underTest = new TableParser();
		String testStr = "[<nl>, <NM>, <zr>, <PM>, <PL>]}";
		underTest.parseOneXOneTable(testStr);
	}

	@Test(expected = TableParser.TablePareserExcetion.class)
	public void notCorrectTableEnder() {
		TableParser underTest = new TableParser(false);
		String testStr = "{[<nl>, <NM>, <zr>, <PM>, <PL>]";
		underTest.parseOneXOneTable(testStr);
	}

	@Test(expected = TableParser.TablePareserExcetion.class)
	public void notLineStarter() {
		TableParser underTest = new TableParser(false);
		String testStr = "{{<nl>, <NM>, <zr>, <PM>, <PL>]}";
		underTest.parseOneXOneTable(testStr);
	}

	@Test(expected = TableParser.TablePareserExcetion.class)
	public void notLineEnder() {
		TableParser underTest = new TableParser(false);
		String testStr = "{[<nl>, <NM>, <zr>, <PM>, <PL>}";
		underTest.parseOneXOneTable(testStr);
	}

	@Test(expected = TableParser.TablePareserExcetion.class)
	public void notCellStarter() {
		TableParser underTest = new TableParser(false);
		String testStr = "{[<nl>, <NM>, zr>, <PM>, <PL>]}";
		underTest.parseOneXOneTable(testStr);
	}

	@Test(expected = TableParser.TablePareserExcetion.class)
	public void notCellEnder() {
		TableParser underTest = new TableParser(false);
		String testStr = "{[<nl>, <NM>, <zr, <PM>, <PL>]}";
		underTest.parseOneXOneTable(testStr);
	}

	@Test(expected = TableParser.TablePareserExcetion.class)
	public void notFuzzyValue() {
		TableParser underTest = new TableParser(false);
		String testStr = "{[<duck>, <NM>, <zr, <PM>, <PL>]}";
		underTest.parseOneXOneTable(testStr);
	}


	@Test(expected = TableParser.TablePareserExcetion.class)
	public void OneXTwo_incompleteCells() {
		TableParser underTest = new TableParser(false);
		String testStr = "{[<NL>, <NM,NM>, <ZR,ZR>, <PM, PM>, <PL, PL>]}";
		underTest.parseOneXTwoTable(testStr);
	}

	@Test(expected = TableParser.TablePareserExcetion.class)
	public void OneXTwo_notAFuzzyValue() {
		TableParser underTest = new TableParser(false);
		String testStr = "{[<NL,ImAmNotABug>, <NM,NM>, <ZR,ZR>, <PM, PM>, <PL, PL>]}";
		underTest.parseOneXTwoTable(testStr);
	}

}
