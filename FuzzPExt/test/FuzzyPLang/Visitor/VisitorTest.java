package FuzzyPLang.Visitor;

import FuzzyPLang.NetBuilder.FuzzyPLangNetBuilder;
import FuzzyPLang.NetBuilder.LoggerBuilder;
import FuzzyPLang.gen.FuzzyPLangLexer;
import FuzzyPLang.gen.FuzzyPLangParser;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;
import core.TableParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by ors.kilyen on 10/5/2016.
 */
public class VisitorTest {
    FuzzyPLangNetBuilder netBuilder;

    Visitor visitor;
    TableParser parser = new TableParser();
    TableParser parserWithPhi = new TableParser(true);
    private ArgumentCaptor<ITable> captor;

    @Before
    public void setUp(){
        netBuilder = mock(FuzzyPLangNetBuilder.class);
        visitor = new Visitor(netBuilder);
        captor = ArgumentCaptor.forClass(ITable.class);
    }

    @Test
    public void OneXOneTableParsingTest(){
        String  tableStr = "{[<NL><zr><ff><NM><PL>]}";
        String tester = "bla = " + tableStr + ";";
        ParseTree tree = makeParseTree(tester);

        visitor.visit(tree);
        captor = ArgumentCaptor.forClass(ITable.class);

        verify(netBuilder).addTableWithName(eq("bla"), captor.capture());
        assertTrue(((OneXOneTable)captor.getValue()).getTable().equals(parser.parseOneXOneTable(tableStr).getTable()));
    }

    @Test
    public void OneXTwoTableParsingTest(){
        String  tableStr = "{[<NL,ZR><zr,ff><ff,ff><PL,NM><PL,pm><pm, FF>]}";
        String tester = "tucc =" + tableStr + ";";
        ParseTree tree = makeParseTree(tester);

        visitor.visit(tree);

        verify(netBuilder).addTableWithName(eq("tucc"), captor.capture());
        assertTrue(((OneXTwoTable) captor.getValue()).getTables().equals(parserWithPhi.parseOneXTwoTable(tableStr).getTables()));
    }


    String twoXTwoDefaultStringWithoutPhi = "" +
            "{[<NL,NL><NL,NL><NM,NM><NM,NM><ZR,ZR>]" +
            " [<NL,NL><NM,NM><NM,NM><ZR,ZR><PM,PM>]" +
            " [<NM,NM><NM,NM><ZR,ZR><PM,PM><PM,PM>]" +
            " [<NM,NM><ZR,ZR><PM,PM><PM,PM><PL,PL>]" +
            " [<ZR,ZR><PM,PM><PM,PM><PL,PL><PL,PL>]}";

    @Test
    public void TwoXTwoTableParsingTest(){
        String tester = "barmi =" + twoXTwoDefaultStringWithoutPhi + ";";
        ParseTree tree = makeParseTree(tester);

        visitor.visit(tree);

        verify(netBuilder).addTableWithName(eq("barmi"), captor.capture());
        assertTrue(((TwoXTwoTable) captor.getValue()).getTables().equals(TwoXTwoTable.defaultTable().getTables()));
    }

    String twoXOneDefaultStringWithtPhi = "" +
            "{[<NL><NL><NM><NM><ZR><FF>]" +
            " [<NL><NM><NM><ZR><PM><FF>]" +
            " [<NM><NM><ZR><PM><PM><FF>]" +
            " [<NM><ZR><PM><PM><PL><FF>]" +
            " [<ZR><PM><PM><PL><PL><FF>]" +
            " [<FF><FF><FF><FF><FF><FF>]}";

    @Test
    public void TwoXOneTableParsing(){
        String tester = "last =" + twoXOneDefaultStringWithtPhi + ";";
        ParseTree tree = makeParseTree(tester);

        visitor.visit(tree);

        verify(netBuilder).addTableWithName(eq("last"), captor.capture());
        assertTrue(((TwoXOneTable) captor.getValue()).getTable().equals(TwoXOneTable.defaultTable().getTable()));
    }

    @Test
    public void lineTest(){
        String tester = "iP0-(1.2)->t0[2]->P1->oT1;";
        ParseTree parseTree = makeParseTree(tester);

        visitor.visit(parseTree);

        verify(netBuilder).addInpPlace("iP0");
        verify(netBuilder).addTransition("T0");
        verify(netBuilder).setDelayForTransition("T0",2);
        verify(netBuilder).addPlace("P1");
        verify(netBuilder).addOutTransition("oT1");

        verify(netBuilder).addArc("iP0", "T0" ,1.2);
        verify(netBuilder).addArc("T0", "P1" );
        verify(netBuilder).addArc("P1" ,"oT1");





    }

    private static ParseTree makeParseTree(String toParse){
        InputStream is = new ByteArrayInputStream(toParse.getBytes(StandardCharsets.UTF_8));
        ANTLRInputStream stream = null;
        try {
            stream = new ANTLRInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FuzzyPLangLexer ll = new FuzzyPLangLexer(stream);
        CommonTokenStream cms = new CommonTokenStream(ll);
        FuzzyPLangParser parser = new FuzzyPLangParser(cms);
        return parser.prog();
    }

}