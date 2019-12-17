/*
 * Copyright 2006-2017 the original author or authors.
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

import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Christoph Deppisch
 */
@Configuration
@PropertySource("citrus.properties")
public class EndpointConfig {

//    @Bean
//    public GlobalVariables globalVariables() {
//        GlobalVariables variables = new GlobalVariables();
//        variables.getVariables().put("project.name", "Citrus Integration Tests");
//        return variables;
//    }

    @Bean
    public HttpClient springAppClient() {
        return CitrusEndpoints
                .http()
                .client()
                .requestUrl("http://localhost:8080")
                .build();
    }
}
