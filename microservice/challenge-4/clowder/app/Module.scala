import actors.egress.{AggregationResultsActor, SparkStreamingActor}
import actors.ingress.{MoodPublishRouterActor, MoodPublisher, MoodyCatActor, OverlordActor}
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

class Module extends AbstractModule with AkkaGuiceSupport {
  override def configure(): Unit = {
    bindActorFactory[MoodyCatActor, MoodyCatActor.Factory]
    bindActor[OverlordActor]("overlord-actor")
    bindActorFactory[MoodPublisher, MoodPublisher.Factory]
    bindActor[MoodPublishRouterActor]("mood-publish-router")
    bindActor[AggregationResultsActor]("aggregation-results-actor")
    bindActor[SparkStreamingActor]("spark-stream")
  }
}
