package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateTests extends DuckActionsClient {
    @Test(description = "Create duck with material = rubber")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.03, "rubber", "quack", "FIXED");
        validateCreateDuck(runner, "yellow", 0.03, "rubber", "quack", "FIXED");
    }
    @Test(description = "Create duck with material = wood")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.03, "wood", "quack", "FIXED");
        validateCreateDuck(runner, "yellow", 0.03, "wood", "quack", "FIXED");
    }
}