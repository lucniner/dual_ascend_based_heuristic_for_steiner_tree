package at.ac.tuwien.nda.dualascent.SteinerTree;

import at.ac.tuwien.nda.dualascent.util.Pair;

import java.util.List;

public class ProblemInstance {
  private int nodeNumber;
  private int edgeNumber;
  private int terminalNumber;
  private List<Pair<Integer, Integer>> edges;
  private List<Integer> terminals;

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

  public List<Pair<Integer, Integer>> getEdges() {
    return edges;
  }

  public void setEdges(List<Pair<Integer, Integer>> edges) {
    this.edges = edges;
  }

  public List<Integer> getTerminals() {
    return terminals;
  }

  public void setTerminals(List<Integer> terminals) {
    this.terminals = terminals;
  }
}
