package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.JsonPathSupport;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
public class DuckSwimTest extends TestNGCitrusSupport {

    @Test(description = "Существующий id - утка пытается плыть")
    @CitrusTest
    public void testSwimWithExistingId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"color\":\"yellow\",\"height\":10.0,\"material\":\"rubber\",\"sound\":\"quack\",\"wingsState\":\"UNDEFINED\"}"));

        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .extract(JsonPathSupport.jsonPath().expression("$.id", "duckId")));

        runner.run(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", "${duckId}"));

        runner.run(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.NOT_FOUND));
        // Баг. Приходит статус код 404, по факту выдает сообщение body("{\"message\": \"Paws are not found ((((\"}"));
    }

    @Test(description = "Несуществующий id - ошибка")
    @CitrusTest
    public void testSwimWithNonExistingId(@Optional @CitrusResource TestCaseRunner runner) {

        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"color\":\"yellow\",\"height\":10.0,\"material\":\"rubber\",\"sound\":\"quack\",\"wingsState\":\"UNDEFINED\"}"));

        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .extract(JsonPathSupport.jsonPath().expression("$.id", "duckId")));

        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", "${duckId}"));

        runner.run(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK));

    runner.run(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", "${duckId}"));

        runner.run(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.NOT_FOUND));
        // Баг. Приходит верный статус код 404,но еще и выдает сообщение в body("{\"message\": \"Paws are not found ((((\"}"));
    }
}