package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DeleteClient extends DuckClient {

    public void deleteDuck(TestCaseRunner runner, String duckId) {
        runner.$(http()
                .client(duckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", duckId));

        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK));
    }
}