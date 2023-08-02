package com.tw.e4r.controller

import com.linecorp.armeria.common
import com.linecorp.armeria.common.{HttpResponse, HttpStatus}
import com.linecorp.armeria.server.annotation.Get
import com.tw.e4r.domain.Message
import com.tw.e4r.services.ChatService

import java.util.concurrent.{CompletableFuture, CompletionStage}
import scala.concurrent.{ExecutionContext, Future}
import com.tw.e4r.common.ScalaFutureExt.*

class ChatController(chatService: ChatService)(using ec: ExecutionContext):
  @Get("/message")
  def getMessages: CompletableFuture[HttpResponse] =
    val result = chatService.getMessages
    result.map(messages => HttpResponse.ofJson(HttpStatus.OK, messages)).toJavaFuture
