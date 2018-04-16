package UnifiedGpProblmes.ArtificalAnt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MutableStateLogged extends MutableState {

  int[][] way;
  int cntr;

  public MutableStateLogged(boolean[][] grid) {
    super(grid);
    way = new int[GridReader.getGridLength()][];
    for (int i = 0; i < GridReader.getGridLength(); i++) {
      way[i] = new int[GridReader.getGridLength()];
    }
    cntr = 1;
  }

  @Override
  void left() {
    super.left();
    log();
  }

  @Override
  void right() {
    super.right();
    log();
  }

  @Override
  void forward() {
    super.forward();
    log();
  }

  private void log() {
    int[] i = getCurrentPosition();
    way[i[0]][i[1]] = cntr;
    cntr++;
  }

  public void writeToFileWithXs(File file) {
    StringBuilder bld = new StringBuilder();
    for (int i = 0; i < way.length; i++) {
      for (int j = 0; j < way.length; j++) {
        if (way[i][j] == 0) {
          bld.append("-");
        } else {
          bld.append("X");
        }
      }
      bld.append("\n");
    }

    try {
      BufferedWriter output = new BufferedWriter(new FileWriter(file));
      output.write(bld.toString());
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
