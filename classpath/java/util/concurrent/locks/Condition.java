/* Copyright (c) 2008-2015, Avian Contributors

   Permission to use, copy, modify, and/or distribute this software
   for any purpose with or without fee is hereby granted, provided
   that the above copyright notice and this permission notice appear
   in all copies.

   There is NO WARRANTY for this software.  See license.txt for
   details. */

package java.util.concurrent.locks;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface Condition {
  public void await() throws InterruptedException ;
  public boolean await(long time, TimeUnit unit) throws InterruptedException;
  public long awaitNanos(long nanosTimeout) throws InterruptedException;
  public void awaitUninterruptibly();
  public boolean awaitUntil(Date deadline) throws InterruptedException;
  public void signal();
  public void signalAll();
}
