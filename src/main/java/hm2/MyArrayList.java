package hm2;

import java.util.AbstractList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;


public class MyArrayList<E> extends AbstractList<E> {

  private static final int DEFAULT_CAPACITY = 10;
  private int size;
  private Object[] elementItems;

  public MyArrayList() {
    this.elementItems = new Object[DEFAULT_CAPACITY];
  }

  public MyArrayList(int size) {
    if (size >= 0 ) {
      elementItems = new Object[size];
    }else {
      throw new IllegalArgumentException("Illegal Capacity: " + size);
    }

  }

  @Override
  public void add(int index, Object x) {
    if ((size+1)> elementItems.length) {
      increaseArraySize();
    }
    System.arraycopy(elementItems, index, elementItems, index + 1, (elementItems.length -1 ) - index);
    elementItems[index] = x;
    size++;
  }

  private void increaseArraySize(){
    Object[] newElementItems = new Object[(size + 1) * 2];

    System.arraycopy(elementItems, 0, newElementItems, 0, size);

    elementItems = newElementItems;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    Object[] newArray = c.toArray();

    while (elementItems.length < size + newArray.length) {
      E[] newArr = (E[]) new Object[elementItems.length * 2];
      System.arraycopy(elementItems, 0, newArr, 0, size);
      elementItems = newArr;
    }
    System.arraycopy(newArray, 0, elementItems, size, newArray.length);
    size += newArray.length;
    return true;
  }
  public E get(int index) {
    if (index > elementItems.length) {
      throw new IndexOutOfBoundsException();
    } else {
      return (E) this.elementItems[index];
    }
  }

  public int size() {
    return size;
  }

  @Override
  public void clear() {
    for (int i = 0; i < this.size; i++) {
      this.elementItems[i] = null;
    }
    this.size = 0;
  }

  @Override
  public boolean isEmpty(){
    return size == 0;
  }

  @Override
  public boolean remove(Object x){
    int index = find(x);
    System.arraycopy(elementItems, index +1 , elementItems, index, (elementItems.length - 1) - index);
    elementItems[elementItems.length - 1] = null;
    size--;
    return true;
  }

  public int find(Object x) {
    int indexOfObject = -1;
    for (int i = 0; i < elementItems.length; i++){
      if (elementItems[i] == x)
        indexOfObject = i;
    }
    return indexOfObject;
  }

  @Override
  public E remove(int index) {
    E element = (E) elementItems[index];
    elementItems[index] = null;
    System.arraycopy(elementItems, index+1, elementItems, index, size - index-1);
    elementItems[size - 1] = null;
    size--;
    return element;
  }

  @Override
  public void sort(Comparator<? super E> c) {

    if (c == null) throw new NullPointerException();

    mergeSort(elementItems, c);

  }

  public void mergeSort(Object[] elementData, Comparator<? super E> c) {
    if (elementData.length > 1) {
      int mid = elementData.length / 2;
      Object[] left = Arrays.copyOfRange(elementData, 0, mid);
      Object[] right = Arrays.copyOfRange(elementData, mid, elementData.length);

      mergeSort(left, c);
      mergeSort(right, c);

      merge(elementData, left, right, c);
    }
  }

  private void merge(Object[] result, Object[] left, Object[] right, Comparator<? super E> c) {
    int i = 0, j = 0, k = 0;

    while (i < left.length && j < right.length) {
      if (left[i] == null) {
        result[k++] = right[j++];
      } else if (right[j] == null) {
        result[k++] = left[i++];
      } else if (c.compare((E) left[i], (E) right[j]) <= 0) {
        result[k++] = left[i++];
      } else {
        result[k++] = right[j++];
      }
    }

    while (i < left.length) {
      result[k++] = left[i++];
    }

    while (j < right.length) {
      result[k++] = right[j++];
    }
  }
}
