package org.project.weatherinfo;

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
        log.info(">>>>>>>>>>>>>> Inbound Logging Request <<<<<<<<<<<<<");
        if (request instanceof HttpServletRequest httpRequest) {
            log.info("request Uri: {}",httpRequest.getRequestURI());
            log.info("request Method: {}",httpRequest.getMethod());
            log.info("request URL: {}",httpRequest.getRequestURL());
        }

        log.info(">>>>>>>>>>>>>>>>> Inbound Logging Request <<<<<<<<<<<<<<<");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("-----------In Inbound Logging Request-Ended-------------");
        Filter.super.destroy();
    }
}
