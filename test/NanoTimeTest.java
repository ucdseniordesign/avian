
import static avian.testing.Asserts.*;

public class NanoTimeTest {

  public static void main(String[] args) throws InterruptedException {
    long last = System.nanoTime();
    for(int i=0; i<100; i++) {
      Thread.sleep(1);
      long nt = System.nanoTime();
      assertTrue(nt>last);
      last = nt;
    }
  }
}
