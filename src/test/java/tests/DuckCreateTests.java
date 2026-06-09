package tests;


import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.DuckActionsClient;
import autotests.payloads.CreateDuckResponse;
import autotests.payloads.CreateDuckRequest;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckCreateTests extends DuckActionsClient {


    @Test(description = "Создание уточки с material = rubber, валидация по строке")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.03, "rubber", "quack", "FIXED");
        validateCreateDuck(runner, "yellow", 0.03, "rubber", "quack", "FIXED");
    }

    @Test(description = "Создание уточки с material = rubber, валидация из файла")
    @CitrusTest
    public void createRubberDuckFile(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 10.0, "rubber", "quack", "UNDEFINED");
        validateCreateDuckFromFile(runner, "responses/createDuckResponse.json");
    }
    @Test(description = "Создание уточки с с material = wood, валидация через модели Request + Response ")
    @CitrusTest
    public void createRubberDuckWithModels(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckRequest request = new CreateDuckRequest()
                .color("yellow")
                .height(0.03)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");

        runner.$(http()
                .client(BASE_URL)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType("application/json")
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(request, new ObjectMapper())));

        CreateDuckResponse expectedResponse = new CreateDuckResponse()
                .id("@isNumber()@")
                .color("yellow")
                .height(0.03)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");

        runner.$(http()
                .client(BASE_URL)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedResponse, new ObjectMapper())));
    }
}