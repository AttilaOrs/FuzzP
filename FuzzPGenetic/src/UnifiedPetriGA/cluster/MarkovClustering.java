package UnifiedPetriGA.cluster;

import static org.ejml.dense.row.CommonOps_DDRM.elementSum;
import static org.ejml.dense.row.CommonOps_DDRM.mult;

import java.util.HashSet;
import java.util.Set;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
public class MarkovClustering {

  private final DMatrixRMaj base;

  public MarkovClustering(DMatrixRMaj base) {
    this.base = base;
  }

  public DMatrixRMaj run() {
    double residual = 1.0;
    DMatrixRMaj old = base;
    while (residual > 0.001) {
      DMatrixRMaj expaneded = new DMatrixRMaj(base.numCols, base.numRows);
      mult(old, old, expaneded);
      // System.out.println(expaneded);
      residual = inflate(expaneded);
      old = expaneded;
      System.out.println("pukk" + residual);

    }
    return old;
  }

  public Set<Integer> attractors(DMatrixRMaj w) {
    Set<Integer> toRet = new HashSet<>();
    for(Integer i = 0; i <w.numCols;i++) {
      DMatrixRMaj row = CommonOps_DDRM.extractRow(w, i, null);
      if (elementSum(row) > 0.0) {
        toRet.add(i);

      }
    }
    return toRet;
  }

  private double inflate(DMatrixRMaj expaneded) {
    double overallMaxDiff = 0;
    for (int i = 0; i < expaneded.numCols; i++) {
      double sum = 0.0;
      double sumNormalized = 0.0;
      double maxNormalized = 0.0;
      int nonZero = 0;
      for (int j = 0; j < expaneded.numRows; j++) {
        double v = expaneded.get(j, i);
        v = Math.pow(v, 1.75);
        v = (v < 0.000001) ? 0.0 : v;
        expaneded.set(j, i, v);

        sum += v;
      }
      if (sum != 0.0) {
        for (int j = 0; j < expaneded.numRows; j++) {
          double v = expaneded.get(j, i);
          v /= sum;
          expaneded.set(j, i, v);
          sumNormalized += v;
          maxNormalized = (maxNormalized < v) ? v : maxNormalized;
          nonZero += (v != 0.0) ? 1 : 0;
        }
        double w = maxNormalized - (sumNormalized / nonZero);
        overallMaxDiff = (overallMaxDiff < w) ? w : overallMaxDiff;
      }
    }

    return overallMaxDiff;
  }

}
