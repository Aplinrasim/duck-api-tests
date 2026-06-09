package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class UpdateClient extends DuckClient {

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
}