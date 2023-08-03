package com.tw.e4r.controller

import com.fasterxml.jackson.annotation.JsonKey
import com.linecorp.armeria.common
import com.linecorp.armeria.common.{HttpResponse, HttpStatus}
import com.linecorp.armeria.server.annotation.{
  Get,
  JacksonRequestConverterFunction,
  Post,
  RequestConverter,
  RequestObject
}
import com.tw.e4r.domain.Message
import com.tw.e4r.services.ChatService

import java.util.concurrent.{CompletableFuture, CompletionStage}
import scala.concurrent.{ExecutionContext, Future}
import com.tw.e4r.common.ScalaFutureExt.*
import com.tw.e4r.dto.MessageDTO

class ChatController(
    chatService: ChatService
  )(using
    ec: ExecutionContext,
    socket: WebSocketHandler):
  @Get("/message")
  def getMessages: CompletableFuture[HttpResponse] =
    val result = chatService.getMessages
    result.map(messages => HttpResponse.ofJson(HttpStatus.OK, messages)).toJavaFuture

  @Post("/create-message")
  @RequestConverter(classOf[JacksonRequestConverterFunction])
  def createMessage(message: MessageDTO): CompletableFuture[HttpResponse] =

    socket.sendMessage(message.sender, message.toMessage.toJson)
    chatService
      .createMessage(message.toMessage)
      .map(_ => HttpResponse.of(HttpStatus.CREATED))
      .toJavaFuture
