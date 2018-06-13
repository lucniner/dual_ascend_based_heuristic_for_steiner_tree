package at.ac.tuwien.nda.dualascent.util;

public class Edge {

  private final int from;
  private final int to;
  private final int weight;

  public Edge(final int from, final int to, final int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  public int getFrom() {
    return from;
  }

  public int getTo() {
    return to;
  }

  public int getWeight() {
    return weight;
  }
}
