package autotests.tests.duckConrollerTests;

import autotests.clients.UpdateClient;
import autotests.payloads.MessageResponse;
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
@Feature("Обновление характеристик уточки")
@Story("PUT /api/duck/update + проверка в БД")
public class DuckUpdateTest extends UpdateClient {

    @Test(description = "Изменение цвета и размера уточки")
    @CitrusTest
    @Step("Тест: Обновление цвета и размера утки")
    public void testUpdateDuckColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        runner.variable("duckId", "3");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        executeSql(runner,
                "INSERT INTO DUCK (id, color, height, material, sound, wings_state) " +
                        "VALUES (${duckId}, 'yellow', 5.0, 'wood', 'quack', 'FIXED')");
        validateDuckInDb(runner, "3", "yellow", "5.0", "wood", "quack", "FIXED");
        updateDuck(runner, "3", "red", "15.0", "wood", "quack", "ACTIVE");
        validateResponse(runner, "{\"message\": \"Duck with id = 3 is updated\"}");
        validateDuckInDb(runner, "3", "red", "15.0", "wood", "quack", "ACTIVE");
    }
    @Test(description = "Изменение цвета и звука уточки, валидация через модель MessageResponse")
    @CitrusTest
    @Step("Тест: Обновление цвета и звука утки с валидацией через модель")
    public void testUpdateDuckColorAndSound(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckTable(runner);
        runner.variable("duckId", "5");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        executeSql(runner,
                "INSERT INTO DUCK (id, color, height, material, sound, wings_state) " +
                        "VALUES (${duckId}, 'blue', 5.0, 'wood', 'quack', 'FIXED')");
        validateDuckInDb(runner, "5", "blue", "5.0", "wood", "quack", "FIXED");
        updateDuck(runner, "5", "green", "5.0", "wood", "meow", "ACTIVE");
        MessageResponse expectedResponse = new MessageResponse("Duck with id = 5 is updated");
        validateResponseFromPayload(runner, expectedResponse);
        validateDuckInDb(runner, "5", "green", "5.0", "wood", "meow", "ACTIVE");
    }
}