package tests;

import autotests.clients.SwimClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
@Epic("Тесты duck-action-controller")
@Feature("Команда плыть уточки")
@Story("GET /api/duck/action/swim + проверка в БД")
public class DuckSwimTest extends SwimClient {

    @Test(description = "Команда плыть - уточка есть в БД")
    @CitrusTest
    @Step("Тест: Плавание существующей утки (ожидаем NOT_FOUND из-за бага)")
    public void testSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        createDuckInDatabase(runner, "orange", 3.0, "cheese", "hrum", "ACTIVE");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        duckSwim(runner, "${duckId}");
        validateStatus(runner, HttpStatus.NOT_FOUND);
    }
    @Test(description = "Команда плыть - уточка удалена из БД")
    @CitrusTest
    @Step("Тест: Плавание удалённой из БД утки (ожидаем NOT_FOUND)")
    public void testSwimWithDeletedDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        createDuckInDatabase(runner, "orange", 3.0, "cheese", "hrum", "ACTIVE");
        String duckId = "${duckId}";
        deleteDuckFromDb(runner, duckId);
        validateDuckDeletedFromDatabase(runner, duckId);
        duckSwim(runner, duckId);
        validateStatus(runner, HttpStatus.NOT_FOUND);
    }

}