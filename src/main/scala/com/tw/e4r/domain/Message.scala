package com.tw.e4r.domain

import java.time.Instant
import java.util.{Date, UUID}

case class Message(id: UUID, sender: String, text: String, createdAt: Instant)

object Message:
  def apply(sender: String, text: String, createdAt: Instant): Message =
    new Message(UUID.randomUUID(), sender, text, createdAt)
