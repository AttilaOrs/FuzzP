package View;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxGraph;

import Controller.IGlobalController;
import Model.FuzzyPVizualModel;

public class GraphView implements IView {
  private static int BIG_SIZE = 40;
  private static int SMALL_SIZE = BIG_SIZE / 10 + 1;
  private static int FONT_SIZE = BIG_SIZE / 2 - SMALL_SIZE;

  private static final String TRANSITION_STYLE = "shape=rectangle;fillColor=#6C939F;strokeColor=#6C939F;fontSize="
      + FONT_SIZE + ";";
  private static final String TRANSITION_STYLE_OUT = "shape=rectangle;fillColor=#6C939F;strokeColor=#104050;strokeWidth=2;fontSize="
      + FONT_SIZE + ";";
  private static final String TRANSITION_STYLE_SELECTED = "shape=rectangle;fillColor=#104050;strokeColor=#104050;fontSize="
      + FONT_SIZE + ";";
  private static final String TRANSITION_STYLE_SELECTED_OUT = "shape=rectangle;fillColor=#104050;strokeColor=#6C939F;strokeWidth=2;fontSize="
      + FONT_SIZE + ";";
  private static final String PLACE_STYLE = "shape=ellipse;fillColor=#6C939F;strokeColor=#104050;fontColor=#104050;fontSize="
      + FONT_SIZE +";";
  private static final String PLACE_STYLE_INP = "shape=ellipse;fillColor=#6C939F;strokeWidth=3;strokeColor=#104050;fontColor=#104050;fontSize="
      + FONT_SIZE +";";
  private static final String PLACE_STYLE_SELECTED = "shape=ellipse;fillColor=#104050;strokeWidth=2;strokeColor=#6C939F;fontColor=#6C939F;fontSize="
      + FONT_SIZE + ";";
  private static final String PLACE_STYLE_SELECTED_INP = "shape=ellipse;fillColor=#104050;strokeWidth=4;strokeColor=#6C939F;fontColor=#6C939F;fontSize="
      + FONT_SIZE + ";";
  private static final String EDGE_STYLE = "fillColor=#6C939F;strokeColor=#6C939F;verticalAlign=top;";

  private static String TR_NR = "TRNR";
  private static String PL_NR = "PLNR";
  private static String TR_ID_PTTRN = "tr-TRNR";
  private static String PL_ID_PTTRN = "pl-PLNR";

  private static String EDGE_TR_TO_PL_PTTRN = "e-tr-TRNR-pl-PLNR";
  private static String EDGE_PL_TO_TR_PTTRN = "e-pl-PLNR-tr-TRNR";

  ArrayList<mxCell> transitionCells;
  ArrayList<mxCell> palceCells;
  ArrayList<mxCell> edgeCells;
  FuzzyPVizualModel vizualModel;
  private IGlobalController controller;

  private mxGraph graph;
  private mxGraphComponent graphComponent;

  public GraphView(FuzzyPVizualModel model) {
    this.vizualModel = model;
    initalizeCellHolders();
  }

  private void initalizeCellHolders() {
    transitionCells = new ArrayList<>();
    palceCells = new ArrayList<>();
    edgeCells = new ArrayList<>();
  }

