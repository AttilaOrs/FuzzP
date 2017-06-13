package FuzzyGp.Tree.Visitors;

import java.util.ArrayList;
import java.util.HashMap;

import FuzzyGp.Tree.INode;

public class NodeToTransitionMapping {

  HashMap<INode, int[]> mapping;
  ArrayList<INode> emptyMappings;

  public NodeToTransitionMapping() {
    mapping = new HashMap<>();
    emptyMappings = new ArrayList<>();
  }

  public void addIdsToNode(INode node, int[] ids) {
    mapping.put(node, ids);
  }

  public void addEmptyMapping(INode node) {
    emptyMappings.add(node);
  }

  public int[] getMyTransitions(INode node) {
    if (mapping.containsKey(node)) {
      return mapping.get(node);
    }
    if (emptyMappings.contains(node)) {
      return null;
    }
    throw new RuntimeException("node does not exist " + node);

  }

}
