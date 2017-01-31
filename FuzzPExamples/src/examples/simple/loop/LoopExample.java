package examples.simple.loop;

import java.util.HashMap;
import java.util.Map;

import Main.FuzzyPVizualzer;
import core.TableParser;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.AsyncronRunnableExecutor;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;

public class LoopExample {
    public FuzzyPetriNet net;
    /*input places */
    public int iP4 ;
    /*ordinary places */
    int P0, P1, P2, P3, P4 ;
    /* output transitions */
    public int oT4;
    /* table for out transitions */
    String oT4_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    /* ordinary transitions */
    int T0, T1, T2, T3;
    /* table for ordinary transitions */
    String T0_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";
    String T1_tableStr = "{"+
    	"[<PL><PM><ZR><PM><PL><FF>]"+//
    	"[<PL><PM><ZR><PM><PL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    	"[<NL><NM><ZR><NM><NL><FF>]"+//
    	"[<NL><NM><ZR><NM><NL><FF>]"+//
    	"[<FF><FF><FF><FF><FF><FF>]"+//
    "}";
    String T2_tableStr = "{[<NL,NL><PM,PM><FF,FF><NM,NM><PL,PL><FF,FF>]}";
    String T3_tableStr = "{[<NL><NM><ZR><PM><PL><FF>]}";

    public TransitionPlaceNameStore nameStore = new TransitionPlaceNameStore();

    private static TableParser parser = new TableParser(true);

    public LoopExample (){
        net = new FuzzyPetriNet();
        addPlaces();
        addTransitions();
        addArcs();
        putInitialMarking();

    }

    private void addPlaces(){
        iP4 = net.addInputPlace();
        P0 = net.addPlace();
        P1 = net.addPlace();
        P2 = net.addPlace();
        P3 = net.addPlace();
        P4 = net.addPlace();
        nameStore.addPlaceName(iP4, "iP4" );
        nameStore.addPlaceName(P0, "P0" );
        nameStore.addPlaceName(P1, "P1" );
        nameStore.addPlaceName(P2, "P2" );
        nameStore.addPlaceName(P3, "P3" );
        nameStore.addPlaceName(P4, "P4" );
    }

    private void addTransitions(){
        oT4 =  net.addOuputTransition(parser.parseOneXOneTable(oT4_tableStr));
        nameStore.addTransitionName(oT4, "oT4" );
        T0 =  net.addTransition(0,parser.parseTable(T0_tableStr));
        T1 =  net.addTransition(0,parser.parseTable(T1_tableStr));
        T2 =  net.addTransition(0,parser.parseTable(T2_tableStr));
        T3 =  net.addTransition(1,parser.parseTable(T3_tableStr));
        nameStore.addTransitionName(T0, "T0" );
        nameStore.addTransitionName(T1, "T1" );
        nameStore.addTransitionName(T2, "T2" );
        nameStore.addTransitionName(T3, "T3" );
    }

    private void addArcs(){
        net.addArcFromPlaceToTransition(P0, T0, 1.0);
        net.addArcFromPlaceToTransition(P1, T1, 1.0);
        net.addArcFromPlaceToTransition(iP4, T1, 1.0);
        net.addArcFromPlaceToTransition(P4, oT4, 1.0);
        net.addArcFromPlaceToTransition(P2, T2, 1.0);
        net.addArcFromPlaceToTransition(P3, T3, 1.0);
        net.addArcFromTransitionToPlace(T0, P1);
        net.addArcFromTransitionToPlace(T1, P2);
        net.addArcFromTransitionToPlace(T2, P3);
        net.addArcFromTransitionToPlace(T2, P4);
        net.addArcFromTransitionToPlace(T3, P1);
    }

    private void putInitialMarking(){
    net.setInitialMarkingForPlace(P0, FuzzyToken.buildFromString(initail));
    }

  public static String initail = "<0.82,0.1,0.08,0.0,0.0>";
  public static double[] inps = new double[] { -0.25, -0.75 };

  public static void main(String[] args) throws InterruptedException {
    LoopExample myMaker = new LoopExample();
    FuzzyDriver defaultDriver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);
    myMaker.net.addActionForOuputTransition(myMaker.oT4, tk -> {
      System.out.println(tk.shortString() + " " + defaultDriver.defuzzify(tk));
    });

    AsyncronRunnableExecutor executor = new AsyncronRunnableExecutor(myMaker.net, 20);
    FullRecorder recorder = new FullRecorder();
    executor.setRecorder(recorder);
    FuzzyDriver driver = FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);
    (new Thread(executor)).start();
    for (double inp : inps) {
      Map<Integer, FuzzyToken> inpTokens = new HashMap<>();
      inpTokens.put(myMaker.iP4, driver.fuzzifie(inp));
      executor.putTokenInInputPlace(inpTokens);
      Thread.sleep(20);
    }
    Thread.sleep(40);
    executor.stop();

    FuzzyPVizualzer.visualize(myMaker.net, recorder);
    System.out.println(parser.createString(TwoXOneTable.defaultTable()));

  }

}