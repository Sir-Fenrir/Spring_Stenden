package stenden.spring.resource;

import javax.validation.constraints.Size;

public class Message {

    @Size(max = 20)
    private String message;

    public Message(String message) {
        this.message = message;
    }

    // The empty constructor is used by Jackson
    // To deserialize it first constructs an object using this
    // and then populates it by using setters
    public Message() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
