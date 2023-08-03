package com.tw.e4r

import com.linecorp.armeria.common.{HttpMethod, HttpResponse, HttpStatus}
import com.linecorp.armeria.server.cors.CorsService
import com.linecorp.armeria.server.websocket.WebSocketService
import com.linecorp.armeria.server.{Server, ServerBuilder}
import com.tw.e4r.controller.{ChatController, WebSocketHandler}
import com.tw.e4r.repository.ChatRepository
import com.tw.e4r.services.ChatService
import org.slf4j.{Logger, LoggerFactory}

import java.time.Duration
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import concurrent.ExecutionContext.Implicits.global

object Main:
  private val logger: Logger = LoggerFactory.getLogger(this.getClass)
  given ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(12))
  def main(args: Array[String]): Unit =
    val port: Int      = 8000
    val server: Server = newServer(port)
    val future         = server.start()
    future.join()
    logger.info(s"Server is running on port $port")

  private def newServer(port: Int) =
    val serverBuilder: ServerBuilder = Server.builder()
    val webSocketHandler             = new WebSocketHandler(using ec)

    val corsService = CorsService.builderForAnyOrigin.allowCredentials
      .allowRequestMethods(HttpMethod.POST, HttpMethod.GET, HttpMethod.UNKNOWN)
      .allowRequestHeaders("*")
      .newDecorator

    serverBuilder
      .http(port)
      .decorator(corsService)
      .decorator { (delegate, ctx, req) =>
        val url: String    = ctx.path()
        val method: String = ctx.method().toString

        logger.info(s"Request|Url: $url|method: $method")
        delegate.serve(ctx, req)
      }
      .service(
        "/socket",
        WebSocketService.builder(webSocketHandler).allowedOrigins("*").build()
      )
      .service(
        "/",
        (ctx, req) => HttpResponse.of(HttpStatus.OK)
      )
      .annotatedService(
        "/e4r",
        ChatController(ChatService(new ChatRepository))(using ec, webSocketHandler)
      )
      .requestTimeout(Duration.ofSeconds(1000))
      .build()
