package com.regisleray.db

import cats.effect.{IO, Resource}
import cats.implicits._
import com.regisleray.Cat.Mood
import doobie.free.connection.ConnectionIO
import doobie.hikari.HikariTransactor
import doobie.implicits._
import doobie.util.query.Query0
import doobie.util.update.{Update, Update0}
import doobie.util.{ExecutionContexts, Meta}

object Db{
  implicit val moodMeta: Meta[Mood] = Meta[String].imap(Mood.from(_).fold(throw _, identity))(_.toString.toLowerCase)

  private[db] val createTableQ: Update0 =
    sql"""
        CREATE TABLE IF NOT EXISTS moods (
          id      VARCHAR NOT NULL PRIMARY KEY,
          counter INT NOT NULL
        )
      """.update

  private val upsertQ = Update[Mood](
    """
      | INSERT INTO moods (id, counter)
      |  VALUES (?, 1)
      |  ON CONFLICT (id)
      |  DO UPDATE SET counter = moods.counter + 1
    """.stripMargin)

  private val selectAllQ = Query0[(Mood, Int)]("SELECT id, counter FROM moods")

  def upsert(mood: Mood): doobie.ConnectionIO[Int] = upsertQ.run(mood)

  def selectAll: doobie.ConnectionIO[List[(Mood, Int)]] = selectAllQ.to[List]
}


object DBSupport {
  implicit val cs = IO.contextShift(scala.concurrent.ExecutionContext.Implicits.global)

  private[db] val transactor: Resource[IO, HikariTransactor[IO]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
      te <- ExecutionContexts.cachedThreadPool[IO]    // our transaction EC
      xa <- HikariTransactor.newHikariTransactor[IO](
        "org.postgresql.Driver",                        // driver classname
        "jdbc:postgresql://localhost:5432/cats",   // connect URL
        "postgres",                                   // username
        "postgres",                                     // password
        ce,                                     // await connection here
        te                                      // execute JDBC operations here
      )
    } yield xa

  def setup(): IO[Unit] = {
    transactor.use(Db.createTableQ.run.transact(_)).map(_ => ())
  }

  final class ConnectionIOOps[A](val self: ConnectionIO[A]){
    def exec: IO[A] = DBSupport.transactor.use(self.transact(_))
  }

  trait ConnectionIOSyntax {
    implicit def toConnectionIOOps[A](value: ConnectionIO[A]): ConnectionIOOps[A] = new ConnectionIOOps(value)
  }
}


