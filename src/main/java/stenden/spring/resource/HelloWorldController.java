package stenden.spring.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// We're telling Spring MVC that this is a REST controller
// This treats methods with @RequestMapping as if it had the @ResponseBody annotation
// which tells Spring that the return value should be transformed into a response
@RestController
// The base request mapping this controller listens to, we respond to anything starting with /hello_world
@RequestMapping("/hello_world")
public class HelloWorldController {

    // We have a greeting we read from the properties file using the Spring Expression Language
    @Value("${stenden.greeting}")
    private String greeting;

    /**
     * Finally, your first endpoint! And this is a GET endpoint, as you can see in the annotation
     *
     * @return a message with a greeting
     */
    @RequestMapping(method = RequestMethod.GET)
    public Message helloWorld() {
        return new Message(greeting);
    }

    /**
     * Now let's grab a parameter from the URL
     *
     * @param name
     * @return
     */
    @RequestMapping(path = "/custom", method = RequestMethod.GET)
    public Message customGreeting(@RequestParam("name") String name) {
        return new Message("Hello " + name + "!");
    }

    /**
     * Let's demonstrate an exception
     *
     * @return This method will always fail!
     */
    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public Message failedHelloWorld() {
        throw new GreetingException("I can't be bothered");
    }

    @PostMapping("/uppercase")
    public Message upperCase(@RequestBody @Valid Message message) {
        return new Message(message.getMessage().toUpperCase());
    }
}
