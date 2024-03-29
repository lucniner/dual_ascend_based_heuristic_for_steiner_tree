package at.ac.tuwien.nda.dualascent.util;

import java.util.Objects;

public class Pair<T, E> {

  private T key;
  private E value;

  public Pair(final T key, final E value) {
    this.key = key;
    this.value = value;
  }

  public T getKey() {
    return key;
  }

  public E getValue() {
    return value;
  }

  public void setKey(T key) {
    this.key = key;
  }

  public void setValue(E value) {
    this.value = value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Pair<?, ?> pair = (Pair<?, ?>) o;
    return Objects.equals(key, pair.key) &&
            Objects.equals(value, pair.value);
  }

  @Override
  public int hashCode() {

    return Objects.hash(key, value);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Pair{");
    sb.append("key=").append(key);
    sb.append(", value=").append(value);
    sb.append('}');
    return sb.toString();
  }
}