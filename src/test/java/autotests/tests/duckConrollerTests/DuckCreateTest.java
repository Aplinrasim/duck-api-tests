package autotests.tests.duckConrollerTests;

import autotests.clients.CreateClient;
import autotests.payloads.CreateDuckRequest;
import autotests.payloads.CreateDuckResponse;
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
@Feature("Создание уточки через API")
@Story("POST /api/duck/create + проверка в БД")
public class DuckCreateTest extends CreateClient {

    @Test(description = "Создать утку с материалом wood через API, проверить в БД")
    @CitrusTest
    @Step("Тест: Создание утки с материалом wood и проверка в БД")
    public void duckCreateTest(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckAndExtractId(runner, "red", 3.4, "wood", "quack", "ACTIVE");
        runner.$(doFinally().actions(context -> deleteDuckFromDb(runner, "${duckId}")));
        validateDuckInDb(runner, "${duckId}", "red", "3.4", "wood", "quack", "ACTIVE");
    }

    @Test(description = "Создание уточки с material = rubber, валидация по строке")
    @CitrusTest
    @Step("Тест: Создание резиновой утки с валидацией по строке")
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.03, "rubber", "quack", "FIXED");
        validateCreateDuck(runner, "yellow", 0.03, "rubber", "quack", "FIXED");
    }

    @Test(description = "Создание уточки с material = rubber, валидация из файла")
    @CitrusTest
    @Step("Тест: Создание резиновой утки с валидацией из файла")
    public void createRubberDuckFile(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 10.0, "rubber", "quack", "UNDEFINED");
        validateResponseFromFile(runner, "responses/createDuckResponse.json");
    }
    @Test(description = "Создание уточки с material = wood, валидация через модели Request + Response")
    @CitrusTest
    @Step("Тест: Создание деревянной утки с валидацией через модели")
    public void createRubberDuckWithModels(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckRequest request = new CreateDuckRequest()
                .color("yellow")
                .height(0.03)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");
        createDuckWithModel(runner, request);
        CreateDuckResponse expectedResponse = new CreateDuckResponse()
                .id("@isNumber()@")
                .color("yellow")
                .height(0.03)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");
        validateResponseFromPayload(runner, expectedResponse);
    }
}