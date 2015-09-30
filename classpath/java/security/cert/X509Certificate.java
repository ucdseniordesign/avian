package java.security.cert;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Date;

public abstract class X509Certificate extends Certificate {

  public static X509Certificate   getInstance(byte[] certData) {
    return null;
  }

  public static X509Certificate  getInstance(InputStream inStream) {
    return null;
  }

  public abstract Principal  getIssuerDN();

  public abstract Date getNotAfter();

  public abstract Date getNotBefore();

  public abstract BigInteger getSerialNumber();

  public abstract String getSigAlgName();

  public abstract String getSigAlgOID();
  
  public abstract byte[] getSigAlgParams();

  public abstract Principal getSubjectDN();

  public abstract int getVersion();

}
