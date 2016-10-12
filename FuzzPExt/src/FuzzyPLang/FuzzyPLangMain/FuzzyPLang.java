package FuzzyPLang.FuzzyPLangMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import FuzzyPLang.NetBuilder.HiearchicalIntermediateNet;
import FuzzyPLang.NetBuilder.HierachicalBuilder;
import FuzzyPLang.Visitor.Visitor;
import FuzzyPLang.gen.FuzzyPLangLexer;
import FuzzyPLang.gen.FuzzyPLangParser;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

public class FuzzyPLang {

	private FuzzyPetriNet builtNet;
	private TransitionPlaceNameStore nameStrore;
	private String errors;

	public FuzzyPLang() {

	}

	public void loadFile(String file) {
		try {
			InputStream stream = new FileInputStream(file);
			createNet(stream);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Error with file reading " + file.toString());
		}
	}

	public void loadFile(File file) {
		try {
			InputStream stream = new FileInputStream(file);
			createNet(stream);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Error with file reading " + file.toString());
		}

	}

	public void createNet(InputStream is) {
		ANTLRInputStream stream;
		try {
			stream = new ANTLRInputStream(is);
		} catch (IOException e) {
			throw new RuntimeException("Error with file reading ");
		}
		FuzzyPLangLexer ll = new FuzzyPLangLexer(stream);
		CommonTokenStream cms = new CommonTokenStream(ll);
		FuzzyPLangParser parser = new FuzzyPLangParser(cms);
		ParseTree parseTree = parser.prog();
        Visitor vis = new Visitor();
		vis.visit(parseTree);
		HiearchicalIntermediateNet net = vis.getIntermeddiateNet();
		HierachicalBuilder bld = new HierachicalBuilder(net);
	
        /*
         * builtNet = builder.build(); nameStrore = builder.getNameStore();
         * errors = builder.getErrors();
         */
	}

	public FuzzyPetriNet getNet() {
		return builtNet;
	}

	public TransitionPlaceNameStore getNameStore() {
		return nameStrore;
	}

	public String getErrors() {
		return errors;
	}

	public static void main(String[] args) throws Exception {

        FuzzyPLang main = new FuzzyPLang();
        if (args.length > 0) {
            main.loadFile(args[0]);
        } else {
            main.createNet(System.in);
        }
        /*
         * MakerGenerator makerGenerator = new MakerGenerator(main.getNet(),
         * main.getNameStore(), "fuzzyP.exampleNets"); String res =
         * (makerGenerator.createMaker("ConcurentGenerated"));
         * System.err.println(main.getErrors()); System.out.println(res);
         */

	}

}
