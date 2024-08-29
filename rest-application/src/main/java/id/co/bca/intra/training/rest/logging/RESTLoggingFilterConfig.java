package id.co.bca.intra.training.rest.logging;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
@WebFilter("/*")
public class RESTLoggingFilterConfig implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RESTLoggingFilterConfig.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Instant start = Instant.now();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            Instant finish = Instant.now();
            long time = Duration.between(start, finish).toMillis();

            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            LOGGER.debug("URL: {} | METHOD: {} | STATUS: {} | DURATION: {} ms ", request.getRequestURI(), request.getMethod(), response.getStatus(), time);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
