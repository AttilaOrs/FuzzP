package examples.trafic.controller;

import java.util.HashMap;

import Main.FuzzyPVizualzer;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.AsyncrounousThreadlessExecutor;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
public class ControllerMain {

    public static void main(String[] args) {
        ControllerFuzzyPetriMaker maker = new ControllerFuzzyPetriMaker();
        FuzzyDriver carDriver = FuzzyDriver.createDriverFromMinMax(-50, 50);
        FuzzyDriver commandDriver = FuzzyDriver.createDriverFromMinMax(-1, 1);
        AsyncrounousThreadlessExecutor exec = new AsyncrounousThreadlessExecutor(maker.net);
        FullRecorder rec = new FullRecorder();
        exec.setRecorder(rec);
        maker.net.addActionForOuputTransition(maker.oT0, ft -> {
            HashMap<Integer, FuzzyToken> inps = new HashMap<>();
            inps.put(maker.iP0, carDriver.fuzzifie(30.0));
            exec.putTokenInInputPlace(inps);
        });
        maker.net.addActionForOuputTransition(maker.oT1, ft -> {
            HashMap<Integer, FuzzyToken> inps = new HashMap<>();
            inps.put(maker.iP1, carDriver.fuzzifie(20.0));
            exec.putTokenInInputPlace(inps);
        });
        maker.net.addActionForOuputTransition(maker.oT2, ft -> {
            HashMap<Integer, FuzzyToken> inps = new HashMap<>();
            inps.put(maker.iP2, carDriver.fuzzifie(13.0));
            exec.putTokenInInputPlace(inps);
        });
        maker.net.addActionForOuputTransition(maker.oT3, ft -> {
            HashMap<Integer, FuzzyToken> inps = new HashMap<>();
            inps.put(maker.iP3, carDriver.fuzzifie(18.0));
            exec.putTokenInInputPlace(inps);
        });
        for (int i = 0; i < 144; i++) {
            exec.startNewTick();
        }
        FuzzyPVizualzer.visualize(maker.net, rec, maker.nameStore);



    }

}
