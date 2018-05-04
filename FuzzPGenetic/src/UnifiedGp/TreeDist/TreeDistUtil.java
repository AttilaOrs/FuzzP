package UnifiedGp.TreeDist;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import com.google.common.base.Objects;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.TreeTraverser;

import UnifiedGp.Tree.DepthFirstPostOrderVisitor;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.VisitorCostumizer;
import UnifiedGp.Tree.Nodes.NodeType;
import structure.TriFunction;

public class TreeDistUtil {

  public static <T> String treeToString(TreeDistNode<T> node) {
    if (node.getChildren().isEmpty()) {
      return node.getData().toString();
    } else {
      String result = node.getData().toString() + "(";
      for (TreeDistNode<T> child : node.getChildren()) {
        result += treeToString(child) + " ";
      }
      return result.substring(0, result.length() - 1) + ")";
    }
  }

  public static void treeFromString(TreeDistNode<String> node, String text) {
    final int idx = text.indexOf("(");
    if (idx == -1) {
      node.setData(text);
    } else {
      node.setData(text.substring(0, idx));
      for(String subString : split(text.substring(idx + 1, text.lastIndexOf(")")))) {
        final TreeDistNode<String> n = new SimpleTreeDistNode<>("");
        node.addChild(n);
        treeFromString(n, subString);
      }
    }
  }

  public static <T> BiMap<TreeDistNode<T>, Integer> postOrder(TreeDistNode<T> root) {
    BiMap<TreeDistNode<T>, Integer> result = HashBiMap.create();
    final Iterable<TreeDistNode<T>> treeNodes = new SimpleTreeDistTraverser<T>().postOrderTraversal(root);
    int i = 1;
    for (TreeDistNode<T> node : treeNodes) {
      result.put(node, i++);
    }
    return result;
  }

  public static <T> int treeEditDistance(TreeDistNode<T> root1, TreeDistNode<T> root2, List<TreeDistOperation> ops,
      TriFunction<TreeDistNode<T>, TreeDistNode<T>, TreeDistOperationType, Integer> costFunction) {
    if(root1 == null || root1 == null || ops == null) {
      throw new RuntimeException(" the parameters shoud not be null");
    }
    final BiMap<TreeDistNode<T>, Integer> order1 = postOrder(root1);
    final BiMap<TreeDistNode<T>, Integer> order2 = postOrder(root2);

    int[] l1 = leftMostDescendants(order1);
    int[] l2 = leftMostDescendants(order2);

    int[] kr1 = keyRoots(root1, order1);
    int[] kr2 = keyRoots(root2, order2);

    CostAndOps[][] td = new CostAndOps[order1.size() + 1][order2.size() + 1];

    for (int aKr1 : kr1) {
      for (int aKr2 : kr2) {
        forestDistance(aKr1, aKr2, l1, l2, td, order1, order2, costFunction);
      }
    }

    ops.addAll(td[order1.size()][order2.size()].getOperations());
    return td[order1.size()][order2.size()].getCost();
  }

  private static <T> int[] leftMostDescendants(BiMap<TreeDistNode<T>, Integer> order) {
    int[] result = new int[order.size() + 1];
    for (int i = 1; i < result.length; i++) {
      TreeDistNode<T> localRoot = order.inverse().get(i);
      if (localRoot.isLeaf()) {
        result[i] = i;
      } else {
        final TreeDistNode<T> node = new SimpleTreeDistTraverser<T>().postOrderTraversal(localRoot).first().orNull();
        result[i] = order.get(node);
      }
    }
    return result;
  }

  private static <T> int[] keyRoots(TreeDistNode<T> root, BiMap<TreeDistNode<T>, Integer> order) {
    TreeSet<Integer> result = new TreeSet<>();
    new SimpleTreeDistTraverser<T>().breadthFirstTraversal(root).forEach(node -> {
      if (node.getParent() == null) {
        result.add(order.get(node));
      } else {
        final TreeDistNode parent = node.getParent();
        if (parent.getChildren().indexOf(node) > 0) {
          result.add(order.get(node));
        }
      }
    });
    int i = 0;
    int[] res = new int[result.size()];
    for (Integer num : result) {
      res[i++] = num;
    }
    return res;
  }

