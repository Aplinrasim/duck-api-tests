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

public class DuckFlyTests extends TestNGCitrusSupport {

    @Test(description = "Существующий id с активными крыльями (ACTIVE) - утка летит")
    @CitrusTest
    public void testFlyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.run(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", "10"));

        runner.run(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"message\": \"I am flying :)\"}"));
    }

    @Test(description = "Существующий id со связанными крыльями (FIXE) - утка не летит")
    @CitrusTest
    public void testFlyWithTiedWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.run(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", "14"));

        runner.run(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"message\": \"I can not fly :C\"}"));
    }

    @Test(description = "Существующий id с крыльями в неопределенном состоянии (UNDEFINED) - ошибка")
    @CitrusTest
    public void testFlyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.run(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", "12"));

        runner.run(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"message\": \"Wings are not detected :(\"}"));
    }
}