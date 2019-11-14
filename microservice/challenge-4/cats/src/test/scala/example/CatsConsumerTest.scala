package example
import org.scalatest._

class CatsConsumerSpec extends FlatSpec {
  "Cats Consumer" should "give the mean/median/variance of moods" in {
    val catsConsumer = new CatsConsumerKafka
    catsConsumer.generateStatistics() // generateStatistics() doesn't do anything except for logging to console
    assert(true)
  }

  "Data Store" should "accumulate new data without overriding" in {
    val inMemoryStore = new InMemoryStore
    val map1 = Map("A" -> 20, "B" -> 20)
    val map2 = Map("B" -> 10, "C" -> 10)
    val map3 = Map("C" -> 5, "D" -> 5)
    inMemoryStore.store(map1)
    inMemoryStore.store(map2)
    inMemoryStore.store(map3)
    val a = inMemoryStore.storedMoodMap("A") == 20
    val b = inMemoryStore.storedMoodMap("B") == 30
    val c = inMemoryStore.storedMoodMap("C") == 15
    val d = inMemoryStore.storedMoodMap("D") == 5
    for (cond <- Seq(a,b,c,d)){
      assert(cond)
    }
  }

  /* Still unsure at the moment how to actually write a Kafka Consumer/Producer unit test/integration test.
   * However, my idea would be to test the following functionality:
   *  1. Unit test the producer to ensure that the the messages sent match the history of sent messages.
   *  2. Unit test the consumer to ensure that the messages received match some mock sent messages.
   *  3. Test that given some fixed mock data, CatsConsumerKafka.moodMap is appropriately updated.
   *  4. Integration test to ensure that a message sent by the producer is received by the consumer
   */
}