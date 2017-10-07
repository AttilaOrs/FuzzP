package UETPNLisp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import UETPNLisp.gen.UETPNLispLexer;
import UETPNLisp.gen.UETPNLispParser;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;

public class UETPNLisp {
  
  public static INode<NodeType> fromFile(File file) {

    try {
      InputStream stream = new FileInputStream(file);
      return createFrom(stream);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Error with file reading " + file.toString());
    }
  }

  public static INode<NodeType> fromString(String str) {
    ByteArrayInputStream ii = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    return createFrom(ii);
  }

  private static INode<NodeType> createFrom(InputStream stream) {
    ANTLRInputStream antlrStream;
    try {
      antlrStream = new ANTLRInputStream(stream);
    } catch (IOException e) {
      throw new RuntimeException("Error with file reading ");
    }
    UETPNLispLexer lexer = new UETPNLispLexer(antlrStream);
    CommonTokenStream cms = new CommonTokenStream(lexer);
    UETPNLispParser parser = new UETPNLispParser(cms);
    Visitor visitor = new Visitor();
    visitor.visit(parser.prog());
    return visitor.getRoot();
  }

}