  @Override
  public void reset() {
    graph.removeCells(transitionCells.toArray());
    graph.removeCells(palceCells.toArray());
    graph.removeCells(edgeCells.toArray());
    initalizeCellHolders();
    createMxGraph();
    graphComponent.refresh();
  }
  public mxGraphComponent createGraphComponent() {
    graph = new SpecificMxGraph();
    createMxGraph();
    graphComponent = new mxGraphComponent(graph);
    graphComponent.setDragEnabled(false);
    graphComponent.setEnabled(false);
    graphComponent.setWheelScrollingEnabled(true);
    graphComponent.getGraphHandler().setMoveEnabled(false);
    graphComponent.setToolTips(true);


    graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
        if (cell != null) {
          elementIsClicked(cell.getId(), e.getButton() == 1);
        }
      }

    });
    return graphComponent;
  }

  private void createMxGraph() {
    Object parent = graph.getDefaultParent();

    graph.getModel().beginUpdate();
    try {
      createTransitionsOnCanvas(parent);
      createPlacesOnCanvas(parent);
      createArcOnCanvas(parent);

      mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
      layout.setUseBoundingBox(false);
      layout.setInterHierarchySpacing(SMALL_SIZE * 3);
      layout.setInterRankCellSpacing(BIG_SIZE);
      layout.setIntraCellSpacing(SMALL_SIZE * 3);
      layout.setParallelEdgeSpacing(SMALL_SIZE * 3);

      layout.setOrientation(SwingConstants.WEST);
      layout.execute(graph.getDefaultParent());

      modifyEdgesNotToTouch();
      graph.setCellsSelectable(false);
      graph.setCellsEditable(false);

    } finally {
      graph.getModel().endUpdate();
    }

    graph.setAllowDanglingEdges(false);
  }



  private void createArcOnCanvas(Object parent) {
    vizualModel.getDrowableNet().getArcs().forEach(arc -> {
      if (arc.arcFromPlaceToTransition) {
        String id = EDGE_PL_TO_TR_PTTRN.replace(TR_NR, Integer.toString(arc.transitionId)).replace(PL_NR,
            Integer.toString(arc.placeId));
        mxCell edge = (mxCell) graph.insertEdge(parent, id, arc.label, palceCells.get(arc.placeId),
            transitionCells.get(arc.transitionId), EDGE_STYLE);
        edgeCells.add(edge);
      } else {
        String id = EDGE_TR_TO_PL_PTTRN.replace(TR_NR, Integer.toString(arc.transitionId)).replace(PL_NR,
            Integer.toString(arc.placeId));
        mxCell edge = (mxCell) graph.insertEdge(parent, id, arc.label, transitionCells.get(arc.transitionId),
            palceCells.get(arc.placeId),
            EDGE_STYLE);
        edgeCells.add(edge);
      }
    });

  }

private void createPlacesOnCanvas(Object parent) {
    for (int i = 0; i < vizualModel.getDrowableNet().getNrOfPlaces(); i++) {
        mxCell plCell = (mxCell) graph.insertVertex(parent, PL_ID_PTTRN.replace(PL_NR, Integer.toString(i)),
          vizualModel.getDrowableNet().getPlaceName(i), 0, 0, BIG_SIZE, BIG_SIZE, placeStyle(i));
        graph.getCellGeometry(plCell).setOffset(new mxPoint(new mxPoint(0, +BIG_SIZE / 10)));
        palceCells.add(plCell);
      }
}


