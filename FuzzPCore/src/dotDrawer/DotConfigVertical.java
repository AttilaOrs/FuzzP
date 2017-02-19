package dotDrawer;

public class DotConfigVertical extends DotConfig {

  final private String allStarter = "digraph G{ \n rankdir=LR; \n";

  final private String placeStarter = "subgraph palce { \n graph [shape=circle,color=gray];node [shape=circle,fixedsize=true,width=0.7];";
  final private String tranStarter = "subgraph transitions{ \n node [style=filled fillcolor=black shape=rect height=0.6 width=0.05 ];";

  @Override
  public String getAllStarter() {
    return allStarter;
  }

  @Override
  public String getPlaceStarter() {
    return placeStarter;
  }

  @Override
  public String getTranStarter() {
    return tranStarter;
  }
}
