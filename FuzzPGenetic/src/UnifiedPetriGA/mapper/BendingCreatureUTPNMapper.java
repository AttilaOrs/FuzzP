package UnifiedPetriGA.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import UnifiedPetriGA.BendingCreature;
import core.UnifiedPetriLogic.IUnifiedTable;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedPetriNetCloner;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;

public class BendingCreatureUTPNMapper {

  private UnifiedPetriNet net;
  private List<Integer> transitionToOptimize;
  private boolean optimizeOperators;
  private int nrOfGenes;

  public BendingCreatureUTPNMapper(UnifiedPetriNet net, List<Integer> transitionsToOptimzie, boolean optimizeOperator) {
    this.net = net;
    this.transitionToOptimize = Collections.unmodifiableList(transitionsToOptimzie);
    this.optimizeOperators = optimizeOperator;
    nrOfGenes = -1;
  }

  public BendingCreatureUTPNMapper(UnifiedPetriNet net, boolean optimizeOperstors) {
    this(net, IntStream.range(0, net.getNrOfTransition()).mapToObj(i -> i).collect(Collectors.toList()),
        optimizeOperstors);
  }

  public int getNrOfGenes() {
    if (nrOfGenes == -1) {
      int sum = 0;
      for (Integer trId : transitionToOptimize) {
        if (!net.isOuputTransition(trId)) {
          IUnifiedTable table = net.getTableForTransition(trId);
          if (table instanceof UnifiedOneXOneTable) {
            sum += 5;
          }
          if (table instanceof UnifiedOneXTwoTable) {
            sum += 10;
          }
          if (table instanceof UnifiedTwoXOneTable) {
            sum += 25 + ((optimizeOperators) ? 1 : 0);
          }
          if (table instanceof UnifiedTwoXTwoTable) {
            sum += 50 + ((optimizeOperators) ? 1 : 0);
          }
        }
      }
      nrOfGenes = sum;
    }
    return nrOfGenes;
  }

  public UnifiedPetriNet decode(BendingCreature creature) {
    if (creature.gen.size() != getNrOfGenes()) {
      throw new RuntimeException(" WRONG gene size");
    }
    Map<Integer, IUnifiedTable> modifed = new HashMap<>();
    UnifiedTableFactory tableFactory = new UnifiedTableFactory(optimizeOperators);
    Queue<Integer> ll = new LinkedList<>(creature.gen);
    for (Integer transId : this.transitionToOptimize) {
      if (!net.isOuputTransition(transId)) {
        IUnifiedTable rez = tableFactory.create(net.getTableForTransition(transId), ll);
        modifed.put(transId, rez);
      }
    }
    return UnifiedPetriNetCloner.cloneUnifiedPetriNetWithModifiedTabler(this.net, modifed);
  }

  public BendingCreature createCreatureBasedOnNet() {
    LinkedList<Integer> ll = new LinkedList<>();
    UnifiedTableFactory tableFactory = new UnifiedTableFactory(optimizeOperators);
    for (Integer transId : this.transitionToOptimize) {
      if (!net.isOuputTransition(transId)) {
        tableFactory.putValuesFromTable(net.getTableForTransition(transId), ll);
      }
    }
    return new BendingCreature(ll);

  }

  public BendingCreatureUTPNMapper myClone() {
    UnifiedPetriNet clonedNet = UnifiedPetriNetCloner.cloneUnifiedPetriNet(net);
    List<Integer> trToOptimize = new ArrayList<>(this.transitionToOptimize);
    return new BendingCreatureUTPNMapper(clonedNet, trToOptimize, this.optimizeOperators);

  }

}
