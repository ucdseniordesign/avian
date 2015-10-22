import javax.net.ssl.SSLSessionBindingEvent;
import static avian.testing.Asserts.assertTrue;

public class NativeTest {

  public static void main(String[] args) throws Exception {
    SSLSessionBindingEvent.print("HELLO"); 
  }
}
