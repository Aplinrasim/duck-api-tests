package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class PropertiesClient extends DuckClient {

    @Step("Получение характеристик утки с ID: {id}")
    public void duckProperties(TestCaseRunner runner, String id) {
        String path = "/api/duck/action/properties?id=" + id;
        sendGetMethod(runner, path, duckService);
    }
}