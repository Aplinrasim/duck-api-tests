package tests;

import autotests.payloads.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DuckActionsClient;

public class DuckUpdateTests extends DuckActionsClient {

    @Test(description = "Изменение цвета и размера уточки")
    @CitrusTest
    public void testUpdateDuckColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        updateDuck(runner, "3", "red", "15.0", "wood", "quack", "ACTIVE");
        validateResponse(runner, "{\"message\": \"Duck with id = 3 is updated\"}");
    }

    @Test(description = "Изменение цвета и звука уточки, валидация через модель MessageResponse")
    @CitrusTest
    public void testUpdateDuckColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        updateDuck(runner, "5", "green", "5.0", "wood", "meow", "ACTIVE");
        MessageResponse expectedResponse = new MessageResponse("Duck with id = 5 is updated");
        validateResponseFromPayload(runner, expectedResponse);
    }
}