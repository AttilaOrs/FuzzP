package AlgoImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IterationLoggerTest {

  private IterationLogger underTest;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setUp() throws Exception {
    underTest = new IterationLogger();
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void after() {
    System.setOut(null);
  }

  @Test
  public void iter_finished_prinln() {
    underTest.addLogToTopic(IterationLogger.AVG_FIT, 0.55);
    underTest.addLogToTopic(IterationLogger.MAX_FIT, 1.11);
    underTest.addLogToTopic(IterationLogger.POP_SIZE, 100.0);
    underTest.addLogToTopic(IterationLogger.MEM_USE, 123.4);
    underTest.addLogToTopic(IterationLogger.GC_SEC, 432.1);

    underTest.iterFinished(0);

    String out = "IterNR: 0 maximum of Fitnes 1.11 avarage of Fitnes 0.55 population size 100.00";
    Assert.assertEquals(out, outContent.toString().trim());
  }

  @Test
  public void getLogsForPlot() {
    underTest.addLogToTopic(IterationLogger.MEM_USE, 1.11);
    underTest.addLogToTopic(IterationLogger.GC_SEC, 2.22);
    underTest.iterFinished(0);
    underTest.addLogToTopic(IterationLogger.MEM_USE, 3.33);
    underTest.addLogToTopic(IterationLogger.GC_SEC, 4.44);
    underTest.iterFinished(1);

    Map<String, List<Double>> res = underTest.getLogsForPlotting(IterationLogger.MEM_USE, IterationLogger.GC_SEC);

    Assert.assertEquals(Arrays.asList(1.11, 3.33), res.get(IterationLogger.MEM_USE));
    Assert.assertEquals(Arrays.asList(2.22, 4.44), res.get(IterationLogger.GC_SEC));

  }

}
