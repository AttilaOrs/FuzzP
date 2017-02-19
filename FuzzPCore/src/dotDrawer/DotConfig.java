package dotDrawer;

public class DotConfig {

  final private String allStarter = "digraph G{";
  final private String allEnder = "}";
  final private String placeStarter = "subgraph palce { \n graph [shape=circle,color=gray];node [shape=circle,fixedsize=true,width=0.4];";
  final private String placeEnder = "}";
  final private String tranStarter = "subgraph transitions{\n node [shape=rect,height=0.2,width=1.0];";
  final private String transEnder = "}";
  final private String limit = ";";
  final private String arc = "->";

  public String getAllStarter() {
    return allStarter;
  }

  public String getAllEnder() {
    return allEnder;
  }

  public String getPlaceStarter() {
    return placeStarter;
  }

  public String getPlaceEnder() {
    return placeEnder;
  }

  public String getTranStarter() {
    return tranStarter;
  }

  public String getTransEnder() {
    return transEnder;
  }

  public String getLimit() {
    return limit;
  }

  public String getArc() {
    return arc;
  }

}
