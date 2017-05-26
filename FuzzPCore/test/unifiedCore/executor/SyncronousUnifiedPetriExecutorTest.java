package unifiedCore.executor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Before;

import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.common.recoder.DebuggerRecorder;
import core.common.recoder.FullRecorder;

public class SyncronousUnifiedPetriExecutorTest {

  private UnifiedTableParser tableParser;
  private UnifiedPetriNet petriNet;

  @Before
  public void setUp() {
    tableParser = new UnifiedTableParser();
    petriNet = new UnifiedPetriNet();

  }

    String allTwo = "@+@" +
        "{[<2><2><2><2><2>]" +
        " [<2><2><2><2><2>]" +
        " [<2><2><2><2><2>]" +
        " [<2><2><2><2><2>]" +
        " [<2><2><2><2><2>]}";

  public void test() {
    int inpPlace0 = petriNet.addInputPlace(2.0);
    int inpPlace1 = petriNet.addInputPlace(2.0);
    int pl2 = petriNet.addPlace(4.0);
    int tr0 = petriNet.addTransition(0, tableParser.parseTwoXOneTable(allTwo));
    petriNet.addArcFromPlaceToTransition(inpPlace0, tr0);
    petriNet.addArcFromPlaceToTransition(inpPlace1, tr0);
    petriNet.addArcFromTransitionToPlace(tr0, pl2);
    int ouTr = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(pl2, ouTr);
    
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(petriNet);
    DebuggerRecorder<UnifiedToken> debugRec = new DebuggerRecorder<>();
    exec.setRecorder(debugRec);
    Map<Integer, UnifiedToken> inpMap = new HashMap<>();
    inpMap.put(inpPlace0, new UnifiedToken(1.0));
    inpMap.put(inpPlace1, new UnifiedToken(1.0));

    exec.runTick(inpMap);
    
    // PetriDotDrawerVerical draw = new PetriDotDrawerVerical(new
    // DrawableUnifiedPetriNet(petriNet));
    // draw.makeImage("first_try");
  }

  String reader = "" +
      "{[<-2><-1>< 0>< 1>< 2>]" +
      " [<-2><-1>< 0>< 1>< 2>]" +
      " [<-2><-1>< 0>< 1>< 2>]" +
      " [<-2><-1>< 0>< 1>< 2>]" +
      " [<-2><-1>< 0>< 1>< 2>]}";

  String positivEnabled = "{[<FF,FF><FF,FF><FF,FF><1,1><1,2>]}";
  String neagtiveEnabled = "{[<-1,-2><-1,-1><FF,FF><FF,FF><FF,FF>]}";

  int cntr = 0;

  public void firstExampleTest() {
    int p0 = petriNet.addPlace(1.0);
    petriNet.setInitialMarkingForPlace(p0, new UnifiedToken(0.0));
    int iP1 = petriNet.addInputPlace(1.0);
    int p2 = petriNet.addPlace(1.0);
    int t0 = petriNet.addTransition(0, tableParser.parseTwoXOneTable(reader));
    petriNet.addArcFromPlaceToTransition(p0, t0);
    petriNet.addArcFromPlaceToTransition(iP1, t0);
    petriNet.addArcFromTransitionToPlace(t0, p2);

    int t1 = petriNet.addTransition(0, tableParser.parseOneXTwoTable(positivEnabled));
    petriNet.addArcFromPlaceToTransition(p2, t1);
    int p3 = petriNet.addPlace(1.0);
    petriNet.addArcFromTransitionToPlace(t1, p3);
    int ot2 = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p3, ot2);
    int p4 = petriNet.addPlace(1.0); // itt folytatod
    petriNet.addArcFromTransitionToPlace(t1, p4);

