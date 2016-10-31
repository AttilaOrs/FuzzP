package examples.trafic.intersection;

import java.util.HashMap;

import Main.FuzzyPVizualzer;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.SynchronousFuzzyPetriExecutor;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;

public class IntersectionMain {

    public static void main(String[] args) {
        IntersectionFuzzyPetriMaker maker = new IntersectionFuzzyPetriMaker();
        FuzzyDriver splitDriver = FuzzyDriver.createDriverFromMinMax(-1, 1);
        FuzzyDriver carDriver = FuzzyDriver.createDriverFromMinMax(-50, 50);
        FuzzyDriver commandDriver = FuzzyDriver.createDriverFromMinMax(-1, 1);

        SynchronousFuzzyPetriExecutor executor = new SynchronousFuzzyPetriExecutor(maker.net);
        FullRecorder rec = new FullRecorder();
        executor.setRecorder(rec);

        for (int i = 0; i < 144; i++) {
            HashMap<Integer, FuzzyToken> inpMap = new HashMap<>();
            /* lane input */
            inpMap.put(maker.iP0, carDriver.fuzzifie(30.0));
            inpMap.put(maker.iP1, carDriver.fuzzifie(30.0));
            inpMap.put(maker.iP2, carDriver.fuzzifie(30.0));
            inpMap.put(maker.iP3, carDriver.fuzzifie(30.0));
            /* split inputs */
            inpMap.put(maker.iP4, splitDriver.fuzzifie(0.5));
            inpMap.put(maker.iP5, splitDriver.fuzzifie(0.5));
            inpMap.put(maker.iP6, splitDriver.fuzzifie(0.5));
            inpMap.put(maker.iP7, splitDriver.fuzzifie(0.5));
            /* request lane */
            switch(i % 4){
            case 0:
                inpMap.put(maker.iP8, FuzzyToken.zeroToken());
                break;
            case 1:
                inpMap.put(maker.iP9, FuzzyToken.zeroToken());
                break;
            case 2:
                inpMap.put(maker.iP10, FuzzyToken.zeroToken());
                break;
            case 3:
                inpMap.put(maker.iP11, FuzzyToken.zeroToken());
                break;
            }
            if(i % 12 == 0){
                double cmd = (((i + 1.0) / 144.0) - 0.5) * 2;
                inpMap.put(maker.iP12, commandDriver.fuzzifie(cmd));
            }
            executor.runTick(inpMap);
        }
        FuzzyPVizualzer.visualize(maker.net, rec, maker.nameStore);

    }

}
