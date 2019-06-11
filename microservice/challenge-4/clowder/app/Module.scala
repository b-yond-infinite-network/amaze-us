import actors.egress.{SparkStreamingActor, AggregationResultsActor}
import actors.ingress.{MoodPublishRouterActor, MoodPublisher, MoodyCatActor, OverlordActor}
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport
import services.SystemSchedular

class Module extends AbstractModule with AkkaGuiceSupport {
  override def configure(): Unit = {
    bindActorFactory[MoodyCatActor, MoodyCatActor.Factory]
    bindActor[OverlordActor]("overlord-actor")
    bindActorFactory[MoodPublisher, MoodPublisher.Factory]
    bindActor[MoodPublishRouterActor]("mood-publish-router")
    bindActor[AggregationResultsActor]("aggregation-results-actor")
    bindActor[SparkStreamingActor]("spark-stream")
    bind(classOf[SystemSchedular]).asEagerSingleton()
  }
}
