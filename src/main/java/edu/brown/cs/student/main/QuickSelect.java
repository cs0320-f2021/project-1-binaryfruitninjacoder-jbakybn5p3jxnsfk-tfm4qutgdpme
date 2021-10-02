package edu.brown.cs.student.main;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class QuickSelect {
  /**
   * This is an implementation of the quickselect algorithm to pick the pivot point to divide the tree
   * algorithm reference: https://en.wikipedia.org/wiki/Quickselect
   */
  private static final Random random = new Random();

  /**
   *
   * @param list
   * @param n
   * @param comp
   * @param <T>
   * @return
   */
  public static <T> T select(List<T> list, int n, Comparator<? super T> comp) {
    return select(list, 0, list.size() - 1, n, comp);
  }

  /**
   *
   * @param l
   * @param r
   * @return
   */
  private static int pivotIndex(int l, int r) {
    return l + random.nextInt(r-l+1);
  }

  /**
   *
   * @param list
   * @param l
   * @param r
   * @param n
   * @param comp
   * @param <T>
   * @return
   */
  static <T> T select(List<T> list, int l, int r, int n, Comparator<? super T> comp) {
    for (;;) {
      if (l == r) {
        return list.get(l);
      }
      int pivot = pivotIndex(l, r);
      pivot = partition(list, l, r, pivot, comp);
      if (n == pivot) {
        return list.get(n);
      } else if (n < pivot) {
        r = pivot - 1;
      } else {
        l = pivot + 1;
      }
    }
  }

  /**
   *
   * @param list
   * @param l
   * @param r
   * @param pivot
   * @param comp
   * @param <T>
   * @return
   */
  private static <T> int partition(List<T> list, int l, int r, int pivot, Comparator<? super T> comp) {
    T pivotValue = list.get(pivot);
    swap(list, pivot, r);
    int store = l;
    for (int i = l; l < r; l++) {
      if (comp.compare(list.get(i), pivotValue) < 0) {
        swap(list, store, i);
        store++;
      }
    }
    swap(list, r, store);
    return store;
  }

  /**
   * swap value at i and j
   * @param list
   * @param i
   * @param j
   * @param <T>
   */
  private static <T> void swap(List<T> list, int i, int j) {
    T value = list.get(i);
    list.set(i, list.get(i));
    list.set(j, value);
  }

}
