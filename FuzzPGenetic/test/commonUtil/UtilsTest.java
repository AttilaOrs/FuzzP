package commonUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void test() {
    String[] res = Utils.splitToChildren("cft[123] (kacsa1 kacsa2)");
    Assert.assertEquals(res[0], "cft[123]");
    Assert.assertEquals(res[1], "(kacsa1 kacsa2)");
  }

  @Test
  public void test_withspace() {
    String[] res = Utils.splitToChildren("cft[123, cd] (kacsa1 kacsa2)");
    Assert.assertEquals(res[0], "cft[123,cd]");
    Assert.assertEquals(res[1], "(kacsa1 kacsa2)");
    res = Utils.splitToChildren("cft[123, {cd, mm}] (kacsa1 kacsa2)");
    Assert.assertEquals(res[0], "cft[123,{cd,mm}]");
    Assert.assertEquals(res[1], "(kacsa1 kacsa2)");
    res = Utils.splitToChildren("cft[123, {cd, mm}] tenger");
    Assert.assertEquals(res[0], "cft[123,{cd,mm}]");
    Assert.assertEquals(res[1], "tenger");

    res = Utils.splitToChildren("(kacsa ) tenger");

    Assert.assertEquals(res[0], "(kacsa )");
    Assert.assertEquals(res[1], "tenger");

  }
}
