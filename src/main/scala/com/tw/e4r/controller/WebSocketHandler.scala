package com.tw.e4r.controller

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.linecorp.armeria.common.AggregatedHttpRequest
import com.linecorp.armeria.common.websocket.{
  CloseWebSocketFrame,
  WebSocket,
  WebSocketFrame,
  WebSocketFrameType,
  WebSocketWriter
}
import com.linecorp.armeria.server.ServiceRequestContext
import com.linecorp.armeria.server.websocket.WebSocketServiceHandler
import io.netty.handler.codec.http.HttpRequest
import org.reactivestreams.{Subscriber, Subscription}

import java.net.{URI, URL, URLDecoder}
import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext

def getBody(req: AggregatedHttpRequest): Map[String, AnyRef] =
  val mapper         = ObjectMapper()
  val node: JsonNode = mapper.readTree(req.contentUtf8())
  Map("id" -> node.get("id"))

class WebSocketHandler(using ec: ExecutionContext) extends WebSocketServiceHandler:
  private val connections: ListBuffer[Map[String, WebSocketWriter]] = ListBuffer()
  def sendMessage(id: String, message: String): Unit =
    println("==" * 10)
    println(id)
    connections.foreach { connection =>
      val key = connection.keys.toList(0)
      connection(key).write(message)
//      if key != id then connection(key).write(message)
    }
  override def handle(ctx: ServiceRequestContext, socket: WebSocket): WebSocket =
    println("=" * 10)
//    println()
//    println(new URL(ctx.request().uri().toString).getQuery.split("=")(1))
//    val body = getBody(ctx.request().aggregate().get())
//    println(body)
    val webSocketWriter = WebSocket.streaming
    val id: String      = new URL(ctx.request().uri().toString).getQuery.split("=")(1)
    println(id)
    println("=" * 10)
    connections.addOne(
      Map[String, WebSocketWriter](id -> webSocketWriter)
    )

    socket.subscribe(new Subscriber[WebSocketFrame]:
      def onSubscribe(s: Subscription): Unit =
        s.request(Long.MaxValue)

      def onNext(webSocketFrame: WebSocketFrame): Unit =
        try
          val frame = webSocketFrame
          try
            frame.`type` match
              case WebSocketFrameType.TEXT =>
                connections.foreach { connection =>
                  val key = connection.keys.toList(0)

                  if key != id then connection(key).write(s"Joined by $id")
                }

              case WebSocketFrameType.BINARY =>
              // do nothing

              case WebSocketFrameType.CLOSE =>
                val closeFrame = frame.asInstanceOf[CloseWebSocketFrame]
                webSocketWriter.close(closeFrame.status, closeFrame.reasonPhrase)

              case _ =>
              // do nothing
          catch
            case t: Throwable =>
              webSocketWriter.close(t)
          finally if frame != null then frame.close()

      def onError(t: Throwable): Unit =
        webSocketWriter.close(t)

      def onComplete(): Unit =
        webSocketWriter.close
    )
    webSocketWriter
