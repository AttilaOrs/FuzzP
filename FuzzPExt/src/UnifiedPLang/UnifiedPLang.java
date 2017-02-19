package UnifiedPLang;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import UnifiedPLang.gen.UnifiedPLangLexer;
import UnifiedPLang.gen.UnifiedPLangParser;

public class UnifiedPLang {

  public static void main(String[] args) throws FileNotFoundException {

    InputStream stream = new FileInputStream("simple.upn");
    createNet(stream);


  }

  private static void createNet(InputStream is) {
    ANTLRInputStream stream;
    try {
      stream = new ANTLRInputStream(is);
    } catch (IOException e) {
      throw new RuntimeException("Error with file reading ");
    }
    UnifiedPLangLexer lexer = new UnifiedPLangLexer(stream);
    CommonTokenStream cms = new CommonTokenStream(lexer);
    UnifiedPLangParser parser = new UnifiedPLangParser(cms);
    UnifiedPLangVisitor visitor = new UnifiedPLangVisitor();
    visitor.visit(parser.prog());

  }

}