  private static <T> void forestDistance(int kr1, int kr2, int[] l1, int[] l2, CostAndOps[][] td,
      BiMap<TreeDistNode<T>, Integer> order1, BiMap<TreeDistNode<T>, Integer> order2,
      TriFunction<TreeDistNode<T>, TreeDistNode<T>, TreeDistOperationType, Integer> costFunction) {
    CostAndOps[][] fd = new CostAndOps[td.length + 1][td[0].length + 1];
    fd[l1[kr1] - 1][l2[kr2] - 1] = CostAndOps.ZERO;

    for (int di = l1[kr1]; di <= kr1; di++) {
      final Integer cost = costFunction.apply(order1.inverse().get(di),
          order2.inverse().get(l2[kr2] - 1), TreeDistOperationType.DELETE);
      fd[di][l2[kr2] - 1] = fd[di - 1][l2[kr2] - 1].addOperation(SimpleTreeDistOperation.delete(di, cost));
    }

    for (int dj = l2[kr2]; dj <= kr2; dj++) {
      final Integer cost = costFunction.apply(order1.inverse().get(l1[kr1] - 1),
          order2.inverse().get(dj), TreeDistOperationType.INSERT);
      fd[l1[kr1] - 1][dj] = fd[l1[kr1] - 1][dj - 1].addOperation(SimpleTreeDistOperation.insert(dj, cost));
    }

    for (int di = l1[kr1]; di <= kr1; di++) {
      for (int dj = l2[kr2]; dj <= kr2; dj++) {
        CostAndOps commonMin;
        final Integer costDelete = costFunction.apply(order1.inverse().get(di - 1), order2.inverse().get(dj),
            TreeDistOperationType.DELETE);
        final Integer costInsert = costFunction.apply(order1.inverse().get(di), order2.inverse().get(dj - 1),
            TreeDistOperationType.INSERT);
        if (fd[di - 1][dj].getCost() + costDelete < fd[di][dj - 1].getCost() + costInsert) {
          commonMin = fd[di - 1][dj].addOperation(SimpleTreeDistOperation.delete(di, costDelete));
        } else {
          commonMin = fd[di][dj - 1].addOperation(SimpleTreeDistOperation.insert(dj, costInsert));
        }
        if (l1[di] == l1[kr1] && l2[dj] == l2[kr2]) {
          TreeDistOperation op;
          if (order1.inverse().get(di).getData().equals(order2.inverse().get(dj).getData())) {
            final int cost = costFunction.apply(order1.inverse().get(di), order2.inverse().get(dj),
                TreeDistOperationType.KEEP);
            op = SimpleTreeDistOperation.keep(di, dj, cost);
          } else {
            final int cost = costFunction.apply(order1.inverse().get(di), order2.inverse().get(dj),
                TreeDistOperationType.DELETE);
            op = SimpleTreeDistOperation.replace(di, dj, cost);
          }
          if (commonMin.getCost() < fd[di - 1][dj - 1].addOperation(op).getCost()) {
            fd[di][dj] = commonMin;
          } else {
            fd[di][dj] = fd[di - 1][dj - 1].addOperation(op);
          }
          td[di][dj] = fd[di][dj];
        } else {
          if (commonMin.getCost() < fd[l1[di] - 1][l2[dj] - 1].getCost() + td[di][dj].getCost()) {
            fd[di][dj] = commonMin;
          } else {
            fd[di][dj] = fd[l1[di] - 1][l2[dj] - 1].addOperations(td[di][dj].getOperations());
          }
        }
      }
    }
  }

  private static int indexOfClosingBracket(String text, int openingBracket) {
    int result = -1;
    ArrayDeque<String> stack = new ArrayDeque<>();
    for (int i = openingBracket; i < text.length(); i++) {
      if (text.charAt(i) == '(') {
        stack.push("(");
      } else if (text.charAt(i) == ')') {
        if (stack.isEmpty()) {
          break;
        }
        stack.pop();
        if (stack.isEmpty()) {
          result = i;
          break;
        }
      }
    }
    return result;
  }

