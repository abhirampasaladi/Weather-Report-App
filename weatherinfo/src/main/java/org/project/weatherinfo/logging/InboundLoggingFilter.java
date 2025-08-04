package org.project.weatherinfo.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class InboundLoggingFilter extends FilterRegistrationBean<InboundLoggingFilter> implements Filter {

    InboundLoggingFilter() {
        super.setFilter(this);
        super.addUrlPatterns("/*");
        super.setName("InboundLoggingFilter");
        super.setOrder(-99999999);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            log.info("Inbound HTTP Request Received - URI: {}, Method: {}, URL: {}",
                    httpRequest.getRequestURI(),
                    httpRequest.getMethod(),
                    httpRequest.getRequestURL());
        } else {
            log.info("Inbound Request Received: Non-HTTP request");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("Inbound Request Processing Ended");
        Filter.super.destroy();
    }
}
