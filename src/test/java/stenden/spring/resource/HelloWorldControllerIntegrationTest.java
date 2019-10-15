package stenden.spring.resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import stenden.spring.configuration.WebConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringJUnitWebConfig(classes = WebConfig.class)
public class HelloWorldControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testHelloWorld() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/hello_world")).andReturn();
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "{\"message\":\"Hello Stenden Students!\"}");
    }
}
