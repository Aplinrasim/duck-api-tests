package tests;

import autotests.clients.DeleteClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
@Epic("Тесты duck-action-controller")
@Feature("Удаление уточки через API")
@Story("DELETE /api/duck/delete + проверка в БД")
public class DuckDeleteTest extends DeleteClient {


    @Test(description = "Удаление уточки, предварительно созданной через БД")
    @CitrusTest
    @Step("Тест: Удаление утки, созданной через БД")
    public void testDeleteDuckFromDatabase(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        createDuckInDatabase(runner, "blue", 5.5, "metal", "clack", "ACTIVE");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        deleteDuck(runner, "${duckId}");
        validateDuckDeletedFromDatabase(runner, "${duckId}");
    }
}