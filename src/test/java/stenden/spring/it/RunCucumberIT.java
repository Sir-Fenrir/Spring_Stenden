package stenden.spring.it;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"com.consol.citrus.cucumber.CitrusReporter"},
        features = "classpath:it")
public class RunCucumberIT {
}
