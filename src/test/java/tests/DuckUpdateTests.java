package tests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import clients.DuckActionsClient;

public class DuckUpdateTests extends DuckActionsClient {

    @Test(description = "Update duck color and height")
    @CitrusTest
    public void testUpdateDuckColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        updateDuck(runner, "3", "red", "15.0", "wood", "quack", "ACTIVE");
        validateResponse(runner, "{\"message\": \"Duck with id = 3 is updated\"}");
    }

    @Test(description = "Update duck color and sound")
    @CitrusTest
    public void testUpdateDuckColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        updateDuck(runner, "3", "green", "5.0", "wood", "meow", "ACTIVE");
        validateResponse(runner, "{\"message\": \"Duck with id = 3 is updated\"}");
    }
}