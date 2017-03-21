package UnifiedPLang;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import FuzzyPLang.NetBuilder.HiearchicalIntermediateUnifiedNet;
import FuzzyPLang.NetBuilder.UnifiedHierachicalBuilder;
import UnifiedPLang.gen.UnifiedPLangLexer;
import UnifiedPLang.gen.UnifiedPLangParser;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.DrawableUnifiedPetriNet;
import core.UnifiedPetriLogic.ScaleDeducer;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import dotDrawer.PetriDotDrawerVerical;

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
    HiearchicalIntermediateUnifiedNet intermedate = visitor.getIntermediateNet();
    UnifiedHierachicalBuilder bld = new UnifiedHierachicalBuilder(intermedate, true);
    UnifiedPetriNet net = bld.buildPetriNet();
    if (bld.hasErrors()){
      System.err.println("");
      System.err.println(">>>>>>>>>>>>>>>>>>erros during Petri net buiding: <<<<<<<<<<<<<<<<<<<");
      System.err.println(bld.getErrors());
    } else {
      System.out.println("");
      System.out.println("No error found");
    }

    UnifiedPetriNet scaled = ScaleDeducer.deduceScale(net);
    List<Integer> uu = ScaleDeducer.unscaledPlaces(scaled);
    if (uu.isEmpty()) {
      System.out.println("All scales deduced");
    } else {
      System.out.println("Unable to deduce scales of " + uu);
    }

    PetriNetJsonSaver<UnifiedPetriNet> saver = new PetriNetJsonSaver<>();
    saver.save(scaled, "simple.json");
    PetriDotDrawerVerical dd = new PetriDotDrawerVerical(new DrawableUnifiedPetriNet(scaled));
    dd.makeImage("simple");
    

  }

}
