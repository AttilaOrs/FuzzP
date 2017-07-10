package AlgoImpl.pools;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import AlgoImpl.pools.CreaturePoolWithStreams.OpManager;
import structure.GPIndividSize;
import structure.ICreatureFitnes;
import structure.ICreaturePool;
import structure.IGPGreature;
import structure.IOperatorFactory;
import structure.operators.ICreatureBreeder;
import structure.operators.ICreatureGenerator;
import structure.operators.ICreatureMutator;

public class CreaturePoolWithStreamsTest {
  ICreaturePool<TestCreature> underTest;

  protected ArrayList<IOperatorFactory<ICreatureGenerator<TestCreature>>> generators;
  protected ArrayList<IOperatorFactory<ICreatureMutator<TestCreature>>> mutators;
  protected ArrayList<IOperatorFactory<ICreatureBreeder<TestCreature>>> breeders;
  protected ArrayList<IOperatorFactory<ICreatureFitnes<TestCreature>>> fitnesCals;

  @Before
  public void setUp() throws Exception {
    setupOperators();

    underTest = new CreaturePoolWithStreams<>(generators, mutators, breeders, fitnesCals);
  }

  protected void setupOperators() {
    generators = new ArrayList<>();
    mutators = new ArrayList<>();
    breeders = new ArrayList<>();
    fitnesCals = new ArrayList<>();

    generators.add(() -> this::oneGenerator);
    generators.add(() -> this::twoGenerator);
    mutators.add(() -> this::mutator);
    mutators.add(() -> this::mutator2);
    breeders.add(() -> this::crossOver);
    breeders.add(() -> this::crossOver2);
    fitnesCals.add(() -> this::fitnes);
    fitnesCals.add(() -> this::fitnes2);
  }

  @Test
  public void generate_test() {
    underTest.generate(0, 0, 2);
    underTest.generate(1, 2, 2);

    Map<Integer, Double[]> res = underTest.calculateFitness();

    Assert.assertArrayEquals(res.get(0), new Double[] { 6.0, 0.0 });
    Assert.assertArrayEquals(res.get(1), new Double[] { 6.0, 0.0 });
    Assert.assertArrayEquals(res.get(2), new Double[] { 0.0, 6.0 });
    Assert.assertArrayEquals(res.get(3), new Double[] { 0.0, 6.0 });

  }

  @Test
  public void crossover_test() {
    underTest.generate(0, 0, 2);
    underTest.generate(1, 2, 2);
    Map<Integer, Double[]> res = underTest.calculateFitness();

    ArrayList<int[]> crossOverids1 = new ArrayList<>();
    crossOverids1.add(new int[] { 0, 2, 5, 6 });
    crossOverids1.add(new int[] { 3, 1, 9, 21 });
    underTest.crossover(0, crossOverids1);
    ArrayList<int[]> crossOverids2 = new ArrayList<>();
    crossOverids2.add(new int[] { 0, 2, 7, 8 });
    crossOverids2.add(new int[] { 3, 1, 10, 22 });
    underTest.crossover(1, crossOverids2);

    res = underTest.calculateFitness();
    Assert.assertArrayEquals(res.get(5), new Double[] { 3.0, 3.0 });
    Assert.assertArrayEquals(res.get(6), new Double[] { 3.0, 3.0 });
    Assert.assertArrayEquals(res.get(9), new Double[] { 3.0, 3.0 });
    Assert.assertArrayEquals(res.get(21), new Double[] { 3.0, 3.0 });

    Assert.assertArrayEquals(res.get(7), new Double[] { 4.0, 2.0 });
    Assert.assertArrayEquals(res.get(8), new Double[] { 2.0, 4.0 });
    Assert.assertArrayEquals(res.get(10), new Double[] { 2.0, 4.0 });
    Assert.assertArrayEquals(res.get(22), new Double[] { 4.0, 2.0 });

  }

