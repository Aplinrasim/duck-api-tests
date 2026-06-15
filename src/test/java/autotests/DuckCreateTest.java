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

public class DuckCreateTest extends TestNGCitrusSupport {

    @Test(description = "Создать утку с material = rubber")
    @CitrusTest
    public void testCreateDuckWithRubberMaterial(@Optional @CitrusResource TestCaseRunner runner) {
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
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"id\":\"@isNumber()@\",\"color\":\"yellow\",\"height\":10.0,\"material\":\"rubber\",\"sound\":\"quack\",\"wingsState\":\"UNDEFINED\"}"));
    }

    @Test(description = "Создать утку с material = wood")
    @CitrusTest
    public void testCreateDuckWithWoodMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"color\":\"blue\",\"height\":0.5,\"material\":\"wood\",\"sound\":\"quack\",\"wingsState\":\"ACTIVE\"}"));

        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"id\":\"@isNumber()@\",\"color\":\"blue\",\"height\":0.5,\"material\":\"wood\",\"sound\":\"quack\",\"wingsState\":\"ACTIVE\"}"));
    }
}