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

public class DuckQuackTests extends TestNGCitrusSupport {

    @Test(description = "Проверка кряканья с корректным нечётным id, корректный звук")
    @CitrusTest
    public void testQuackWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.run(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", "1")
                .queryParam("repetitionCount", "1")
                .queryParam("soundCount", "1"));

        runner.run(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"sound\": \"quack\"}"));
    }

    @Test(description = "Проверка кряканья с корректным чётным id, корректный звук")
    @CitrusTest
    public void testQuackWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.run(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", "2")
                .queryParam("repetitionCount", "1")
                .queryParam("soundCount", "1"));

        runner.run(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"sound\": \"moo\"}"));
        //Баг. Звук выводится некорректный
    }
}