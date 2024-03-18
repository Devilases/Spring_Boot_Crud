package hm2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyArrayListTest {

  private MyArrayList<Integer> list;

  @BeforeEach
  public void setUp() {
    list = new MyArrayList<>();
    list.add(0, 10);
    list.add(1, 29);
    list.add(2, 3);
    list.add(3, 3);
    list.add(4, 5);
    list.add(5, 19);
  }

  @Test
  void addElement() {
    assertEquals(6, list.size());

    list.add(6, 10);

    assertEquals(10, list.get(list.size()-1));
  }

  @Test
  void getElementWithIndex() {

    assertEquals(19, list.get(5));
    assertEquals(29, list.get(1));
    assertEquals(5, list.get(4));
  }

  @Test
  void clear() {
    list.clear();
    assertEquals(0, list.size());
  }

  @Test
  void isEmpty() {
    assertEquals(false, list.isEmpty());
    list.clear();
    assertEquals(true, list.isEmpty());
  }

  @Test
  void remove() {
    list.remove(5);
    assertNull(list.get(5));

  }
}