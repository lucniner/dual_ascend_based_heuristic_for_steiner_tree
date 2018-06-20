package at.ac.tuwien.nda.dualascent.util;

import java.util.Objects;

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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Arc arc = (Arc) o;
    return from == arc.from &&
            to == arc.to &&
            weight == arc.weight;
  }

  @Override
  public int hashCode() {

    return Objects.hash(from, to, weight);
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
