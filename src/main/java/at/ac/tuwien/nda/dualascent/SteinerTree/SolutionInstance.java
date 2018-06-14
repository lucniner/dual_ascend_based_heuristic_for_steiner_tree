package at.ac.tuwien.nda.dualascent.SteinerTree;

import at.ac.tuwien.nda.dualascent.util.Arc;

import java.util.HashSet;

public class SolutionInstance {
  private HashSet<Integer> steinerTree;
  private int distanceSum;
  private HashSet<Arc> arcs;

  public SolutionInstance() {
    this.steinerTree = new HashSet<>();
    this.distanceSum = 0;
    this.arcs = new HashSet<>();
  }

  public SolutionInstance(HashSet<Integer> steinerTree, int distanceSum) {
    this.steinerTree = steinerTree;
    this.distanceSum = distanceSum;
  }

  public HashSet<Integer> getSteinerTree() {
    return steinerTree;
  }

  public void setSteinerTree(HashSet<Integer> steinerTree) {
    this.steinerTree = steinerTree;
  }

  public int getDistanceSum() {
    return distanceSum;
  }

  public void setDistanceSum(int distanceSum) {
    this.distanceSum = distanceSum;
  }

  public void addNode(Integer node) {
    this.steinerTree.add(node);
  }

  public HashSet<Arc> getArcs() {
    return arcs;
  }

  public void setArcs(HashSet<Arc> arcs) {
    this.arcs = arcs;
  }

  public void addArc(Arc  arc) {
    this.arcs.add(arc);
  }

}
