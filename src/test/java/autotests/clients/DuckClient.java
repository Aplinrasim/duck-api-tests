package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Locale;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckClient extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient duckService;

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

    public void validateStatus(TestCaseRunner runner, HttpStatus status) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status));
    }
}
