package UnifiedGp.Behaviour;

import java.util.BitSet;
import java.util.Map;
import java.util.function.BiFunction;

import UnifiedGp.AbstactFitness;
import UnifiedGp.ProblemSpecification;
import core.UnifiedPetriLogic.UnifiedToken;
import structure.IGPGreature;
import structure.behaviour.IBeahviourDescriptor;

public abstract class BinaryDescritor<TDesc extends BinaryDescrition, TCreature extends IGPGreature>
    extends
      AbstactFitness
    implements
      IBeahviourDescriptor<TDesc, TCreature> {

  private int bitsForChannel;
  private BiFunction<BitSet, Integer, TDesc> constructor;
  private int index;

  public BinaryDescritor(ProblemSpecification ps, int bitsForChannel, BiFunction<BitSet, Integer, TDesc> constructor) {
    super(ps);
    this.bitsForChannel = bitsForChannel;
    this.constructor = constructor;
  }

  public TDesc createDescprition(InputOutputRecorder rec) {
    BitSet b = new BitSet();
    index = 0;
    for (int tickNr = 0; tickNr < rec.inps.size(); tickNr++) {
      Map<Integer, UnifiedToken> currentInp = rec.inps.get(tickNr);
      for (int inputChannelId = 0; inputChannelId < ps.getInputCount(); inputChannelId++) {
        int inputPlace = rez.inpNrToInpPlace.getOrDefault(inputChannelId, -1);
        UnifiedToken inpState = currentInp.getOrDefault(inputPlace, new UnifiedToken());
        putBits(inpState, b, ps.getScaleForInp(inputChannelId));
      }
      Map<Integer, UnifiedToken> currentOut = rec.outs.get(tickNr);
      for (int outChannelId = 0; outChannelId < ps.getOuputCount(); outChannelId++) {
        int outTr = rez.outNrToOutTr.getOrDefault(outChannelId, -1);
        UnifiedToken outTrState = currentOut.getOrDefault(outTr, new UnifiedToken());
        putBits(outTrState, b, ps.getScaleForOut(outChannelId));
      }

    }
    return constructor.apply(b, index);
  }

  private void putBits(UnifiedToken inpState, BitSet b, Double baseScale) {
    if(inpState.isPhi()) {
      for(int i = 0; i < bitsForChannel; i++) {
        b.set(index, false);
        index++;
      }
      return;
    } else {
      b.set(index);
      index++;
    }

    double intervalLeft = baseScale * -1.0;
    double intervalRight = baseScale;
    int remainingBits = bitsForChannel - 1;

    while (remainingBits > 0) {
      double midle = (intervalLeft + intervalRight) / 2;
      if (inpState.getValue() < midle) {
        b.set(index, false);
        index++;
        intervalRight = midle;
      } else {
        b.set(index);
        index++;
        intervalLeft = midle;
      }

      remainingBits--;
    }

  }



}
