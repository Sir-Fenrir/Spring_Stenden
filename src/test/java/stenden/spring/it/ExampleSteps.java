/*
 * Copyright 2006-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stenden.spring.it;

import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.dsl.runner.TestRunner;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpStatus;

/**
 * designer
 *
 * @author Christoph Deppisch
 */
public class ExampleSteps {

    /**
     * Test runner filled with actions by step definitions
     */
    @CitrusResource
    private TestRunner runner;

    @When("^the client makes a GET request to \"([^\"]*)\"$")
    public void callUrl(String url) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client("springAppClient")
                .send()
                .get(url));
    }

    @Then("^the HTTP status code should be (\\d+)$")
    public void expectedHttpStatus(int status) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client("springAppClient")
                .receive()
                .response(HttpStatus.valueOf(status)));
    }

}
