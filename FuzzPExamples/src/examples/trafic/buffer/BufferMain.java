package examples.trafic.buffer;

import java.util.HashMap;
import java.util.Map;

import Main.FuzzyPVizualzer;
import View.MainView;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.SynchronousFuzzyPetriExecutor;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;

public class BufferMain {

    // iP1 ~ lane length
    // iP2 ~ send lane length request
    // iP3 ~ enabled (green ligth)
    // iP4 ~ splitValue

    // oT7 ~ lane length (sent because of request)
    // oT5 ~ car out one
    // oT6 ~ car out two
    public static void main(String[] args) {
        BufferFuzzyPetriMaker maker = new BufferFuzzyPetriMaker();

        FuzzyDriver splitDriver = FuzzyDriver.createDriverFromMinMax(-1, 1);
        FuzzyDriver carDriver = FuzzyDriver.createDriverFromMinMax(-50, 50);
        SynchronousFuzzyPetriExecutor executor = new SynchronousFuzzyPetriExecutor(maker.net);
        FullRecorder rec = new FullRecorder();
        executor.setRecorder(rec);

        for (int i = 0; i < 50; i++) {
            Map<Integer, FuzzyToken> inpMap = new HashMap<>();
            inpMap.put(maker.iP1, carDriver.fuzzifie(30.0));
            inpMap.put(maker.iP4, splitDriver.fuzzifie(0.7));
            /*
             * if (i % 10 < 5) { inpMap.put(maker.iP3, new FuzzyToken(0.0, 0.0,
             * 0.0, 0.0, 1.0)); }
             */

            if (i % 7 < 3) {
                inpMap.put(maker.iP2, FuzzyToken.zeroToken());
            }

            executor.runTick(inpMap);
        }
        MainView main = FuzzyPVizualzer.visualize(maker.net, rec, maker.nameStore);

    }

}
