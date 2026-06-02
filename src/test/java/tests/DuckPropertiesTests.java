package tests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckPropertiesResponse;

public class DuckPropertiesTests extends DuckActionsClient {

    @Test(description = "Характеристики уточки с четным id и материалом wood")
    @CitrusTest
    public void testPropertiesEvenIdWoodMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        duckProperties(runner, "8");
        validateResponse(runner, "{}");
    }

    @Test(description = "Характеристики уточки с нечетным id и материалом rubber, валидация из файла")
    @CitrusTest
    public void testPropertiesOddIdRubberMaterial(@Optional @CitrusResource TestCaseRunner runner) {
        duckProperties(runner, "7");
        validateResponseFromFile(runner, "responses/getDuckPropertiesTest/duckPropertiesRubber.json");
    }
    @Test(description = "Характеристики уточки с нечетным id и материалом rubber, валидация из payloads")
    @CitrusTest
    public void testPropertiesOddIdRubberMaterialPayload(@Optional @CitrusResource TestCaseRunner runner) {
        duckProperties(runner, "7");

        DuckPropertiesResponse expectedResponse = new DuckPropertiesResponse(
                "yellow", 1000.0, "rubber", "quack", "UNDEFINED"
        );

        validateResponseFromPayload(runner, expectedResponse);
    }
}