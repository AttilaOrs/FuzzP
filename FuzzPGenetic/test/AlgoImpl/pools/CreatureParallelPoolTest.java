package AlgoImpl.pools;

import org.junit.Before;

public class CreatureParallelPoolTest extends CreaturePoolWithStreamsTest {

  @Before
  public void setUp() throws Exception {
    setupOperators();

    underTest = new CreatureParallelPool<>(generators, mutators, breeders, fitnesCals);
  }

}
