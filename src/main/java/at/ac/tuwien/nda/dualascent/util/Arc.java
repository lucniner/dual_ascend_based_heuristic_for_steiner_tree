package at.ac.tuwien.nda.dualascent.util;

public class Arc {

  private final int from;
  private final int to;
  private int weight;

  public Arc(final int from, final int to, final int weight) {
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

  public Arc setWeight(final int weight) {
    this.weight = weight;
    return this;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Arc{");
    sb.append("from=").append(from);
    sb.append(", to=").append(to);
    sb.append(", weight=").append(weight);
    sb.append('}');
    return sb.toString();
  }
}