package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckUpdateTests extends TestNGCitrusSupport {

    @Test(description = "Update duck color and height")
    @CitrusTest
    public void testUpdateDuckColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .put("/api/duck/update")
                .queryParam("id", "2")
                .queryParam("color", "red")
                .queryParam("height", "15.0")
                .queryParam("material", "wood")
                .queryParam("sound", "quack")
                .queryParam("wingsState", "ACTIVE"));

        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"message\": \"Duck with id = 2 is updated\"}"));
    }

    @Test(description = "Update duck color and sound")
    @CitrusTest
    public void testUpdateDuckColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .put("/api/duck/update")
                .queryParam("id", "2")
                .queryParam("color", "green")
                .queryParam("height", "5.0")
                .queryParam("material", "wood")
                .queryParam("sound", "meow")
                .queryParam("wingsState", "ACTIVE"));

        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"message\": \"Duck with id = 2 is updated\"}"));
    }
}