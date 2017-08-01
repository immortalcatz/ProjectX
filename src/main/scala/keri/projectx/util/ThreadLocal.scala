/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.util

class ThreadLocal[T](init: => T) extends java.lang.ThreadLocal[T] with Function0[T] {

  override def initialValue(): T = init

  override def apply(): T = get

  def withValue[S](thunk: (T => S)): S = thunk(get)

}
