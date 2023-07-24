package lerolero;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class LoadTest5000 extends Simulation {

    HttpProtocolBuilder httpProtocol =
        http.baseUrl("http://192.168.0.12:8080")
            .acceptHeader("text/event-stream,application/json;q=0.9,*/*;q=0.8")
            .userAgentHeader(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0"
            );

    Integer wordCount = 100;
    ChainBuilder getAdverbs =
        exec(http("Adverbs").get("/adverbs?size=" + wordCount));

    ChainBuilder getVerbs =
        exec(http("Verbs").get("/verbs?size=" + wordCount));

    ChainBuilder getNouns =
        exec(http("Nouns").get("/nouns?size=" + wordCount));

    ScenarioBuilder users = scenario("Users")
        .during(90).on(exec(getAdverbs, getVerbs, getNouns));

    {
        setUp(
            users.injectOpen(rampUsers(5000).during(30))
        ).protocols(httpProtocol);
    }

}
