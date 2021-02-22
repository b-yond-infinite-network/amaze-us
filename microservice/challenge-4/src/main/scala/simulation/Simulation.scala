package simulation

import cats.effect.{Blocker, ExitCode, IO, IOApp}
import cats.implicits.catsSyntaxFlatMapOps
import fs2.{Pipe, Stream, text}
import simulation.CatRecord.recordToStat
import scala.util.Random.between

object CatSimulation extends IOApp {

  val GROWL = "grr"
  val HISS = "kssss"
  val PURR = "rrrrr"
  val THROWGLASS = "cling bling"
  val ROLLONFLOOR = "fffff"
  val SCRATCHCHAIRS = "gratgrat"
  val LOOKDEEPINEYES = "-o-o-___--"
  val MEOW = "meow"

  val catMoods = Map(1 -> GROWL, 2 -> HISS, 3 -> PURR, 4 -> THROWGLASS, 5 -> ROLLONFLOOR, 6 -> SCRATCHCHAIRS, 7 -> LOOKDEEPINEYES, 8 -> MEOW)

  val csvEncode: Pipe[IO, CatRecord, Byte] =
    _
      .map(x => s"${x.catId},${x.mood},${x.time}")
      .intersperse("\n")
      .through(text.utf8Encode)

  val csvDecode: Pipe[IO, Byte, CatRecord] =
    _
      .through(text.utf8Decode)
      .through(text.lines)
      .map(x => {
        val cols = x.split(",")
        CatRecord(cols(0).toLong,
          cols(1),
          cols(2).toLong)
      })


  lazy val timeStream = Stream.awakeEvery[IO](DefaultConfig.interval)
    .map(t => t._1)
    .interruptAfter(DefaultConfig.duration + DefaultConfig.interval)
  // Adds one extra interval because its what a human would expect happen

  lazy val moodStream = Stream
    .emits(1l to DefaultConfig.cats)
    .map(catId => (catId, catMoods.getOrElse(between(1, 10), MEOW)))
    .take(DefaultConfig.cats)

  lazy val recordStream = timeStream.flatMap(time => moodStream
    .map(c => c match {
      case (id: Long, mood: String) => CatRecord(id, mood, time)
    })
  )

  override def run(args: List[String]): IO[ExitCode] =
    IO(println(s"Simulating moods for ${DefaultConfig.cats} cats every ${DefaultConfig.interval} for ${DefaultConfig.duration}...")) >>
      Blocker[IO].use(blocker => {
        recordStream
          .through(csvEncode)
          .through(fs2.io.file.writeAll(DefaultConfig.outputFile, blocker))
          .compile
          .drain
      }) >>
      IO(println(s"Calculating stats for simulated moods...")) >>
      Blocker[IO].use(blocker => {
        fs2.io.file
          .readAll[IO](DefaultConfig.outputFile, blocker, 4096)
          .through(csvDecode)
          .scanMap(c => recordToStat(c))
          .last.evalMap(l => IO(println(l.getOrElse("ERROR")))).compile.drain
      }).as(ExitCode.Success)
}