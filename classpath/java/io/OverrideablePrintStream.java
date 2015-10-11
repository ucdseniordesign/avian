/* Copyright (c) 2008-2015, Avian Contributors

   Permission to use, copy, modify, and/or distribute this software
   for any purpose with or without fee is hereby granted, provided
   that the above copyright notice and this permission notice appear
   in all copies.

   There is NO WARRANTY for this software.  See license.txt for
   details. */

package java.io;

public class OverrideablePrintStream extends PrintStream {
  
  private volatile PrintStream override = null;

  public OverrideablePrintStream(OutputStream out, boolean autoFlush) {
    super(out, autoFlush);
  }

  public OverrideablePrintStream(OutputStream out, boolean autoFlush, String encoding)
    throws UnsupportedEncodingException
  {
    super(out, autoFlush, encoding);
  }

  public OverrideablePrintStream(OutputStream out) {
    this(out, false);
  }
  
  public void overrideStream(PrintStream ps){
    override = ps;
  }
  
  @Override
  public void setError() {
    if(override == null) {
      super.setError();
    } else {
      override.setError();
    }
  }
  
  @Override
  public boolean checkError() {
    if(override == null) {
      return super.checkError();
    } else {
      return override.checkError();
    }
  }
  
  @Override
  public void clearError() {
    if(override == null) {
      super.clearError();
    } else {
      override.clearError();
    }
  }

  @Override
  public void print(String s) {
    if(override == null) {
      super.print(s);
    } else {
      override.print(s);
    }
  }

  @Override
  public void println(String s) {
    if(override == null) {
      super.println(s);
    } else {
      override.println(s);
    }
  }

  @Override
  public void println() {
    if(override == null) {
      super.println();
    } else {
      override.println();
    }
  }
  
  public void write(int c) throws IOException {
    if(override == null) {
      super.write(c);
    } else {
      override.write(c);
    }
  }

  public void write(byte[] buffer, int offset, int length) {
    if(override == null) {
      super.write(buffer, offset, length);
    } else {
      override.write(buffer, offset, length);
    }
  }

  public void flush() {
    if(override == null) {
      super.flush();
    } else {
      override.flush();
    }
  }

  public void close() {
    if(override == null) {
      super.close();
    } else {
      override.close();
    }
  }
}
