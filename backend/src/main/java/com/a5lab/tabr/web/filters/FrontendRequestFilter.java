package com.a5lab.tabr.web.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FrontendRequestFilter extends OncePerRequestFilter {

    private static final String INDEX_HTML = "/index.html";
    private static final String API_PREFIX = "/api";
    private final String indexHtmlPath;
    private final String apiPath;

    public FrontendRequestFilter(@Value("${server.servlet.context-path:}") final String contextPath) {
        this.indexHtmlPath = contextPath + INDEX_HTML;
        this.apiPath = contextPath + API_PREFIX;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isHtmlRequest(request) && !isAPI(request)) {
            final HttpServletRequest mutatedRequest = mutateRequestToIndexPage(request);
            filterChain.doFilter(mutatedRequest, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
    private boolean isHtmlRequest(HttpServletRequest request) {
        final String acceptHeader = request.getHeader("Accept");
        return acceptHeader != null && acceptHeader.contains(MediaType.TEXT_HTML_VALUE);
    }
    private boolean isAPI(HttpServletRequest request) {
        return request.getRequestURI().startsWith(apiPath);
    }

    private HttpServletRequest mutateRequestToIndexPage(HttpServletRequest request) {
        return new HttpServletRequestWrapper(request) {
            @Override
            public String getRequestURI() {
                return indexHtmlPath;
            }
        };
    }

}
