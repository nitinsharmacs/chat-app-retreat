package com.tw.e4r.dto

import com.tw.e4r.domain.Message

import java.time.Instant
import java.util.{Date, UUID}

case class MessageDTO(sender: String, text: String, createdAt: Instant):
  def toMessage: Message =
    Message(sender, text, createdAt)
