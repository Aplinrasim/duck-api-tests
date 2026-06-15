package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckDeleteTests extends DuckActionsClient {

    @Test(description = "Delete existing duck")
    @CitrusTest
    public void deleteExistingDuck(@Optional @CitrusResource TestCaseRunner runner) {
        String duckId = "2";
        deleteDuck(runner, duckId);
    }
}