  @Test
  public void mutation_test() {
    underTest.generate(0, 0, 2);
    underTest.generate(1, 2, 2);
    Map<Integer, Double[]> res = underTest.calculateFitness();

    ArrayList<int[]> mutationIds = new ArrayList<>();
    mutationIds.add(new int[] { 0, 7 });
    mutationIds.add(new int[] { 2, 10 });
    underTest.mutate(0, mutationIds);
    ArrayList<int[]> mutationIds2 = new ArrayList<>();
    mutationIds2.add(new int[] { 1, 8 });
    mutationIds2.add(new int[] { 3, 11 });
    underTest.mutate(1, mutationIds2);
    res = underTest.calculateFitness();

    Assert.assertArrayEquals(res.get(7), new Double[] { 5.0, 1.0 });
    Assert.assertArrayEquals(res.get(10), new Double[] { 0.0, 6.0 });
    Assert.assertArrayEquals(res.get(8), new Double[] { 6.0, 0.0 });
    Assert.assertArrayEquals(res.get(11), new Double[] { 1.0, 5.0 });

  }

  public void noTest() {
    underTest.generate(0, 0, 100000000);
    Map<Integer, Double[]> res = underTest.calculateFitness();

  }

  int cntr = 0;

  public TestCreature factoryForOpMAnager() {
    return new TestCreature(Integer.toString(cntr++));

  }

  @Test
  public void managerTest() {
    CreaturePoolWithStreams.OpManager<TestCreature> tt = new OpManager<>(this::factoryForOpMAnager);
    TestCreature one = tt.getOperator();
    TestCreature two = tt.getOperator();
    TestCreature three = tt.getOperator();
    TestCreature four = tt.getOperator();
    Assert.assertEquals(one.bla, "0");
    Assert.assertEquals(two.bla, "1");
    Assert.assertEquals(three.bla, "2");
    Assert.assertEquals(four.bla, "3");
    tt.allOpsAreFree();
    TestCreature one2 = tt.getOperator();
    TestCreature two2 = tt.getOperator();
    TestCreature three2 = tt.getOperator();
    TestCreature four2 = tt.getOperator();
    Assert.assertEquals(one2.bla, "0");
    Assert.assertEquals(two2.bla, "1");
    Assert.assertEquals(three2.bla, "2");
    Assert.assertEquals(four2.bla, "3");

  }

  public static class TestCreature implements IGPGreature {
    public String bla;

    public TestCreature(String bla) {
      this.bla = bla;
    }

    @Override
    public GPIndividSize getSizes() {
      return null;
    }

    @Override
    public IGPGreature myClone() {
      return new TestCreature(bla);
    }
  }

  public TestCreature oneGenerator(Random rr) {
    return new TestCreature("aaaaaa");
  }

  public TestCreature twoGenerator(Random rr) {
    return new TestCreature("bbbbbb");
  }

  public TestCreature[] crossOver(TestCreature tt, TestCreature tt2, Random rnd) {
    String ff = tt.bla.substring(0, 3) + tt2.bla.substring(3, 6);
    String ff2 = tt2.bla.substring(0, 3) + tt.bla.substring(3, 6);
    return new TestCreature[] { new TestCreature(ff), new TestCreature(ff2) };
  }

  public TestCreature[] crossOver2(TestCreature tt, TestCreature tt2, Random rnd) {
    String ff = tt.bla.substring(0, 4) + tt2.bla.substring(4, 6);
    String ff2 = tt2.bla.substring(0, 4) + tt.bla.substring(4, 6);
    return new TestCreature[] { new TestCreature(ff), new TestCreature(ff2) };
  }

  public TestCreature mutator(TestCreature tt, Random rnd) {
    char[] ff = tt.bla.toCharArray();
    ff[1] = 'b';
    return new TestCreature(new String(ff));
  }

  public TestCreature mutator2(TestCreature tt, Random rnd) {
    char[] ff = tt.bla.toCharArray();
    ff[4] = 'a';
    return new TestCreature(new String(ff));
  }

  public double fitnes(TestCreature ff) {
    long aas = ff.bla.chars().filter(i -> i == 'a').count();
    return aas;
  }

  public double fitnes2(TestCreature ff) {
    long bbs = ff.bla.chars().filter(i -> i == 'b').count();
    return bbs;
  }

}
