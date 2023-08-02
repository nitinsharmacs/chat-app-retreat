package com.tw.e4r.services

import com.tw.e4r.domain.Message
import com.tw.e4r.repository.ChatRepository

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}

val singleThreadEc = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))
class ChatService(chatRepository: ChatRepository):
  def getMessages: Future[List[Message]] =
    chatRepository.getMessages

  def createMessage(message: Message): Future[Unit] =
    chatRepository.addMessage(message)(using singleThreadEc)
