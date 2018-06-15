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
  private List<Integer> allTerminals;

  public SolutionInstance(int rootTerminal, List<Integer> allTerminals) {
    this.rootTerminal = rootTerminal;
    this.steinerTree = new HashSet<>();
    this.steinerTree.add(rootTerminal);
    this.distanceSum = 0;
    this.arcs = new HashMap<>();
    this.allTerminals = allTerminals;
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

  public ProblemInstance convertToProblemInstance() {
    ProblemInstance problemInstance = new ProblemInstance();
    problemInstance.setTerminals(allTerminals);

    for (Integer node : arcs.keySet()) {
      for (Arc arc : arcs.get(node)) {
        problemInstance.addArc(new Arc(arc.getFrom(), arc.getTo(), arc.getWeight()));
      }
    }

    return problemInstance;
  }
}
