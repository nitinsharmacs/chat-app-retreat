package com.tw.e4r.repository

import com.tw.e4r.domain.Message

import java.time.Instant
import java.util.Date
import scala.concurrent.Future

class ChatRepository:
  private val messages: List[Message] = List(
    Message("@atul", "Hey there!", Date.from(Instant.now()))
  )

  def getMessages: Future[List[Message]] =
    Future.successful(messages)
