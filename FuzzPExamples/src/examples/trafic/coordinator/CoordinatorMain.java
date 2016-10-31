package examples.trafic.coordinator;

import java.util.HashMap;
import java.util.Map;

import Main.FuzzyPVizualzer;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.SynchronousFuzzyPetriExecutor;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;

public class CoordinatorMain {

    public static void main(String[] args) {

        FuzzyDriver commandDriver = FuzzyDriver.createDriverFromMinMax(-1, 1);
        CooridinatorFuzzyPetriMaker maker = new CooridinatorFuzzyPetriMaker();
        SynchronousFuzzyPetriExecutor executor = new SynchronousFuzzyPetriExecutor(maker.net);
        FullRecorder rec = new FullRecorder();
        executor.setRecorder(rec);

        for (int i = 0; i < 135; i++) {
            Map<Integer, FuzzyToken> inpMap = new HashMap<>();
            if (i % 12 == 0) {
                inpMap.put(maker.iP0, commandDriver.fuzzifie((i / 60.0) - 1.0));
            }
            executor.runTick(inpMap);
        }
        FuzzyPVizualzer.visualize(maker.net, rec, maker.nameStore);


    }

}
