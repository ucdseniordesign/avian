import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static avian.testing.Asserts.*;

public class EnumSetTest {
  private enum SmallEnum {
    ONE,
    TWO,
    THREE
  }
  
  private enum LargerEnum {
    LARGEONE,
    LARGETWO,
    LARGETHREE,
    LARGEFOUR,
    LARGEFIVE,
    LARGESIX
  }
  
  public static void main(String[] args) {
    testAllOf();
    testNoneOf();
    testIterators();
    testOf();
    testCopyOf();
    testComplimentOf();
  }
  
  private static void testComplimentOf() {
    EnumSet<SmallEnum> one = EnumSet.of(SmallEnum.ONE, SmallEnum.THREE);
    EnumSet<SmallEnum> two = EnumSet.complementOf(one);
    assertContains(SmallEnum.TWO, two);
    assertEquals(1, two.size());
  }

  private static void testCopyOf() {
    EnumSet<SmallEnum> one = EnumSet.of(SmallEnum.ONE, SmallEnum.THREE);
    EnumSet<SmallEnum> two = EnumSet.copyOf(one);
    assertContains(SmallEnum.ONE, two);
    assertContains(SmallEnum.THREE, two);
    assertEquals(2, two.size());
  }

  private static void testOf() {
    EnumSet<LargerEnum> set = EnumSet.of(LargerEnum.LARGEONE, LargerEnum.LARGEFIVE, LargerEnum.LARGETWO);
    assertContains(LargerEnum.LARGEONE, set);
    assertContains(LargerEnum.LARGEFIVE, set);
    assertContains(LargerEnum.LARGETWO, set);
    assertEquals(3, set.size());
  }

  private static void testAllOf() {
    EnumSet<SmallEnum> set = EnumSet.allOf(SmallEnum.class);
    for (SmallEnum current : SmallEnum.values()) {
      assertContains(current, set);
    }
    assertEquals(3, set.size());
  }
  
  private static void testNoneOf() {
    EnumSet<SmallEnum> set = EnumSet.noneOf(SmallEnum.class);
    assertEquals(0, set.size());
  }
  
  private static void testIterators() {
    EnumSet<SmallEnum> set = EnumSet.allOf(SmallEnum.class);
    Iterator<SmallEnum> iterator = set.iterator();
    boolean exceptionCaught = false;
    try {
      iterator.remove();
    } catch (IllegalStateException e) {
      exceptionCaught = true;
    }
    if (!exceptionCaught) {
      throw new RuntimeException("Calling remove() before next() should throw IllegalStateException");
    }
    
    while (iterator.hasNext()) {
      iterator.next();
      iterator.remove();
    }
    assertEquals(0, set.size());

    exceptionCaught = false;
    try {
      iterator.next();
    } catch (NoSuchElementException e) {
      exceptionCaught = true;
    }
    if (!exceptionCaught) {
      throw new RuntimeException("Calling next() when hasNext() == false should throw NoSuchElementException");
    }
  }
  
  
}
