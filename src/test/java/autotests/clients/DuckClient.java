package autotests.clients;

import autotests.config.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import java.util.Locale;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    protected final String BASE_URL = "http://localhost:2222";
    @Autowired
    protected SingleConnectionDataSource testDb;

    @Step("Выполнение SQL запроса: {sql}")
    public void executeSql(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb)
                .statement(sql));
    }

    @Step("Создание утки в БД с автогенерацией ID и извлечением ID")
    public void createDuckInDatabase(TestCaseRunner runner, String color, double height,
                                     String material, String sound, String wingsState) {
        String sql = String.format(Locale.US,
                "INSERT INTO DUCK (color, height, material, sound, wings_state) VALUES ('%s', %f, '%s', '%s', '%s')",
                color, height, material, sound, wingsState);
        executeSql(runner, sql);
        runner.$(query(testDb)
                .statement("SELECT MAX(id) as id FROM DUCK")
                .extract("id", "duckId"));
    }
    @Step("Удаление утки из БД по id={id}")
    public void deleteDuckFromDb(TestCaseRunner runner, String id) {
        String sql = String.format("DELETE FROM DUCK WHERE id = %s", id);
        executeSql(runner, sql);
    }
    @Step("Проверка, что утка удалена из БД по id={id}")
    public void validateDuckDeletedFromDatabase(TestCaseRunner runner, String id) {
        runner.$(query(testDb)
                .statement(String.format("SELECT COUNT(*) as count FROM DUCK WHERE id = %s", id))
                .validate("COUNT", "0"));
    }
    @Step("Проверка данных утки в БД")
    public void validateDuckInDb(TestCaseRunner runner, String id, String color, String height,
                                 String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement(String.format("SELECT * FROM DUCK WHERE id = %s", id))
                .validate("ID", id)
                .validate("COLOR", color)
                .validate("HEIGHT", height)
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }
    public void createDuckTable(TestCaseRunner runner) {
        executeSql(runner, "DROP TABLE IF EXISTS DUCK");
        String sql = "CREATE TABLE DUCK (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +  // Ключевое изменение!
                "color VARCHAR(255), " +
                "height DOUBLE, " +
                "material VARCHAR(255), " +
                "sound VARCHAR(255), " +
                "wings_state VARCHAR(255)" +
                ")";
        executeSql(runner, sql);
    }

    @Step("Проверка статуса ответа: {status}")
    public void validateStatus(TestCaseRunner runner, org.springframework.http.HttpStatus status) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status));
    }
    @Step("Проверка ответа: {expectedBody}")
    public void validateResponse(TestCaseRunner runner, String expectedBody) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(org.springframework.http.HttpStatus.OK)
                .message()
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
                .body(expectedBody));
    }
    public void validateResponseFromFile(TestCaseRunner runner, String expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayload)));
    }
    public void validateResponseFromPayload(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }
}
