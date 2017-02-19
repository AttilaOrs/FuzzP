package main;

import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import exampleNets.ConcurentPetriNetBuilder;
import exampleNets.SelectionLikeTwoBranchExample;
import exampleNets.SimpleDelayPetriNetBuilder;
import exampleNets.TwoLoopNet;

public class Main {
  public static void main(String[] args) {
    SimpleDelayPetriNetBuilder bld = new SimpleDelayPetriNetBuilder();
    PetriNetJsonSaver.saveFuzzyPetriNetToJsonFile(bld.getNet(), "SimpleDelayPetriNet.json");
    ConcurentPetriNetBuilder bld2 = new ConcurentPetriNetBuilder();
    PetriNetJsonSaver.saveFuzzyPetriNetToJsonFile(bld2.getNet(), "ConcurentPetriNet.json");
    SelectionLikeTwoBranchExample bld3 = new SelectionLikeTwoBranchExample();
    PetriNetJsonSaver.saveFuzzyPetriNetToJsonFile(bld3.getNet(), "SelectionLikeTwoBranchExamplePetriNet.json");
    TwoLoopNet bld4 = new TwoLoopNet();
    PetriNetJsonSaver.saveFuzzyPetriNetToJsonFile(bld4.getNet(), "TwoLoopPetriNet.json");


  }

}
