import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.PropertyResourceBundle;

import static avian.testing.Asserts.*;

public class PropertyResourceBundleTest {

  public static void main(String[] args) throws Exception {
    InputStream inputStream = new ByteArrayInputStream("TEST=blah\nNEW=TEST\nGET:OUT".getBytes());
    PropertyResourceBundle prb = new PropertyResourceBundle(inputStream);
    Enumeration<String> k = prb.getKeys();
    HashSet<String> set = new HashSet<String>();
    while(k.hasMoreElements()) {
      set.add(k.nextElement());
    }
    assertTrue(set.contains("TEST"));
    assertTrue(set.contains("NEW"));
    assertTrue(set.contains("GET"));
    assertEquals(prb.getString("TEST"), "blah");
    assertEquals(prb.getString("NEW"), "TEST");
    assertEquals(prb.getString("GET"), "OUT");
  }
}
