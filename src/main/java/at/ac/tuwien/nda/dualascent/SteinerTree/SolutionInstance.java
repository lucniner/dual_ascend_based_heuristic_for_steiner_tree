package at.ac.tuwien.nda.dualascent.SteinerTree;

import at.ac.tuwien.nda.dualascent.util.Arc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SolutionInstance {
  private int rootTerminal;
  private HashSet<Integer> steinerTree;
  private int distanceSum;
  private HashMap<Integer, List<Arc>> arcs;

  public SolutionInstance(int rootTerminal) {
    this.rootTerminal = rootTerminal;
    this.steinerTree = new HashSet<>();
    this.distanceSum = 0;
    this.arcs = new HashMap<>();
  }

  public void addNode(Integer node) {
    this.steinerTree.add(node);
  }

  public HashSet<Integer> getSteinerTree() {
    return steinerTree;
  }

  public int getDistanceSum() {
    return distanceSum;
  }

  public HashMap<Integer, List<Arc>> getArcs() {
    return arcs;
  }

  public void addArc(Integer node, Arc  arc, int weight) {
    if (!arcs.containsKey(node)) {
      arcs.put(node, new ArrayList<>());
    }
    if (!arcs.get(node).contains(arc)) {
      arcs.get(node).add(arc);
      distanceSum += weight;
    }
  }

  public int getRootTerminal() {
    return rootTerminal;
  }
}
