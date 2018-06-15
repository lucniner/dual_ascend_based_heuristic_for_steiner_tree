package at.ac.tuwien.nda.dualascent.SteinerTree;

import at.ac.tuwien.nda.dualascent.util.Arc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProblemInstance {
  private int nodeNumber;
  private int edgeNumber;
  private int terminalNumber;
  private List<Integer> terminals;

  private HashMap<Integer, List<Arc>> graph;

  public ProblemInstance() {
    this.graph = new HashMap<>();
  }

  public void addArc(int node, Arc arc) {
    if (graph.get(node) == null) {
      List arcList = new ArrayList();
      arcList.add(arc);
      graph.put(node, arcList);
    } else {
      graph.get(node).add(arc);
    }
  }

  public int getNodeNumber() {
    return nodeNumber;
  }

  public void setNodeNumber(int nodeNumber) {
    this.nodeNumber = nodeNumber;
  }

  public int getEdgeNumber() {
    return edgeNumber;
  }

  public void setEdgeNumber(int edgeNumber) {
    this.edgeNumber = edgeNumber;
  }

  public int getTerminalNumber() {
    return terminalNumber;
  }

  public void setTerminalNumber(int terminalNumber) {
    this.terminalNumber = terminalNumber;
  }

  public List<Integer> getTerminals() {
    return new ArrayList<>(terminals);
  }

  public void setTerminals(List<Integer> terminals) {
    this.terminals = terminals;
  }

  public HashMap<Integer, List<Arc>> getGraph() {
    return graph;
  }
}
