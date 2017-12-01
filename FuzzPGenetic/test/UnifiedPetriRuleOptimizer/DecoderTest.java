package UnifiedPetriRuleOptimizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.BitSet;

import org.junit.Test;

import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;

public class DecoderTest {

  UnifiedTableParser parser = new UnifiedTableParser();

  private static final String oneSingle = "{[<FF><2><FF><FF><FF><FF>]}";

  @Test
  public void very_simple() {
    UnifiedPetriNet net = new UnifiedPetriNet();
    UnifiedOneXOneTable table = UnifiedTableParser.parseUnifiedOneXOneTable(oneSingle);
    int t0 = net.addTransition(0, table);

    BitSet s = new BitSet();
    s.set(0, false);
    s.set(1, false);
    s.set(2, false);
    Decoder d = new Decoder(net, Arrays.asList(t0));
    UnifiedPetriNet newNet = d.modified(new BitIndi(s));

    IUnifiedTable i = newNet.getTableForTransition(t0);
    assertEquals(parser.createString(i), "{[<FF><-2><FF><FF><FF>]}");

    assertTrue(d.getNrOfRules() == 1);


  }

  private final static String reciveTableStr = "{" +
      "[<FF><FF><FF><FF><FF><FF>]" + //
      "[<FF><FF><FF><FF><FF><FF>]" + //
      "[< 0>< 0>< 0>< 0>< 0><FF>]" + //
      "[<FF><FF><FF><FF><FF><FF>]" + //
      "[<FF><FF><FF><FF><FF><FF>]" + //
      "[<FF><FF><FF><FF><FF><FF>]" + //
      "}";

  @Test
  public void little_complex() {
    UnifiedPetriNet net = new UnifiedPetriNet();
    UnifiedOneXOneTable table = UnifiedTableParser.parseUnifiedOneXOneTable(oneSingle);
    int t0 = net.addTransition(0, table);
    UnifiedTwoXOneTable table2 = UnifiedTableParser.parseUnifiedTwoXOneTable(reciveTableStr);
    int t1 = net.addTransition(0, table2);

    Decoder d = new Decoder(net, Arrays.asList(t0, t1));
    assertTrue(6 == d.getNrOfRules());

    BitSet s = new BitSet();
    s.set(0, false);
    s.set(1, true);
    s.set(2, false);

    s.set(3, false);
    s.set(4, false);
    s.set(5, false);

    s.set(6, true);
    s.set(7, false);
    s.set(8, false);

    s.set(9, false);
    s.set(10, true);
    s.set(11, false);

    s.set(12, true);
    s.set(13, true);
    s.set(14, false);

    s.set(15, false);
    s.set(16, false);
    s.set(17, true);

    UnifiedPetriNet newNet = d.modified(new BitIndi(s));

    IUnifiedTable i = newNet.getTableForTransition(t0);
    assertEquals(parser.createString(i), "{[<FF>< 0><FF><FF><FF>]}");
    i = newNet.getTableForTransition(t1);
    assertEquals(parser.createString(i).replaceAll("\n", ""), rec);

  }

  private final static String rec = "{" +
      "[<FF><FF><FF><FF><FF>]" + //
      "[<FF><FF><FF><FF><FF>]" + //
      "[<-2><-1>< 0>< 1>< 2>]" + //
      "[<FF><FF><FF><FF><FF>]" + //
      "[<FF><FF><FF><FF><FF>]" + //
      "}";

}
