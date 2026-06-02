package tests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import clients.DuckActionsClient;

public class DuckQuackTests extends DuckActionsClient {

    @Test(description = "Odd id - sound quack")
    @CitrusTest
    public void testQuackWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "1", "1", "1");
        validateResponse(runner, "{\"sound\": \"quack\"}");
    }

    @Test(description = "Even id - sound meow")
    @CitrusTest
    public void testQuackWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner, "2", "1", "1");
        validateResponse(runner, "{\"sound\": \"moo\"}");
    }
}