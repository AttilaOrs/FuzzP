package AlgoImpl.pools.behaviour;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import AlgoImpl.pools.CreaturePoolWithStreamsTest;
import structure.IOperatorFactory;
import structure.behaviour.IBeahviourDescriptor;
import structure.behaviour.IBehaviourBasedFitness;
import structure.behaviour.IBehaviourDescriponDataStore;

public class BehaviourParalellPoolTest extends CreaturePoolWithStreamsTest {

  ArrayList<IOperatorFactory<IBehaviourBasedFitness<TestCreature, TestBehaviour>>> behaveFitnesCals;
  IOperatorFactory<IBeahviourDescriptor<TestBehaviour, TestCreature>> descriptorFactory;
  private BehaviourParalellPool<TestCreature, TestBehaviour> testPool;

  public static class TestBehaviour {
    final int aaas;
    final int bbbs;

    public TestBehaviour(int aaas, int bbbs) {
      super();
      this.aaas = aaas;
      this.bbbs = bbbs;
    }

  }

  @Before
  public void setUp() throws Exception {
    setupOperators();

    testPool = new BehaviourParalellPool<TestCreature, TestBehaviour>(generators, mutators, breeders, behaveFitnesCals,
        descriptorFactory);
    underTest = testPool;

  }

  @Override
  protected void setupOperators() {
    super.setupOperators();
    behaveFitnesCals = new ArrayList<>();
    behaveFitnesCals.add(() -> new FitnesOne());
    behaveFitnesCals.add(() -> new SecondFitnes());
    descriptorFactory = () -> (cr -> new TestBehaviour((int) cr.bla.chars().filter(i -> i == 'a').count(),
        (int) cr.bla.chars().filter(i -> i == 'b').count()));



  }

  @Test
  public void storeIsSyncronsWithPopulation() {
    testPool.generate(0, 0, 100);
    Map<Integer, Double[]> w = testPool.calculateFitnessAndDeleteOldGeneration();
    assertTrue(w.size() == 100);
    assertTrue(testPool.store.getIdsAlive().size() == 100);

    ArrayList<int[]> crossOverids1 = new ArrayList<>();
    crossOverids1.add(new int[]{0, 2, 101, 102});
    crossOverids1.add(new int[]{10, 99, 103, 104});

    testPool.crossover(0, crossOverids1);
    w = testPool.calculateFitnessAndDeleteOldGeneration();
    assertTrue(w.size() == 4);
    assertTrue(testPool.store.getIdsAlive().size() == 4);
    assertEquals(testPool.store.getIdsAlive(), new HashSet<>(asList(101, 102, 103, 104)));

  }

  public static class FitnesOne implements IBehaviourBasedFitness<TestCreature, TestBehaviour> {

    private IBehaviourDescriponDataStore<TestBehaviour> store;

    @Override
    public void setStore(IBehaviourDescriponDataStore<TestBehaviour> store) {
      this.store = store;
    }

    @Override
    public double evaluate(Integer id) {
      return store.get(id).aaas;
    }

  }

  public static class SecondFitnes implements IBehaviourBasedFitness<TestCreature, TestBehaviour> {

    private IBehaviourDescriponDataStore<TestBehaviour> store;

    @Override
    public void setStore(IBehaviourDescriponDataStore<TestBehaviour> store) {
      this.store = store;
    }

    @Override
    public double evaluate(Integer id) {
      return store.get(id).bbbs;
    }

  }

}
