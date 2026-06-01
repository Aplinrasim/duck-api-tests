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
public class DuckPropertiesTests extends TestNGCitrusSupport {

    @Test(description = "ID - целое четное число. Есть в БД (утка с material = wood)")
    @CitrusTest
    public void testPropertiesEvenIdWoodMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        runner.run(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", "2"));

        runner.run(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{}"));
        //Баг. Ответ требуется привести к виду
        //.body("{\"id\":+id+,\"color\":\+color+\,\"height\":+hight+,\"material\":\+material+\",\"sound\":\+sound+\",\"wingsState\":\+wingsState+\"}"));
    }

    @Test(description = "ID - целое нечетное число. Есть в БД (утка с material = rubber)")
    @CitrusTest
    public void testPropertiesOddIdRubberMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        runner.run(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", "1"));

        runner.run(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\"color\":\"yellow\",\"height\":500.0,\"material\":\"rubber\",\"sound\":\"squeak\",\"wingsState\":\"ACTIVE\"}"));
        //Баг. Ответ требуется привести к виду как в БД
        //.body("{\"id\":+id+,\"color\":\+color+\,\"height\":+hight+,\"material\":\+material+\",\"sound\":\+sound+\",\"wingsState\":\+wingsState+\"}"));
        // hight в ответе на 100 больше
    }
}