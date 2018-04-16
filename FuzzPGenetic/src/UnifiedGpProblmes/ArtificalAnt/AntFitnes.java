package UnifiedGpProblmes.ArtificalAnt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import UnifiedGp.ProblemSpecificationImpl;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FiredTranitionRecorder;
import core.common.recoder.FullRecorder;
import core.common.recoder.IGeneralPetriBehavoiurRecorder;
import core.common.recoder.MultiRecorder;
import structure.ICreatureFitnes;

public class AntFitnes extends AbstactFitness implements ICreatureFitnes<UnifiedGpIndi> {

  public static final int MAX_MOOVES = 600;

  public AntFitnes() {
    super(problemSpecification());
    tableSup = () -> new MutableState(GridReader.copyGrid());

  }

  protected MutableState table;
  public Supplier<MutableState> tableSup;
  public PetriConversationResult originalRez;
  public FiredTranitionRecorder<UnifiedToken> rec;
  public FullRecorder<UnifiedToken> recc;
  private double moves;

  @Override
  public double evaluate(UnifiedGpIndi creature) {
    int size = creature.getSizes().size();
    double multi = sizeMulti(size);
    if(multi == 0.0){
      return 0.0;
    }
    rec = new FiredTranitionRecorder<>();
    // recc = new FullRecorder<>();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(rec /*
                                                                                  * ,
                                                                                  * recc
                                                                                  */));
    originalRez = calcFitnes(creature, multiRec);
    super.updateCreatureWithSimplification(creature, originalRez, rec);

    /*
     * calcFitnes(creature, new FiredTranitionRecorder<>()); int newMooves =
     * table.getFoodEaten(); String newStr = creature.getRoot().toString(); if
     * (newMooves != inital) { System.err.println("we have a problem sir " +
     * inital + " " + newMooves + "\n>" + originalStr + "\n>" + newStr + "\n>" +
     * originalStr.equals(newStr)); }
     */

    double multi2 = fireCountMulti(rec, MAX_MOOVES);
    return moves * multi * multi2;
  }




  private PetriConversationResult calcFitnes(UnifiedGpIndi creature, IGeneralPetriBehavoiurRecorder<UnifiedToken> rec) {
    PetriConversationResult rez = super.convert(creature);
    AntSimulator sim = new AntSimulator();
    moves = sim.simulate(rez.outNrToOutTr.getOrDefault(0, -1), rez.outNrToOutTr.getOrDefault(1, -1),
        rez.outNrToOutTr.getOrDefault(2, -1), rez.inpNrToInpPlace.getOrDefault(0, -1), rez.net, rec);
    
    
    return rez;
  }

  public static ProblemSpecification problemSpecification() {
    Map<Integer, Double> inpScale = new HashMap<>();
    inpScale.put(0, 1.0);
    Map<Integer, Double> outScale = new HashMap<>();
    outScale.put(0, 1.0);
    outScale.put(1, 1.0);
    outScale.put(2, 1.0);

    return new ProblemSpecificationImpl(20.0, 5, inpScale, outScale);

  }

}
