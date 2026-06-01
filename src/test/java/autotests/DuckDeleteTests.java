package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckDeleteTests extends TestNGCitrusSupport {

    @Test(description = "Удалить утку")
    @CitrusTest
    public void testDeleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        runner.run(http()
                .client("http://localhost:2222")
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", "4"));

        runner.run(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK));
    }
}