package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.core.io.ClassPathResource;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Locale;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckActionsClient extends DuckClient {


    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    public void duckQuack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }

    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        String heightFormatted = String.format(Locale.US, "%.1f", height);
        String jsonBody = String.format("{\"color\":\"%s\",\"height\":%s,\"material\":\"%s\",\"sound\":\"%s\",\"wingsState\":\"%s\"}",
                color, heightFormatted, material, sound, wingsState);
        System.out.println("Sending JSON: " + jsonBody);

        runner.$(http()
                .client(duckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonBody));
    }

    public void validateCreateDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        String heightFormatted = String.format(Locale.US, "%.1f", height);

        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    public void validateResponse(TestCaseRunner runner, String expectedBody) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(expectedBody));
    }

    public void validateCreateDuckFromFile(TestCaseRunner runner, String filePath) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(filePath)));
    }

    public void updateDuck(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(http()
                .client(duckService)
                .send()
                .put("/api/duck/update")
                .queryParam("id", id)
                .queryParam("color", color)
                .queryParam("height", height)
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState));
    }

    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }

    public void validateStatus(TestCaseRunner runner, HttpStatus status) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status));
    }

    public void validateResponseFromFile(TestCaseRunner runner, String expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayload)));
    }

    public void validateResponseFromPayload(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }
}