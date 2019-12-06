package stenden.spring.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * In the same manner as {@link ApplicationInitializer},
 * this class also implements {@link WebApplicationInitializer}.
 * This means it is automatically found by the Web Container.
 * <p>
 * Whereas {@link ApplicationInitializer} is for initializing the Spring Dispatcher Servlet and the Application Context,
 * this one is for registering a filter, more specifically an instance of {@link DelegatingFilterProxy} called
 * the springSecurityFilterChain. This acts as a proxy filter
 */
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    // We don't need to implement anything, the superclass is complete. The only reason is left abstract is
    // so it isn't picked up by the Web Container.

}
