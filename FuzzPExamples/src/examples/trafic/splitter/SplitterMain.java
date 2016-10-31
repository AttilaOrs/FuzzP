package examples.trafic.splitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Main.FuzzyPVizualzer;
import Main.Plotter;
import View.MainView;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.SynchronousFuzzyPetriExecutor;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;

public class SplitterMain {
    public static void main(String[] args) {
        FuzzyDriver splitDriver = FuzzyDriver.createDriverFromMinMax(-1, 1);
        FuzzyDriver carDriver = FuzzyDriver.createDriverFromMinMax(-50, 50);
        SplitterFuzzyPetriMaker maker = new SplitterFuzzyPetriMaker();
        SynchronousFuzzyPetriExecutor executor = new SynchronousFuzzyPetriExecutor(maker.net);
        FullRecorder rec = new FullRecorder();
        executor.setRecorder(rec);
        ArrayList<Double> out0 = new ArrayList<>();
        ArrayList<Double> out1 = new ArrayList<>();
        maker.net.addActionForOuputTransition(maker.oT0, ft -> out0.add(carDriver.defuzzify(ft)));
        maker.net.addActionForOuputTransition(maker.oT1, ft -> out1.add(carDriver.defuzzify(ft)));
        
        for (int i = 0; i < 500; i++) {
            Map<Integer, FuzzyToken> inpMap = new HashMap<>();
            inpMap.put(maker.iP0, carDriver.fuzzifie(22.0));
            inpMap.put(maker.iP1, splitDriver.fuzzifie(i / 500.0));
            executor.runTick(inpMap);
        }
        HashMap<String, List<Double>> toViz = new HashMap<>();
        toViz.put("out0", out0);
        toViz.put("out1", out1);
        Plotter plotter = new Plotter(toViz);

        MainView main = FuzzyPVizualzer.visualize(maker.net, rec);
        main.addInteractivePanel("rez", plotter.makeInteractivePlot());
    }

}
