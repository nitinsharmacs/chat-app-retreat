package com.tw.e4r.services

import com.tw.e4r.domain.Message
import com.tw.e4r.repository.ChatRepository

import scala.concurrent.Future

class ChatService(chatRepository: ChatRepository):
  def getMessages: Future[List[Message]] =
    chatRepository.getMessages
