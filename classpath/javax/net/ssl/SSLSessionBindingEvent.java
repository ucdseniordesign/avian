package javax.net.ssl;

import java.util.EventObject;

public class SSLSessionBindingEvent extends EventObject{

   private final SSLSession session;
   private final String name;


   public SSLSessionBindingEvent(SSLSession session, String name){
       super(session);
       this.session = session;
       this.name = name;
   }

   public String getName(){
      return name;
   }

   public SSLSession getSession(){
       return session;
   }

   public static native void print( String str );
}
