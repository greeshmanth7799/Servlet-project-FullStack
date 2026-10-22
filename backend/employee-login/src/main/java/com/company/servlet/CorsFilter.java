
package com.company.servlet;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/api/*")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
            (HttpServletRequest) request;

        HttpServletResponse httpResponse =
            (HttpServletResponse) response;

        String origin = httpRequest.getHeader("Origin");

        if ("http://localhost:4200".equals(origin)) {
            httpResponse.setHeader(
                "Access-Control-Allow-Origin", origin);
            httpResponse.setHeader(
                "Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader(
                "Vary", "Origin");
        }

        httpResponse.setHeader(
            "Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        httpResponse.setHeader(
            "Access-Control-Allow-Headers", "Content-Type, Accept");

        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(
                HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        chain.doFilter(request, response);
    }
}
