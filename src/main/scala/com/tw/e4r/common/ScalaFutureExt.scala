package com.tw.e4r.common

import java.util.concurrent.CompletableFuture
import scala.concurrent.Future
import scala.compat.java8.FutureConverters.FutureOps

object ScalaFutureExt:
  extension [T](c: Future[T])
    def toJavaFuture: CompletableFuture[T] =
      c.toJava.toCompletableFuture
