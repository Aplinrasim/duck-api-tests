package autotests;

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
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {
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
    public void validateStatus(TestCaseRunner runner, org.springframework.http.HttpStatus status) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status));
    }
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

    public void sendGetMethod (TestCaseRunner runner, String path, HttpClient httpClient) {
        runner.$(http()
                .client(httpClient)
                .send()
                .get(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    public void sendPostMethod (TestCaseRunner runner, String path, String jsonBody, HttpClient httpClient){
        runner.$(http()
                .client(httpClient)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonBody));
    }
}
