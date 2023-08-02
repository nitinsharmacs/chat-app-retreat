package com.tw.e4r.repository

import com.tw.e4r.domain.Message

import java.time.Instant
import java.util.Date
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

class ChatRepository:
  private val messages: ListBuffer[Message] = ListBuffer(
    Message("@atul", "Hey there!", Instant.now())
  )

  def getMessages: Future[List[Message]] =
    Future.successful(messages.toList)

  def addMessage(message: Message): Future[Unit] =
    Future.successful(this.messages.addOne(message))
