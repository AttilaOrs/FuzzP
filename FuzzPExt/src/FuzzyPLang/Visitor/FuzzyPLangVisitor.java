package FuzzyPLang.Visitor;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.TerminalNode;

import FuzzyPLang.NetBuilder.DynamicScope;
import FuzzyPLang.NetBuilder.HiearchicalIntermediateFuzzyNet;
import FuzzyPLang.NetBuilder.NodeRef;
import FuzzyPLang.NetBuilder.StaticScope;
import FuzzyPLang.gen.FuzzyPLangBaseVisitor;
import FuzzyPLang.gen.FuzzyPLangParser;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

public class FuzzyPLangVisitor extends FuzzyPLangBaseVisitor<Boolean> {

  HiearchicalIntermediateFuzzyNet intermediateNet;
  NodeRef prevNode;
  Double weight;
  StaticScope subState;
  DynamicScope curentDynScope;

  public FuzzyPLangVisitor() {
    subState = new StaticScope();
    intermediateNet = new HiearchicalIntermediateFuzzyNet();
    curentDynScope = new DynamicScope();
  }

  public HiearchicalIntermediateFuzzyNet getIntermeddiateNet() {
    return intermediateNet;
  }

  @Override
  public Boolean visitSubCompDcl(FuzzyPLangParser.SubCompDclContext ctx) {
    String subName = ctx.ID().getText();
    intermediateNet.makeDeclaration(subState.cloneSubState(), subName);
    subState.addSub(subName);
    visitChildren(ctx);
    subState.removeLastSub();
    return true;
  }

  @Override
  public Boolean visitNewSubComp(FuzzyPLangParser.NewSubCompContext ctx) {
    String varName = ctx.ID(0).getText();
    String subName = ctx.ID(1).getText();
    intermediateNet.makeInstenciation(subState.cloneSubState(), varName, subName);
    return true;
  }

  @Override
  public Boolean visitSubCompRef(FuzzyPLangParser.SubCompRefContext ctx) {
    DynamicScope ll = new DynamicScope();
    for (TerminalNode v : ctx.ID()) {
      ll.addSub(v.getText());
    }
    curentDynScope = ll;
    visitChildren(ctx);
    curentDynScope = new DynamicScope();

    return true;
  }

  @Override
  public Boolean visitPutInitToken(FuzzyPLangParser.PutInitTokenContext ctx) {
    visitChildren(ctx);
    String token = ctx.token().getText();
    intermediateNet.addInitialTokenInPlace(subState.cloneSubState(), prevNode.getNodeName(), token);
    prevNode = null;
    return true;
  }

  @Override
  public Boolean visitPetriLine(FuzzyPLangParser.PetriLineContext ctx) {
    prevNode = null;
    weight = null;
    visitChildren(ctx);
    prevNode = null;
    weight = null;
    return true;
  }

  @Override
  public Boolean visitInpPlace(FuzzyPLangParser.InpPlaceContext ctx) {
    String name = "iP" + ctx.INT();
    if (curentDynScope.current()) {
      intermediateNet.addInpPlace(subState.cloneSubState(), name);
    }
    addArcIfPossible(name);
    return true;
  }

  @Override
  public Boolean visitPlace(FuzzyPLangParser.PlaceContext ctx) {
    String name = "P" + ctx.INT();
    if (curentDynScope.current()) {
      intermediateNet.addPlace(subState.cloneSubState(), name);
    }
    addArcIfPossible(name);
    return true;
  }

  private void addArcIfPossible(String name) {
    NodeRef ref = new NodeRef(name, curentDynScope);
    if (prevNode != null) {
      if (weight != null) {
        intermediateNet.addArc(subState.cloneSubState(), prevNode, ref, weight);
      } else {
        intermediateNet.addArc(subState.cloneSubState(), prevNode, ref);
      }
    }
    prevNode = ref;
    weight = null;
  }

  @Override
  public Boolean visitTranz(FuzzyPLangParser.TranzContext ctx) {
    String transitionName = "T" + ctx.INT();
    if (curentDynScope.current()) {
      intermediateNet.addTransition(subState.cloneSubState(), transitionName);
    }
    addArcIfPossible(transitionName);
    return visitChildren(ctx);
  }

  @Override
  public Boolean visitOtranz(FuzzyPLangParser.OtranzContext ctx) {
    String transitionName = "oT" + ctx.INT();

    if (curentDynScope.current()) {
      intermediateNet.addOutTransition(subState.cloneSubState(), transitionName);
    }

    addArcIfPossible(transitionName);
    return visitChildren(ctx);
  }

  @Override
  public Boolean visitTranzSpec(FuzzyPLangParser.TranzSpecContext ctx) {
    if (ctx.ID() != null) {
      intermediateNet.setNamedTableForTransition(subState.cloneSubState(), prevNode.getNodeName(),
          ctx.ID().getText());
    }
    if (ctx.INT() != null) {
      intermediateNet.setDelayForTransition(subState.cloneSubState(), prevNode.getNodeName(),
          Integer.parseInt(ctx.INT().getText()));
    }
    return true;
  }

