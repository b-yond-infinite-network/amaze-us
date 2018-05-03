package com.catsandmoods.support

/**
  * cats-and-moods
  *
  */

import java.io.Closeable
import java.net.InetSocketAddress

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.exceptions.SyntaxError
import com.typesafe.config.Config
import org.slf4j.LoggerFactory

import scala.io.Source

object CassandraSupport extends Closeable {

  def executeScriptFromFile(filePath: String): Boolean = executeScript(Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(filePath)))

  lazy val cluster = Cluster.builder()
    .withoutJMXReporting()
    .withoutMetrics()
        .addContactPoint("localhost")
    .build()

  def executeScript(source: Source): Boolean = {
    val session = cluster.newSession()

    def execute(statement: String) = {
      try {
        session.execute(statement)
      } catch {
        case (se: SyntaxError) ⇒ {
          throw se
        }
      }
    }

    try {
      val statements = buildStatements(source)
      statements.foldLeft(true)((result, statement) ⇒ result && execute(statement).wasApplied())
    } finally {
      session.close()
    }

  }

  def buildStatements(source: Source) = {
    val script = source.getLines().mkString
    script.split(";").filter(s ⇒ !s.isEmpty && !s.trim.isEmpty)
  }

  override def close(): Unit = cluster.close()
}
