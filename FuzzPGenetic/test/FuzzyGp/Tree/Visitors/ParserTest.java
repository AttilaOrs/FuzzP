package FuzzyGp.Tree.Visitors;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import FuzzyGp.Tree.INode;
import FuzzyGp.Tree.Parser;
import FuzzyGp.Tree.Visitors.ToSlispExpression;

public class ParserTest {

  public static ArrayList<String> testStrings;

  static {
    testStrings = new ArrayList<>();
    testStrings.add("(# (& i0 i1) (+ i2 (* o0 i1)) )");
    testStrings.add("(# (& z i1) (+ i2 (* o0 i1)) )");
    testStrings.add("(# (& i-be-0 i-nm-1) (+ i-nr-2 (* o0 i-cpe-11)) )");
    testStrings.add("(# (& o-wz-12 o-eip-3 ) (+ o-ein-2 (* o0 o-wz-11)) )");
    testStrings.add("(# (& f-0-2 f-1-12 ) (+ o-ein-2 (* o0 o-wz-11)) )");
  }

  @Test
  public void test() {
    for (String test : testStrings) {
      INode ll = Parser.parse(test);
      String retur = new ToSlispExpression(ll).getString();
      assertEquals(test.replaceAll(" ", ""), retur.replaceAll(" ", ""));
    }

  }

}