  @Override
  public Boolean visitArcOp(FuzzyPLangParser.ArcOpContext ctx) {
    if (ctx.number() != null) {
      weight = Double.parseDouble(ctx.number().getText());
    }
    return true;
  }

  ArrayList<Map<FuzzyValue, FuzzyValue>> firstMapList = new ArrayList<>();
  ArrayList<Map<FuzzyValue, FuzzyValue>> secondMapList = new ArrayList<>();
  ArrayList<FuzzyValue> firstLine = new ArrayList<>();
  ArrayList<FuzzyValue> secondLine = new ArrayList<>();

  @Override
  public Boolean visitOneXOneTable(FuzzyPLangParser.OneXOneTableContext ctx) {
    visitChildren(ctx);
    intermediateNet.addTableWithName(subState.cloneSubState(), ctx.ID().getText(),
        new OneXOneTable(firstMapList.get(0)));
    firstMapList.clear();
    return true;
  }

  @Override
  public Boolean visitOneXTwoTable(FuzzyPLangParser.OneXTwoTableContext ctx) {
    visitChildren(ctx);
    intermediateNet.addTableWithName(subState.cloneSubState(), ctx.ID().getText(),
        new OneXTwoTable(firstMapList.get(0), secondMapList.get(0)));
    firstMapList.clear();
    secondMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXOneWithoutPhi(FuzzyPLangParser.TwoXOneWithoutPhiContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> table = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrderWithoutPhi) {
      table.put(index, firstMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(subState.cloneSubState(), ctx.ID().getText(), new TwoXOneTable(table));
    firstMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXOneWithPhi(FuzzyPLangParser.TwoXOneWithPhiContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> table = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrder) {
      table.put(index, firstMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(subState.cloneSubState(), ctx.ID().getText(), new TwoXOneTable(table));
    firstMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXTwoWithoutPhi(FuzzyPLangParser.TwoXTwoWithoutPhiContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> firstTable = new EnumMap<>(FuzzyValue.class);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> secondTable = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrderWithoutPhi) {
      firstTable.put(index, firstMapList.get(cntr));
      secondTable.put(index, secondMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(subState.cloneSubState(), ctx.ID().getText(),
        new TwoXTwoTable(firstTable, secondTable));
    firstMapList.clear();
    secondMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXTwoWithPhi(FuzzyPLangParser.TwoXTwoWithPhiContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> firstTable = new EnumMap<>(FuzzyValue.class);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> secondTable = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrder) {
      firstTable.put(index, firstMapList.get(cntr));
      secondTable.put(index, secondMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(subState.cloneSubState(), ctx.ID().getText(),
        new TwoXTwoTable(firstTable, secondTable));
    firstMapList.clear();
    secondMapList.clear();
    return true;
  }

  @Override
  public Boolean visitSimpleCellLine(FuzzyPLangParser.SimpleCellLineContext ctx) {
    visitChildren(ctx);
    Map<FuzzyValue, FuzzyValue> mapToPut = new EnumMap<>(FuzzyValue.class);
    for (int i = 0; i < firstLine.size(); i++) {
      mapToPut.put(FuzzyValue.inOrder.get(i), firstLine.get(i));
    }
    firstMapList.add(mapToPut);
    firstLine.clear();
    return true;
  }

  @Override
  public Boolean visitDoubleCellLine(FuzzyPLangParser.DoubleCellLineContext ctx) {
    visitChildren(ctx);
    Map<FuzzyValue, FuzzyValue> firstMapToPut = new EnumMap<>(FuzzyValue.class);
    Map<FuzzyValue, FuzzyValue> secondMapTpPut = new EnumMap<>(FuzzyValue.class);
    for (int i = 0; i < firstLine.size(); i++) {
      firstMapToPut.put(FuzzyValue.inOrder.get(i), firstLine.get(i));
      secondMapTpPut.put(FuzzyValue.inOrder.get(i), secondLine.get(i));
    }
    firstMapList.add(firstMapToPut);
    secondMapList.add(secondMapTpPut);
    firstLine.clear();
    secondLine.clear();
    return true;
  }

  @Override
  public Boolean visitSimpleCell(FuzzyPLangParser.SimpleCellContext ctx) {
    FuzzyValue fv = FuzzyValue.fromString(ctx.fv().type.getText());
    firstLine.add(fv);
    return true;
  }

  @Override
  public Boolean visitDoubleCell(FuzzyPLangParser.DoubleCellContext ctx) {
    FuzzyValue fv1 = FuzzyValue.fromString(ctx.fv(0).type.getText());
    FuzzyValue fv2 = FuzzyValue.fromString(ctx.fv(1).type.getText());
    firstLine.add(fv1);
    secondLine.add(fv2);
    return true;
  }

}
