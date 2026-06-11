package autotests.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class MessageResponse {

    @JsonProperty
    private String message;
    public MessageResponse() {
    }
    public MessageResponse(String message) {
        this.message = message;
    }

}