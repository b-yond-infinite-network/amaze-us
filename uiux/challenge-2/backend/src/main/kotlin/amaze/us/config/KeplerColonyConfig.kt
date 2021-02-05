package amaze.us.config

import com.google.common.base.Predicates
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
internal class KeplerColonyConfig {
  @Bean
  fun barringControlApi(): Docket = Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
      .apiInfo(apiInfo()).select()
      .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
      .build()

  fun apiInfo() = ApiInfo("Kepler Colony API", "This api manage the Colony population via the approval or denial of baby requests", "v1",
      "Terms of service", Contact("Team", "", ""), "", "")
}

val LOGGER: Logger = LoggerFactory.getLogger("Kepler-Colony")