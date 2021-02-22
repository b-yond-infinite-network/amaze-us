package simulation

import java.io.File
import scala.concurrent.duration.DurationInt

// TODO: Refactor into TOML
object DefaultConfig {
  val cats = 1000

  val interval = 2.seconds
  val duration = 10.seconds

  val outputFile = new File("records.csv").toPath
}
