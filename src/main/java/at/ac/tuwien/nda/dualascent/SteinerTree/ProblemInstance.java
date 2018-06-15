package at.ac.tuwien.nda.dualascent.SteinerTree;

import at.ac.tuwien.nda.dualascent.util.Arc;

import java.util.*;

public class ProblemInstance {
  private List<Integer> terminals;

  private boolean isDirected;

  private HashMap<Integer, List<Arc>> arcs_by_from_node;
  private HashMap<Integer, List<Arc>> arcs_by_to_node;

  public ProblemInstance() {
    this.arcs_by_from_node = new HashMap<>();
    this.arcs_by_to_node = new HashMap<>();

    this.isDirected = true;
  }

  public void addArc(Arc arc) {
    int from = arc.getFrom();
    int to = arc.getTo();

    if (arcs_by_from_node.get(from) == null) {
      List arcList = new ArrayList();
      arcList.add(arc);
      arcs_by_from_node.put(from, arcList);
    } else {
      arcs_by_from_node.get(from).add(arc);
    }

    if (arcs_by_to_node.get(to) == null) {
      List arrayList = new ArrayList();
      arrayList.add(arc);
      arcs_by_to_node.put(to, arrayList);
    } else {
      arcs_by_to_node.get(to).add(arc);
    }
  }

  public List<Integer> getTerminals() {
    return new ArrayList<>(terminals);
  }

  public void setTerminals(List<Integer> terminals) {
    this.terminals = terminals;
  }

  public HashMap<Integer, List<Arc>> getGraph() {
    return arcs_by_from_node;
  }

  public HashMap<Integer, List<Arc>> getGraphByToNode() {
    return arcs_by_to_node;
  }

  public HashMap<Integer, List<Arc>> cloneGraphByToNode() {
    HashMap<Integer, List<Arc>> map = new HashMap<>();
    for (Map.Entry entry : arcs_by_to_node.entrySet()) {
      List<Arc> newArcs = new ArrayList<>();
      for (Arc arc : (List<Arc>)entry.getValue()) {
        newArcs.add(new Arc(arc.getFrom(), arc.getTo(), arc.getWeight()));
      }
      map.put((Integer)entry.getKey(), newArcs);
    }
    return  map;
  }

  public Optional<Integer> getWeight(int from, int to) {
    Optional<Integer> weight = Optional.empty();

    for(Integer node : arcs_by_from_node.keySet()) {
      for (Arc arc : arcs_by_from_node.get(node)) {
        if ((arc.getFrom() == from) && (arc.getTo() == to)) {
          weight = Optional.of(arc.getWeight());
        }
      }
    }
    return weight;
  }

  public boolean isDirected() {
    return isDirected;
  }

  public void setDirected(boolean directed) {
    isDirected = directed;
  }
}