private void createTransitionsOnCanvas(Object parent) {
    for (int i = 0; i < vizualModel.getDrowableNet().getNrOfTransition(); i++) {

        mxCell trCell = (mxCell) graph.insertVertex(parent, TR_ID_PTTRN.replace(TR_NR, Integer.toString(i)),
          vizualModel.getDrowableNet().getTransitionName(i), 0, 0, SMALL_SIZE, BIG_SIZE, transitionStyle(i) );
        graph.getCellGeometry(trCell).setOffset(new mxPoint(0, BIG_SIZE - SMALL_SIZE));
        transitionCells.add(trCell);
      }
}

  private void elementIsClicked(String id, boolean selectioOrDeselect) {
    if (cellIsPlace(id)) {
      publishPlaceSelection(id, selectioOrDeselect);
    }
    if (cellIsTransition(id)) {
      publishTransitionSelection(id, selectioOrDeselect);
    }
  }

  boolean cellIsPlace(String id) {
    String placeIdStrater = PL_ID_PTTRN.replace(PL_NR, "");
    return id.startsWith(placeIdStrater);
  }

  boolean cellIsTransition(String id) {
    String transitionStarter = TR_ID_PTTRN.replace(TR_NR, "");
    return id.startsWith(transitionStarter);
  }

  private void publishTransitionSelection(String id, boolean selectioOrDeselect) {
    int trId = extratTransitionId(id);
    if (selectioOrDeselect) {
      controller.transitionSelectionRequest(trId);
    } else {
      controller.transitionDeselectionRequest(trId);
    }

  }

  int extratTransitionId(String id) {
    String transitionStarter = TR_ID_PTTRN.replace(TR_NR, "");
    return Integer.parseInt(id.replace(transitionStarter, ""));
  }

  private void publishPlaceSelection(String id, boolean selectioOrDeselect) {
    int plnr = extractPlaceId(id);
    if (selectioOrDeselect) {
      controller.placeSelectionReqiest(plnr);
    } else {
      controller.placeDeselectionReqiest(plnr);
    }
  }

  int extractPlaceId(String id) {
    String placeIdStrater = PL_ID_PTTRN.replace(PL_NR, "");
    int plnr = Integer.parseInt(id.replace(placeIdStrater, ""));
    return plnr;
  }




  private void modifyEdgesNotToTouch() {
    for (int i = 0; i < edgeCells.size(); i++) {
      List<mxPoint> firstEdgePoints = edgeCells.get(i).getGeometry().getPoints();
      for (int j = i + 1; j < edgeCells.size(); j++) {
        List<mxPoint> secondEdgeProints = edgeCells.get(j).getGeometry().getPoints();
        if (firstEdgePoints != null && secondEdgeProints != null)
          for (int fiEdIndex = 0; fiEdIndex < firstEdgePoints.size(); fiEdIndex++) {
            for (int seEdIndex = 0; seEdIndex < secondEdgeProints.size(); seEdIndex++) {
              double xDist = firstEdgePoints.get(fiEdIndex).getX() - secondEdgeProints.get(seEdIndex).getX();
              xDist = xDist * xDist;
              double yDist = firstEdgePoints.get(fiEdIndex).getY() - secondEdgeProints.get(seEdIndex).getY();
              yDist = yDist * yDist;
              double dd = Math.sqrt(xDist + yDist);
              if (dd < SMALL_SIZE * 2) {
                moveToMidle(i, firstEdgePoints, fiEdIndex);
                moveToMidle(j, secondEdgeProints, seEdIndex);
              }
            }
          }
      }
    }
  }

  private void moveToMidle(int i, List<mxPoint> firstEdgePoints, int fiEdIndex) {
    mxPoint milde;
    if (fiEdIndex == 0) {
      return;
    } else if (fiEdIndex == firstEdgePoints.size() - 1) {
      return;
    } else {
      milde = calcMidle(firstEdgePoints.get(fiEdIndex - 1), firstEdgePoints.get(fiEdIndex + 1));
    }
    milde = calcMidle(firstEdgePoints.get(fiEdIndex), milde);
    firstEdgePoints.get(fiEdIndex).setX(milde.getX());
    firstEdgePoints.get(fiEdIndex).setY(milde.getY());
  }

  private mxPoint calcMidle(mxPoint firstPoint, mxPoint secondPoint) {
    return new mxPoint((firstPoint.getX() + secondPoint.getX()) / 2.0, (firstPoint.getY() + secondPoint.getY()) / 2.0);
  }


  @Override
  public void placeSelected(int plId) {
    palceCells.get(plId).setStyle(placeStyleSelection(plId));
    graphComponent.refresh();

  }

  @Override
  public void placeDeselect(int plId) {
    this.palceCells.get(plId).setStyle(placeStyle(plId));
    graphComponent.refresh();
  }


  @Override
  public void transitionSelected(int trId) {
    transitionCells.get(trId).setStyle(transitionStyleSelection(trId));
    graphComponent.refresh();
  }

  @Override
  public void transitionDeselected(int trId) {
    transitionCells.get(trId).setStyle(transitionStyle(trId));
    graphComponent.refresh();
  }

  @Override
  public void setController(IGlobalController controller) {
    this.controller = controller;
  }

  public void zoomIn() {
    graphComponent.zoom(1.05);
  }

  public void zoomOut() {
    graphComponent.zoom(0.95);
  }
  
private String placeStyle(int i) {
	return (vizualModel.getDrowableNet().isInputPlace(i))?PLACE_STYLE_INP : PLACE_STYLE;
}

private String placeStyleSelection(int i) {
	return (vizualModel.getDrowableNet().isInputPlace(i))?PLACE_STYLE_SELECTED_INP : PLACE_STYLE_SELECTED;
}

private String transitionStyle(int trId){
	return (vizualModel.getDrowableNet().isOuputTransition(trId))?TRANSITION_STYLE_OUT : TRANSITION_STYLE;
}

private String transitionStyleSelection(int trId){
	return (vizualModel.getDrowableNet().isOuputTransition(trId))?TRANSITION_STYLE_SELECTED_OUT : TRANSITION_STYLE_SELECTED;
}

  private class SpecificMxGraph extends mxGraph {

    @Override
    public
    String getToolTipForCell(Object obj) {
      if (obj instanceof mxCell) {
        String cellId = ((mxCell) obj).getId();
        if (cellIsPlace(cellId)) {
          return vizualModel.getDrowableNet().getPlaceFullText(extractPlaceId(cellId));
        }
        if (cellIsTransition(cellId)) {
          return vizualModel.getDrowableNet().getTransitionFullText(extratTransitionId(cellId));
        }

      }
      return super.getToolTipForCell(obj);
    }


  }

}
