
import static avian.testing.Asserts.assertTrue;

public class NanoTimeTest {

  public static void main(String[] args) throws Exception {
    long last = System.nanoTime();
    for(int i=0; i<100; i++) {
      Thread.sleep(1);
      long nt = System.nanoTime();
      assertTrue(nt>last);
      last = nt;
    }
  }
}
