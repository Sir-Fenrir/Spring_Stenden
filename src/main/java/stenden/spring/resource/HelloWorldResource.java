package stenden.spring.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// We're telling Spring MVC that this is a REST controller
// This treats methods with @RequestMapping as if it had the @ResponseBody annotation
// which tells Spring that the return value should be transformed into a response
@RestController
// The base request mapping this controller listens to, we respond to anything starting with /hello_world
@RequestMapping("/hello_world")
public class HelloWorldResource {

  // We have a greeting we read from the properties file using the Spring Expression Language
  @Value("${stenden.greeting}")
  private String greeting;

  private JmsTemplate jmsTemplate;

  @Autowired
  public HelloWorldResource(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  /**
   * Finally, your first endpoint! And this is a GET endpoint, as you can see in the annotation
   *
   * @return a message with a greeting
   */
  @GetMapping
  public Message helloWorld() {
    Message message = new Message(greeting);
    jmsTemplate.convertAndSend(message);
    return message;
  }

  /**
   * Now let's grab a parameter from the URL
   *
   * @param name
   * @return
   */
  @GetMapping("/custom")
  public Message customGreeting(@RequestParam("name") String name) {
    return new Message("Hello " + name + "!");
  }

  /**
   * Let's demonstrate an exception
   *
   * @return This method will always fail!
   */
  @GetMapping("/error")
  public Message failedHelloWorld() {
    throw new GreetingException("I can't be bothered");
  }

}
