package com.tw.e4r.domain

import java.util.{Date, UUID}

case class Message(id: UUID, sender: String, text: String, createdAt: Date)

object Message:
  def apply(sender: String, text: String, createdAt: Date): Message =
    new Message(UUID.randomUUID(), sender, text, createdAt)