    int t3 = petriNet.addTransition(0, tableParser.parseOneXTwoTable(neagtiveEnabled));
    petriNet.addArcFromPlaceToTransition(p2, t3);
    int p5 = petriNet.addPlace(1.0);
    petriNet.addArcFromTransitionToPlace(t3, p5);
    int ot4 = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p5, ot4);
    int p6 = petriNet.addPlace(1.0); // itt folytatod
    petriNet.addArcFromTransitionToPlace(t3, p6);

    int t5 = petriNet.addTransition(1, UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p4, t5);
    petriNet.addArcFromTransitionToPlace(t5, p0);

    int t6 = petriNet.addTransition(1, UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p6, t6);
    petriNet.addArcFromTransitionToPlace(t6, p0);



    StringBuilder firstOutput = new StringBuilder();
    StringBuilder secondOuput = new StringBuilder();
    StringBuilder inputs = new StringBuilder();
    petriNet.addActionForOuputTransition(ot2,
        utk -> firstOutput.append(cntr).append(" ").append(utk.getValue()).append("\n"));

    petriNet.addActionForOuputTransition(ot4,
        utk -> secondOuput.append(cntr).append(" ").append(utk.getValue()).append("\n"));

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(petriNet);
    DebuggerRecorder<UnifiedToken> debugRec = new DebuggerRecorder<>();
    exec.setRecorder(debugRec);
    for (cntr = 0; cntr < 100; cntr++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      inp.put(iP1, new UnifiedToken(Math.sin((cntr + 0.00001) / 10.0)));
      exec.runTick(inp);
      inputs.append(cntr).append(" ").append(Math.sin((cntr + 0.00001) / 10.0)).append("\n");
    }

    // PetriDotDrawerVerical draw = new PetriDotDrawerVerical(new
    // DrawableUnifiedPetriNet(petriNet));
    // draw.makeImage("first_try");
    // writeFile(inputs, "input_ex1");
    // writeFile(firstOutput, "f_out_ex1");
    // writeFile(secondOuput, "s_out_ex1");
  }

  String allTwoMinus = "@-@" +
      "{[<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]}";

  public void firstExampleTest_complexer() {
    int p0 = petriNet.addPlace(4.0);
    petriNet.setInitialMarkingForPlace(p0, new UnifiedToken(0.0));
    int iP1 = petriNet.addInputPlace(4.0);
    int p2 = petriNet.addPlace(4.0);
    int t0 = petriNet.addTransition(0, tableParser.parseTwoXOneTable(reader));
    petriNet.addArcFromPlaceToTransition(p0, t0);
    petriNet.addArcFromPlaceToTransition(iP1, t0);
    petriNet.addArcFromTransitionToPlace(t0, p2);

    int t7 = petriNet.addTransition(0, tableParser.parseTwoXOneTable(allTwoMinus));
    petriNet.addArcFromPlaceToTransition(p2, t7);
    int p7 = petriNet.addPlace(4.0);
    petriNet.addArcFromTransitionToPlace(t7, p7);

    int p8 = petriNet.addPlace(4.0);
    petriNet.setInitialMarkingForPlace(p8, new UnifiedToken(-1.000000001));
    int p9 = petriNet.addPlace(4.0);
    int t8 = petriNet.addTransition(0, UnifiedOneXTwoTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p8, t8);
    petriNet.addArcFromTransitionToPlace(t8, p8);
    petriNet.addArcFromTransitionToPlace(t8, p9);
    petriNet.addArcFromPlaceToTransition(p9, t7);

    int t1 = petriNet.addTransition(0, tableParser.parseOneXTwoTable(positivEnabled));
    petriNet.addArcFromPlaceToTransition(p7, t1);
    int p3 = petriNet.addPlace(4.0);
    petriNet.addArcFromTransitionToPlace(t1, p3);
    int ot2 = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p3, ot2);
    int p4 = petriNet.addPlace(4.0); // itt folytatod
    petriNet.addArcFromTransitionToPlace(t1, p4);

    int t3 = petriNet.addTransition(0, tableParser.parseOneXTwoTable(neagtiveEnabled));
    petriNet.addArcFromPlaceToTransition(p7, t3);
    int p5 = petriNet.addPlace(4.0);
    petriNet.addArcFromTransitionToPlace(t3, p5);
    int ot4 = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p5, ot4);
    int p6 = petriNet.addPlace(4.0); // itt folytatod
    petriNet.addArcFromTransitionToPlace(t3, p6);

    int t5 = petriNet.addTransitionVariableDelay(1.0, UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p4, t5);
    petriNet.addArcFromTransitionToPlace(t5, p0);

    int t6 = petriNet.addTransition(1, UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p6, t6);
    petriNet.addArcFromTransitionToPlace(t6, p0);


    StringBuilder firstOutput = new StringBuilder();
    StringBuilder secondOuput = new StringBuilder();
    StringBuilder inputs = new StringBuilder();
    petriNet.addActionForOuputTransition(ot2,
        utk -> firstOutput.append(cntr).append(" ").append(utk.getValue() * 4.0).append("\n"));

    petriNet.addActionForOuputTransition(ot4,
        utk -> secondOuput.append(cntr).append(" ").append(utk.getValue() * 4.0).append("\n"));

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(petriNet);
    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    exec.setRecorder(rec);
    for (cntr = 0; cntr < 40; cntr++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      double inpuValue = ((cntr / 10) + 1.0) * ((cntr % 10 < 5) ? 1.0 : -1.0);
      inp.put(iP1, new UnifiedToken(inpuValue));
      exec.runTick(inp);
      inputs.append(cntr).append(" ").append(inpuValue).append("\n");
    }

    HandCheckedTester.saveHandCheckedScenario(new File("variable_delay.json"), petriNet, rec);
    // PetriDotDrawerVerical draw = new PetriDotDrawerVerical(new
    // DrawableUnifiedPetriNet(petriNet));
    // draw.makeImage("first_try_c");
    // writeFile(inputs, "input_ex1_c");
    // writeFile(firstOutput, "f_out_ex1_c");
    // writeFile(secondOuput, "s_out_ex1_c");

  }

  private static void writeFile(StringBuilder inputs, String string) {
    try {
      BufferedWriter bf = new BufferedWriter(new FileWriter(string + ".dat"));
      bf.write(inputs.toString());
      bf.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  String allTwoMinusTwo = "@+@" +
      "{[<2,2><2,2><2,2><2,2><2,2>]" +
      " [<2,2><2,2><2,2><2,2><2,2>]" +
      " [<2,2><2,2><2,2><2,2><2,2>]" +
      " [<2,2><2,2><2,2><2,2><2,2>]" +
      " [<2,2><2,2><2,2><2,2><2,2>]}";

  String critria = "" +
      "{[<0,0><FF,FF><FF,FF><FF,FF><FF,FF>]" +
      " [<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" +
      " [<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" +
      " [<FF,FF><FF,FF><FF,FF><FF,FF><FF,FF>]" +
      " [<FF,FF><FF,FF><FF,FF><FF,FF><0,0>]}";

  public void Two_loops_tets() {
    int p0 = petriNet.addPlace(2.0);
    petriNet.setInitialMarkingForPlace(p0, new UnifiedToken(0.0));
    
    int t0 = petriNet.addTransition(0, UnifiedOneXTwoTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p0, t0);

    int p1 = petriNet.addPlace(2.0);
    petriNet.addArcFromTransitionToPlace(t0, p1);

    int p2 = petriNet.addPlace(2.0);
    petriNet.addArcFromTransitionToPlace(t0, p2);

    int ip3 = petriNet.addInputPlace(2.0);
    int t1 = petriNet.addTransition(0, tableParser.parseTwoXTwoTable(allTwoMinusTwo));
    petriNet.addArcFromPlaceToTransition(p1, t1);
    petriNet.addArcFromPlaceToTransition(ip3, t1);

    int p4 = petriNet.addPlace(2.0);
    int p5 = petriNet.addPlace(2.0);
    petriNet.addArcFromTransitionToPlace(t1, p4);// connection point
    petriNet.addArcFromTransitionToPlace(t1, p5);// for output
    int ot2 = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p5, ot2);
    int t3 = petriNet.addTransition(3, UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p4, t3);
    petriNet.addArcFromTransitionToPlace(t3, p1);

    int ip5 = petriNet.addInputPlace(2.0);
    int t4 = petriNet.addTransition(0, tableParser.parseTwoXTwoTable(allTwoMinusTwo));
    petriNet.addArcFromPlaceToTransition(p2, t4);
    petriNet.addArcFromPlaceToTransition(ip5, t4);

    int p6 = petriNet.addPlace(2.0); // connection point
    int p7 = petriNet.addPlace(2.0); // for ouput
    petriNet.addArcFromTransitionToPlace(t4, p6);
    petriNet.addArcFromTransitionToPlace(t4, p7);
    int ot8 = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p7, ot8);
    int t9 = petriNet.addTransition(5, UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p6, t9);
    petriNet.addArcFromTransitionToPlace(t9, p2);

    int t10 = petriNet.addTransition(0, tableParser.parseTwoXTwoTable(critria));
    petriNet.addArcFromPlaceToTransition(p6, t10);
    petriNet.addArcFromPlaceToTransition(p4, t10);
    petriNet.addArcFromTransitionToPlace(t10, p0);

    int p10 = petriNet.addPlace(2.0);
    petriNet.addArcFromTransitionToPlace(t10, p10);
    int ot11 = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p10, ot11);



    StringBuilder firstOutput = new StringBuilder();
    StringBuilder secondOuput = new StringBuilder();
    StringBuilder thirdOuput = new StringBuilder();
    StringBuilder firstInp = new StringBuilder();
    StringBuilder secondInp = new StringBuilder();
    petriNet.addActionForOuputTransition(ot2,
        utk -> firstOutput.append(cntr).append(" ").append(utk.getValue() * 2.0).append("\n"));

    petriNet.addActionForOuputTransition(ot8,
        utk -> secondOuput.append(cntr).append(" ").append(utk.getValue() * 2.0).append("\n"));

    petriNet.addActionForOuputTransition(ot11,
        utk -> thirdOuput.append(cntr).append(" ").append(utk.getValue() * 2.0).append("\n"));

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(petriNet);
    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    exec.setRecorder(rec);
    for (cntr = 0; cntr <= 60; cntr++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      if (cntr % 3 == 0) {
        double inpValue = Math.sin((cntr + 0.00001) / 10.0);
        inp.put(ip3, new UnifiedToken(inpValue));
        firstInp.append(cntr).append(" ").append(inpValue).append("\n");
      }
      if (cntr % 5 == 0) {
        double inpValue = Math.sin((cntr + 0.00001) / 10.0 + 0.5);
        inp.put(ip5, new UnifiedToken(inpValue));
        secondInp.append(cntr).append(" ").append(inpValue).append("\n");
      }

      exec.runTick(inp);
    }
    HandCheckedTester.saveHandCheckedScenario(new File("two_loop.json"),
        petriNet, rec);

    // PetriDotDrawerVerical draw = new PetriDotDrawerVerical(new
    // DrawableUnifiedPetriNet(petriNet));
    // draw.makeImage("two_loops");
    // writeFile(firstInp, "first_inp_to_loop");
    // writeFile(secondInp, "second_inp_to_loop");
    // writeFile(firstOutput, "first_out_to_loop");
    // writeFile(secondOuput, "second_out_to_loop");
    // writeFile(thirdOuput, "third_out_to_loop");
  }

  String allTwoWithPlus = "@+@" +
      "{[<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]" +
      " [<2><2><2><2><2>]}";

  String reader2 = "" +
      "{[<-2><-2><-2><-2><-2>]" +
      " [<-1><-1><-1><-1><-1>]" +
      " [< 0>< 0>< 0>< 0>< 0>]" +
      " [< 1>< 1>< 1>< 1>< 1>]" +
      " [< 2>< 2>< 2>< 2>< 2>]}";

  String t7_table = "" +
      "{[<-2,0><-2,0><-2,0><-2,0><-2,0>]" +
      " [<-1,0><-1,0><-1,0><-1,0><-1,0>]" +
      " [< 0,0>< 0,0>< 0,0>< 0,0>< 0,0>]" +
      " [< 1,0>< 1,0>< 1,0>< 1,0>< 1,0>]" +
      " [< 2,0>< 2,0>< 2,0>< 2,0>< 2,0>]}";

  public void variable_finish_loop() {
    int p0 = petriNet.addPlace(1.0);
    petriNet.setInitialMarkingForPlace(p0, new UnifiedToken(0.0));

    int t0 = petriNet.addTransition(0, UnifiedOneXTwoTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p0, t0);

    int p1 = petriNet.addPlace(20.0);
    petriNet.addArcFromTransitionToPlace(t0, p1);

    int p2 = petriNet.addPlace(1.0);
    petriNet.addArcFromTransitionToPlace(t0, p2);

    int ip3 = petriNet.addInputPlace(1.0);
    
    int t1 = petriNet.addTransition(0, tableParser.parseTwoXOneTable(allTwoWithPlus));
    petriNet.addArcFromPlaceToTransition(p1, t1);
    petriNet.addArcFromPlaceToTransition(ip3, t1);

    int p4 = petriNet.addPlace(20.0);
    petriNet.addArcFromTransitionToPlace(t1, p4);

    int t2 = petriNet.addTransition(1, UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p4, t2);
    petriNet.addArcFromTransitionToPlace(t2, p1);

    int ip5 = petriNet.addInputPlace(20.0);
    int t3 = petriNet.addTransitionVariableDelay(1.0, tableParser.parseTwoXOneTable(reader2));
    petriNet.addArcFromPlaceToTransition(ip5, t3);
    petriNet.addArcFromPlaceToTransition(p2, t3);

    int p6 = petriNet.addPlace(1.0);
    petriNet.addArcFromTransitionToPlace(t3, p6);
    
    int t7 = petriNet.addTransition(0, tableParser.parseTwoXTwoTable(t7_table));
    petriNet.addArcFromPlaceToTransition(p4, t7);
    petriNet.addArcFromPlaceToTransition(p6, t7);

    int p7 = petriNet.addPlace(20.0);
    petriNet.addArcFromTransitionToPlace(t7, p7);

    int ot8 = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p7, ot8);

    petriNet.addArcFromTransitionToPlace(t7, p0);


    StringBuilder ouput = new StringBuilder();
    StringBuilder firstInp = new StringBuilder();
    StringBuilder secondInp = new StringBuilder();
    petriNet.addActionForOuputTransition(ot8,
        utk -> ouput.append(cntr).append(" ").append(utk.getValue()).append("\n"));

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(petriNet);

    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    exec.setRecorder(rec);

    for (cntr = 0; cntr < 100; cntr++) {
      double fi = Math.sin((cntr + 0.0001) / 20.0);
      double se = Math.floor((cntr + 0.0001) / 10.0) + 1.0;
      Map<Integer, UnifiedToken> inpMap = new HashMap<>();
      inpMap.put(ip3, new UnifiedToken(fi));
      inpMap.put(ip5, new UnifiedToken(se));
      firstInp.append(cntr).append(" ").append(fi).append("\n");
      secondInp.append(cntr).append(" ").append(se).append("\n");
      exec.runTick(inpMap);
    }

    // HandCheckedTester.saveHandCheckedScenario(new
    // File("variable_delay_loop.json"), petriNet, rec);
    // PetriDotDrawerVerical draw = new PetriDotDrawerVerical(new
    // DrawableUnifiedPetriNet(petriNet));
    // draw.makeImage("variale_finish_lopp");
    // writeFile(ouput, "varilable_loop_out");
    // writeFile(firstInp, "variable_loop_first_inp");
    // writeFile(secondInp, "variable_loop_second_inp");

  }

  String simple = "" +
      "{[< 2>< 2>< 2>< 2>< 2>]" +
      " [< 2>< 2>< 2>< 2>< 2>]" +
      " [< 2>< 2>< 2>< 2>< 2>]" +
      " [< 2>< 2>< 2>< 2>< 2>]" +
      " [< 2>< 2>< 2>< 2>< 2>]}";

  String arc_inhib = "" +
      "{[<FF><FF><FF><FF><FF>< 2>]" +
      " [<FF><FF><FF><FF><FF>< 1>]" +
      " [<FF><FF><FF><FF><FF>< 0>]" +
      " [<FF><FF><FF><FF><FF><-1>]" +
      " [<FF><FF><FF><FF><FF><-2>]" +
      " [<FF><FF><FF><FF><FF><FF>]}";

  String bending = "@*@" +
      "{[< 2, 2>< 2, 2>< 2, 2>< 1, 1>< 0, 0>]" +
      " [< 2, 2>< 2, 2>< 1, 1>< 0, 0><-1,-1>]" +
      " [< 2, 2>< 1, 1>< 0, 0><-1,-1><-2,-2>]" +
      " [< 1, 1>< 0, 0><-1,-1><-2,-2><-2,-2>]" +
      " [< 0, 0><-1,-1><-2,-2><-2,-2><-2,-2>]}";

  String bending_ = "@*@" +
      "{[< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2>]" +
      " [< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2>]" +
      " [< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2>]" +
      " [< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2>]" +
      " [< 2, 2>< 2, 2>< 2, 2>< 2, 2>< 2, 2>]}";

  public void arc_inhibitor() {
    int ip0 = petriNet.addInputPlace(1.0);
    int p1 = petriNet.addPlace(1.0);

    petriNet.setInitialMarkingForPlace(p1, new UnifiedToken(0.0));

    int t0 = petriNet.addTransition(0, tableParser.parseTwoXOneTable(simple));
    petriNet.addArcFromPlaceToTransition(ip0, t0);
    petriNet.addArcFromPlaceToTransition(p1, t0);

    int p2 = petriNet.addPlace(2.0);
    petriNet.addArcFromTransitionToPlace(t0, p2);

    int t1 = petriNet.addTransition(1, UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p2, t1);
    petriNet.addArcFromTransitionToPlace(t1, p1);
    
    UnifiedTableParser phiTableParser = new UnifiedTableParser(true);
    int p3 = petriNet.addPlace(1.0);
    petriNet.setInitialMarkingForPlace(p3, new UnifiedToken(1.0));
    int t2 = petriNet.addTransition(1, phiTableParser.parseTwoXOneTable(arc_inhib));
    petriNet.addArcFromPlaceToTransition(p3, t2);
    petriNet.addArcFromPlaceToTransition(p1, t2);

    int p4 = petriNet.addPlace(1.0);
    petriNet.addArcFromTransitionToPlace(t2, p4);
    int ip5 = petriNet.addInputPlace(20.0);


    int t3 = petriNet.addTransition(0, tableParser.parseTwoXTwoTable(bending));
    petriNet.addArcFromPlaceToTransition(ip5, t3);
    petriNet.addArcFromPlaceToTransition(p4, t3);

    petriNet.addArcFromTransitionToPlace(t3, p3);

    int p6 = petriNet.addPlace(1.0);
    petriNet.addArcFromTransitionToPlace(t3, p6);
    int ot4 = petriNet.addOuputTransition(UnifiedOneXOneTable.defaultTable());
    petriNet.addArcFromPlaceToTransition(p6, ot4);

    StringBuilder ouput_x6 = new StringBuilder();
    StringBuilder input_x0 = new StringBuilder();
    StringBuilder input_x5 = new StringBuilder();
    
    petriNet.addActionForOuputTransition(ot4,
        utk -> ouput_x6.append(cntr).append(" ").append(utk.getValue()).append("\n"));
    
    int[] eventTick = new int[] { 1, 2, 3, 7, 11, 13, 17, 23, 29, 31, 37 };

    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(petriNet);
    FullRecorder<UnifiedToken> rec = new FullRecorder<>();

    exec.setRecorder(rec);

    for (cntr = 0; cntr < 40; cntr++) {
      Map<Integer, UnifiedToken> inp = new HashMap<>();
      double inp_x5 = -0.9;
      inp.put(ip5, new UnifiedToken(inp_x5));
      input_x5.append(cntr).append(" ").append(inp_x5).append("\n");
      if (IntStream.of(eventTick).anyMatch(x -> x == cntr)) {
        inp.put(ip0, new UnifiedToken(0.0));
        input_x0.append(cntr).append(" ").append(0.0).append("\n");
      }
      exec.runTick(inp);
    }
    HandCheckedTester.saveHandCheckedScenario(new File("inhibitor.json"), petriNet, rec);


    // PetriDotDrawerVerical draw = new PetriDotDrawerVerical(new
    // DrawableUnifiedPetriNet(petriNet));
    // draw.makeImage("arc_inhib");
    // writeFile(input_x5, "i_arc_inhib_x5");
    // writeFile(input_x0, "i_arc_inhib_x0");
    // writeFile(ouput_x6, "o_arc_inhib_x6");

  }


}
