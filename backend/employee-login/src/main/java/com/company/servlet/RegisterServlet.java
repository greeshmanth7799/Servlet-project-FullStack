
package com.company.servlet;

import java.io.IOException;
import java.util.Map;

import com.company.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");

        Map<String, String> data;

        try {
            data = mapper.readValue(
                request.getInputStream(),
                new TypeReference<Map<String, String>>() {}
            );
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(response.getWriter(),
                Map.of("message", "Invalid JSON request."));
            return;
        }

        if (data == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(response.getWriter(),
                Map.of("message", "Request body is required."));
            return;
        }

        String result = userService.register(
            data.get("name"),
            data.get("email"),
            data.get("password")
        );

        int status;

        if ("Registration successful.".equals(result)) {
            status = HttpServletResponse.SC_CREATED;
        } else if ("Email is already registered.".equals(result)) {
            status = HttpServletResponse.SC_CONFLICT;
        } else if (result.startsWith("Invalid input")) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        } else {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }

        response.setStatus(status);

        mapper.writeValue(response.getWriter(),
            Map.of("message", result));
    }
}
