package UnifiedPetriGA.cluster;

import org.ejml.data.DMatrixRMaj;
import org.junit.Test;

public class MarkovClusteringTest {

  @Test
  public void test() {
    DistaneMetricCollector collect = new DistaneMetricCollector();
    for (int i = 0; i < 10; i++) {
      collect.put(0, 1);
      collect.put(1, 2);
      collect.put(2, 0);

      collect.put(3, 4);
      collect.put(4, 5);
      collect.put(5, 3);

    }
    for (int i = 0; i < 3; i++) {
      collect.put(2, 5);
      collect.put(0, 6);
      collect.put(7, 4);
      collect.put(5, 7);
      collect.put(7, 2);
    }

    DMatrixRMaj createGraphMatrix = collect.createGraphMatrix(0, 8);
    // System.out.println(createGraphMatrix);
    MarkovClustering clsut = new MarkovClustering(createGraphMatrix);
    // System.out.println(clsut.run());


  }

}
