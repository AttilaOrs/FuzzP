package FuzzyPLang.Visitor;

import FuzzyPLang.NetBuilder.FuzzyPLangNetBuilder;
import FuzzyPLang.gen.FuzzyPLangBaseVisitor;
import FuzzyPLang.gen.FuzzyPLangParser;
import core.FuzzyPetriLogic.FuzzyValue;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.OneXTwoTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXTwoTable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class Visitor extends FuzzyPLangBaseVisitor<Boolean> {

    FuzzyPLangNetBuilder builder;
    String prevNode;
    Double weight;

    public Visitor(FuzzyPLangNetBuilder builder){
        this.builder = builder;
    }

    @Override
    public Boolean visitPutInitToken(FuzzyPLangParser.PutInitTokenContext ctx) {
        visitChildren(ctx);
        String token = ctx.token().getText();
        builder.addInitialTokenInPlace(prevNode, token);
        prevNode = null;
        return true;
    }

    @Override
    public Boolean visitPetriLine(FuzzyPLangParser.PetriLineContext ctx) {
        prevNode =  null;
        weight = null;
        visitChildren(ctx);
        prevNode =  null;
        weight = null;
        return true;
    }


    @Override
    public Boolean visitInpPlace(FuzzyPLangParser.InpPlaceContext ctx) {
        String name = "iP"+ctx.INT();
        builder.addInpPlace(name);
        addArcIfPossible(name);
        return true;
    }

    @Override
    public Boolean visitPlace(FuzzyPLangParser.PlaceContext ctx) {
        String name = "P"+ctx.INT();
        builder.addPlace(name);
        addArcIfPossible(name);
        return true;
    }

    private void addArcIfPossible(String name) {
        if(prevNode!=null){
            if(weight != null){
                builder.addArc(prevNode, name, weight);
            } else {
                builder.addArc(prevNode, name);
            }
        }
        prevNode = name;
        weight = null;
    }

    @Override
    public Boolean visitTranz(FuzzyPLangParser.TranzContext ctx) {
        String transitionName = "T"+ctx.INT();
        builder.addTransition(transitionName);
        addArcIfPossible(transitionName);
        return visitChildren(ctx);
    }

    @Override
    public Boolean visitOtranz(FuzzyPLangParser.OtranzContext ctx) {
        String transitionName = "oT"+ctx.INT();
        builder.addOutTransition(transitionName);
        addArcIfPossible(transitionName);
        return visitChildren(ctx);
    }

    @Override
    public Boolean visitTranzSpec(FuzzyPLangParser.TranzSpecContext ctx) {
        if(ctx.ID() != null ){
            builder.setNamedTableForTransition(prevNode, ctx.ID().getText());
        }
        if(ctx.INT() != null){
            builder.setDelayForTransition(prevNode, Integer.parseInt(ctx.INT().getText()));
        }
        return true;
    }

    @Override
    public Boolean visitArcOp(FuzzyPLangParser.ArcOpContext ctx) {
        if(ctx.number()!=null){
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
        builder.addTableWithName(ctx.ID().getText(), new OneXOneTable(firstMapList.get(0)));
        firstMapList.clear();
        return  true;
    }

    @Override
    public Boolean visitOneXTwoTable(FuzzyPLangParser.OneXTwoTableContext ctx) {
        visitChildren(ctx);
        builder.addTableWithName(ctx.ID().getText(), new OneXTwoTable(firstMapList.get(0), secondMapList.get(0)));
        firstMapList.clear();
        secondMapList.clear();
        return  true;
    }

    @Override
    public Boolean visitTwoXOneWithoutPhi(FuzzyPLangParser.TwoXOneWithoutPhiContext ctx) {
        visitChildren(ctx);
        EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> table = new EnumMap<>(FuzzyValue.class);
        int cntr = 0;
        for(FuzzyValue index  : FuzzyValue.inOrderWithoutPhi){
            table.put(index, firstMapList.get(cntr++));
        }
        builder.addTableWithName(ctx.ID().getText(), new TwoXOneTable(table));
        firstMapList.clear();
        return true;
    }
    @Override
    public Boolean visitTwoXOneWithPhi(FuzzyPLangParser.TwoXOneWithPhiContext ctx) {
        visitChildren(ctx);
        EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> table = new EnumMap<>(FuzzyValue.class);
        int cntr = 0;
        for(FuzzyValue index  : FuzzyValue.inOrder){
            table.put(index, firstMapList.get(cntr++));
        }
        builder.addTableWithName(ctx.ID().getText(), new TwoXOneTable(table));
        firstMapList.clear();
        return true;
    }
    @Override
    public Boolean visitTwoXTwoWithoutPhi(FuzzyPLangParser.TwoXTwoWithoutPhiContext ctx) {
        visitChildren(ctx);
        EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> firstTable = new EnumMap<>(FuzzyValue.class);
        EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> secondTable = new EnumMap<>(FuzzyValue.class);
        int cntr = 0;
        for(FuzzyValue index  : FuzzyValue.inOrderWithoutPhi){
            firstTable.put(index, firstMapList.get(cntr));
            secondTable.put(index, secondMapList.get(cntr++));
        }
        builder.addTableWithName(ctx.ID().getText(), new TwoXTwoTable(firstTable, secondTable));
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
        for(FuzzyValue index  : FuzzyValue.inOrder){
            firstTable.put(index, firstMapList.get(cntr));
            secondTable.put(index, secondMapList.get(cntr++));
        }
        firstMapList.clear();
        secondMapList.clear();
        return true;
    }

    @Override
    public Boolean visitSimpleCellLine(FuzzyPLangParser.SimpleCellLineContext ctx) {
        visitChildren(ctx);
        Map<FuzzyValue, FuzzyValue> mapToPut = new EnumMap<>(FuzzyValue.class);
        for(int i = 0; i < firstLine.size(); i++){
            mapToPut.put(FuzzyValue.inOrder.get(i), firstLine.get(i));
        }
       firstMapList.add(mapToPut) ;
       firstLine.clear();
       return  true;
    }
    @Override
    public Boolean visitDoubleCellLine(FuzzyPLangParser.DoubleCellLineContext ctx) {
        visitChildren(ctx);
        Map<FuzzyValue, FuzzyValue> firstMapToPut = new EnumMap<>(FuzzyValue.class);
        Map<FuzzyValue, FuzzyValue> secondMapTpPut = new EnumMap<>(FuzzyValue.class);
        for(int i = 0; i < firstLine.size(); i++){
            firstMapToPut.put(FuzzyValue.inOrder.get(i), firstLine.get(i));
            secondMapTpPut.put(FuzzyValue.inOrder.get(i), secondLine.get(i));
        }
        firstMapList.add(firstMapToPut) ;
        secondMapList.add(secondMapTpPut) ;
        firstLine.clear();
        secondLine.clear();
        return  true;
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
