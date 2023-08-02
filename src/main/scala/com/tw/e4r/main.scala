package com.tw.e4r

import com.linecorp.armeria.common.{HttpResponse, HttpStatus}
import com.linecorp.armeria.server.{Server, ServerBuilder}
import org.slf4j.{Logger, LoggerFactory}

object Main:
  private val logger: Logger = LoggerFactory.getLogger(this.getClass)
  def main(args: Array[String]): Unit =
    val port: Int      = 8000
    val server: Server = newServer(port)
    val future         = server.start()
    future.join()
    logger.info(s"Server is running on port $port")

  private def newServer(port: Int) =
    val serverBuilder: ServerBuilder = Server.builder()
    serverBuilder
      .http(port)
      .decorator { (delegate, ctx, req) =>
        val url: String    = ctx.path()
        val method: String = ctx.method().toString

        logger.info(s"Request|Url: $url|method: $method")
        delegate.serve(ctx, req)
      }
      .service("/", (ctx, req) => HttpResponse.of(HttpStatus.OK))
      .build()
