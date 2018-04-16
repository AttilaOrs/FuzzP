package UnifiedGpProblmes.ArtificalAnt.Behaviour;

import java.util.Arrays;
import java.util.function.Supplier;

import UnifiedGp.Behaviour.BinaryDescritor;
import UnifiedGp.Behaviour.InputOutputRecorder;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Visitors.PetriConversationResult;
import UnifiedGpProblmes.ArtificalAnt.AntFitnes;
import UnifiedGpProblmes.ArtificalAnt.AntSimulator;
import UnifiedGpProblmes.ArtificalAnt.GridReader;
import UnifiedGpProblmes.ArtificalAnt.MutableState;
import core.UnifiedPetriLogic.UnifiedToken;
import core.common.recoder.FiredTranitionRecorder;
import core.common.recoder.IGeneralPetriBehavoiurRecorder;
import core.common.recoder.MultiRecorder;

public class AntBinaryDescriptor extends BinaryDescritor<AntBinaryDescription, UnifiedGpIndi> {

  public AntBinaryDescriptor() {
    super(AntFitnes.problemSpecification(), 1, AntBinaryDescription::new);
    tableSup = () -> new MutableState(GridReader.copyGrid());
  }

  protected MutableState table;
  protected Supplier<MutableState> tableSup;
  public PetriConversationResult originalRez;
  public FiredTranitionRecorder<UnifiedToken> rec;
  public InputOutputRecorder recc;
  private int food;

  @Override
  public AntBinaryDescription evaluate(UnifiedGpIndi creature) {
    int size = creature.getSizes().size();
    double multi = sizeMulti(size);
    if (multi == 0.0) {
      return super.createDescprition(InputOutputRecorder.fakeRecorder(AntFitnes.MAX_MOOVES));
    }
    rec = new FiredTranitionRecorder<>();
    recc = new InputOutputRecorder();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(rec, recc));

    originalRez = calcFitnes(creature, multiRec);

    super.updateCreatureWithSimplification(creature, originalRez, rec);
    AntBinaryDescription desc = super.createDescprition(recc);

    double multi2 = fireCountMulti(rec, AntFitnes.MAX_MOOVES);
    desc.setPropiertise(food, multi * multi2, size);

    return desc;
  }

  private PetriConversationResult calcFitnes(UnifiedGpIndi creature, IGeneralPetriBehavoiurRecorder<UnifiedToken> rec) {
    PetriConversationResult rez = super.convert(creature);
    AntSimulator sim = new AntSimulator();
    food = sim.simulate(rez.outNrToOutTr.getOrDefault(0, -1), rez.outNrToOutTr.getOrDefault(1, -1),
        rez.outNrToOutTr.getOrDefault(2, -1), rez.inpNrToInpPlace.getOrDefault(0, -1), rez.net, rec);

    return rez;
  }


}