  private static List<String> split(String text) {
    final ArrayList<String> result = new ArrayList<>();
    String newText = "";
    Scanner scan = new Scanner(text).useDelimiter(" ");
    while (scan.hasNext()) {
      newText += scan.next();
      final int openingIdx = newText.indexOf("(");
      if (openingIdx == -1) {
        result.add(newText);
        newText = "";
      } else {
        final int closingIdx = indexOfClosingBracket(newText, openingIdx);
        if (closingIdx == -1) {
          newText += " ";
        } else {
          result.add(newText);
          newText = "";
        }
      }
    }
    return result;
  }

  private static class CostAndOps {
    private final int cost;
    private final List<TreeDistOperation> operations;

    @SuppressWarnings("unchecked")
    public final static CostAndOps ZERO = new CostAndOps(0, Collections.EMPTY_LIST);

    public CostAndOps(int cost, List<TreeDistOperation> operations) {
      this.cost = cost;
      this.operations = new ArrayList<>(operations);
    }

    public int getCost() {
      return cost;
    }

    public List<TreeDistOperation> getOperations() {
      return operations;
    }

    public CostAndOps addOperation(TreeDistOperation operation) {
      final ArrayList<TreeDistOperation> newOperations = new ArrayList<>(this.operations);
      newOperations.add(operation);
      return new CostAndOps(this.cost + operation.getCost(), newOperations);
    }

    public CostAndOps addOperations(List<TreeDistOperation> operations) {
      final ArrayList<TreeDistOperation> newOperations = new ArrayList<>(this.operations);
      newOperations.addAll(operations);
      return new CostAndOps(this.cost + operations.stream().mapToInt(TreeDistOperation::getCost).sum(), newOperations);
    }

    @Override
    public String toString() {
      return String.valueOf(cost);
    }
  }

  private static class SimpleTreeDistTraverser<T> extends TreeTraverser<TreeDistNode<T>> {

    @Override
    public Iterable<TreeDistNode<T>> children(TreeDistNode<T> tTreeDistNode) {
      return tTreeDistNode.getChildren();
    }
  }
  
  
  public static class MyDistanceFunc implements TriFunction<TreeDistNode<String>, TreeDistNode<String>, TreeDistOperationType, Integer>{
    List<String> concLike = Arrays.asList("+", "*", "%", "&");

    @Override
    public Integer apply(TreeDistNode<String> t, TreeDistNode<String> u, TreeDistOperationType s) {
      if(s == TreeDistOperationType.KEEP) {
        return 0;
      }
      if(t == null || u==null) {
        return 10;
      }
      
      if (t.getData().contains(":") && u.getData().contains(":")) {
        String[] q = t.getData().split(":");
        String[] w = u.getData().split(":");
        if(q[0]!=q[0]) {
          return 10;
        }
        if(q[1]!=w[1]) {
          return 5;
        } 
        return 1;
      }
      if(concLike.contains(t.getData()) && concLike.contains(u.getData())) {
        return 5;
      }
      return 10;
    }
    
  }
  
  private static SimpleTreeDistNode<String> convert(IInnerNode<NodeType> root) {
    Deque<SimpleTreeDistNode<String>> myStack = new ArrayDeque<>();
    VisitorCostumizer<NodeType> costumizer = new VisitorCostumizer<>();
    costumizer.registerPredicatedConsumer(node -> node.isLeaf(), p ->{
      myStack.push(new SimpleTreeDistNode<String>(p.toString()));
    });
    costumizer.registerPredicatedConsumer(node -> !node.isLeaf(), p ->{
      SimpleTreeDistNode<String> chSe = myStack.pop();
      SimpleTreeDistNode<String> chFi = myStack.pop();
      SimpleTreeDistNode<String> newOne = new SimpleTreeDistNode<>(p.getType().symbol);
      newOne.addChild(chFi);
      newOne.addChild(chSe);
      chFi.setParent(newOne);
      chSe.setParent(newOne);
      myStack.add(newOne);
    });
    DepthFirstPostOrderVisitor<NodeType> w = new DepthFirstPostOrderVisitor<>(costumizer);
    w.visit(root);
    return myStack.pop();
    
  }
  public static int treeEditDistance(IInnerNode<NodeType> one, IInnerNode<NodeType> two) {
    return treeEditDistance(convert(one), convert(two), new ArrayList<>(), new MyDistanceFunc());
    
  }

}
