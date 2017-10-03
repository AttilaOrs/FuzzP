package UnifiedPetriGA.easy;

import UnifiedPetriGA.BendingCreature;
import UnifiedPetriGA.mapper.BendingCreatureUTPNMapper;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.UnifiedPetriNet;

public class EasyFitnessSplitTest {

  public static void main(String args[]) {
    EasyFitnessSplit ff = new EasyFitnessSplit();
    PetriNetJsonSaver<UnifiedPetriNet> petriSaver = new PetriNetJsonSaver<UnifiedPetriNet>();
    UnifiedPetriNet ll = petriSaver.load("/home/ors/Dropbox/ujProjectGit/myNewProject/split_nou_upn.json",
        UnifiedPetriNet.class);

    testUnifiedPetriMaker maker = new testUnifiedPetriMaker();
    BendingCreatureUTPNMapper mapper = new BendingCreatureUTPNMapper(ll, true);
    BendingCreature cr = mapper.createCreatureBasedOnNet();
    System.out.println("fitness " + Double.toString(ff.evaluate(cr)));
    System.out.println("max(e_1/e_2-s) =" + ff.maxError);
    System.out.println("max( abs(1.0 - (oT0 + oT1) / i) * 100) = " + ff.maxPercent);
    System.out.println("carlost error " + ff.maxCarLost);
    // UnifiedVizualizer.visualize(ll, ff.getRec(), maker.nameStore);

  }

}
