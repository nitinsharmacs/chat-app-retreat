package com.tw.e4r.core.controller

import com.linecorp.armeria.common
import com.linecorp.armeria.common.{HttpResponse, HttpStatus}
import com.linecorp.armeria.server.annotation.Get
import com.tw.e4r.domain.Message
import com.tw.e4r.services.ChatService

import java.util.concurrent.{CompletableFuture, CompletionStage}
import scala.compat.java8.FutureConverters.FutureOps
import scala.concurrent.ExecutionContext

class ChatController(chatService: ChatService)(using ec: ExecutionContext):
  @Get("/message")
  def getMessages: CompletableFuture[HttpResponse] =
    val result = chatService.getMessages
    result.map(msgs => HttpResponse.ofJson(HttpStatus.OK, msgs)).toJava.toCompletableFuture
