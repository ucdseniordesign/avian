import java.util.BitSet;
import static avian.testing.Asserts.*;

public class BitsetTest {

  public static void main(String[] args) {
    BitSet bits = new BitSet(16);
    bits.set(5);
    bits.set(1);
    
    BitSet other = new BitSet(16);
    other.set(5);
    
    assertTrue(bits.get(1));
    assertTrue(bits.get(5));
    assertTrue(!bits.get(0));
    assertTrue(!bits.get(16));
    assertCardinality(bits, 2);
    
    bits.and(other);
    
    assertTrue(bits.get(5));
    assertTrue(!bits.get(1));
    assertCardinality(bits, 1);
    
    bits.set(100);
    
    assertTrue(bits.get(100));
    assertTrue(!bits.get(101));
    assertCardinality(bits, 2);
    
    other.set(101);
    
    bits.or(other);
    
    assertTrue(bits.get(101));
    
    assertEquals(5, bits.nextSetBit(0));
    assertEquals(5, bits.nextSetBit(4));
    assertEquals(5, bits.nextSetBit(5));
    assertEquals(100, bits.nextSetBit(6));
    assertEquals(100, bits.nextSetBit(100));
    assertEquals(101, bits.nextSetBit(101));
    assertEquals(-1, bits.nextSetBit(102));
    assertCardinality(bits, 3);
    
    assertEquals(0, bits.nextClearBit(0));
    assertEquals(6, bits.nextClearBit(5));
    assertEquals(102, bits.nextClearBit(100));
    
    testFlip();
    testClear();

    BitSet expandingSet = new BitSet();
    //should force us to have 3 partitions.
    expandingSet.set(128);
  }
  
  private static void testFlip() {
    /* simple case */
    BitSet bitset = new BitSet();
    bitset.set(0);
    bitset.flip(0, 0);
    assertTrue(bitset.get(0));
    bitset.flip(0, 1);
    assertTrue(!bitset.get(0));
    bitset.flip(0);
    assertTrue(bitset.get(0));
    
    /* need to grow */
    bitset.flip(1000);
    assertTrue(bitset.get(1000));
    assertTrue(!bitset.get(1001));
    assertTrue(!bitset.get(999));
    
    /* Range over 2 segments */
    bitset.flip(60, 70);
    assertTrue(!bitset.get(59));
    for (int i=60; i < 70; ++i) {
      assertTrue(bitset.get(i));
    }
    assertTrue(!bitset.get(70));
  }

  private static void testClear() {
    BitSet bitset = new BitSet();
    bitset.set(0, 20);
    assertCardinality(bitset, 20);

    bitset.clear(1);
    assertTrue(!bitset.get(1));
    assertCardinality(bitset, 19);

    bitset.clear(0, 3);
    assertTrue(!bitset.get(0));
    assertTrue(!bitset.get(1));
    assertTrue(!bitset.get(2));
    assertTrue(bitset.get(3));
    assertCardinality(bitset, 17);

    bitset = new BitSet(70);
    bitset.flip(0, 65);
    for (int i=0; i < 65; ++i) {
      assertTrue(bitset.get(i));
    }
    assertTrue(!bitset.get(65));
  }
  
  static void assertCardinality(BitSet set, int expectedCardinality) {
    assertEquals(expectedCardinality, set.cardinality());
  }
  
}
