package stenden.spring.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hello_world")
public class HelloWorldController {

    @Value("${stenden.greeting}")
    private String greeting;

    @RequestMapping(method = RequestMethod.GET)
    public Message helloWorld() {
        return new Message(greeting);
    }

}
