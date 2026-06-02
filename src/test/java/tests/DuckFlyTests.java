package tests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DuckActionsClient;
import autotests.payloads.MessageResponse;

public class DuckFlyTests extends DuckActionsClient {


    @Test(description = "Активные крылья, валидация из responses")
    @CitrusTest
    public void testFlyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        duckFly(runner, "10");
        validateResponseFromFile(runner, "responses/getDuckFlyTest/flyActiveWings.json");
    }

    @Test(description = "Неопределенные крылья, валидация из файла")
    @CitrusTest
    public void testFlyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        duckFly(runner, "13");
        MessageResponse expectedResponse = new MessageResponse("Wings are not detected :(");
        validateResponseFromPayload(runner, expectedResponse);
    }

    @Test(description = "Фиксированные крылья, валидация по String")
    @CitrusTest
    public void testFlyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        duckFly(runner, "14");
        validateResponse(runner, "{\"message\": \"I can not fly :C\"}");
    }
}