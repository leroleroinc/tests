package lerolero;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test1 extends Simulation {

    HttpProtocolBuilder httpProtocol =
        http.baseUrl("http://192.168.0.12:8080")
            .acceptHeader("text/event-stream,application/json;q=0.9,*/*;q=0.8")
            .userAgentHeader(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0"
            );

    Integer wordCount = 100000;
    ChainBuilder getNouns =
        exec(http("Nouns").get("/nouns?size=" + wordCount));

    ScenarioBuilder users = scenario("Users")
        .exec(getNouns);

    {
        setUp(
            users.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }

}
