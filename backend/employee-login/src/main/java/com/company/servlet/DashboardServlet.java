
package com.company.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            mapper.writeValue(response.getWriter(),
                Map.of("message", "Please log in to continue."));

            return;
        }

        Map<String, Object> safeUser = new HashMap<>();
        safeUser.put("id", session.getAttribute("userId"));
        safeUser.put("name", session.getAttribute("userName"));
        safeUser.put("email", session.getAttribute("userEmail"));

        Map<String, Object> result = new HashMap<>();
        result.put("message", "Dashboard loaded successfully.");
        result.put("user", safeUser);

        response.setStatus(HttpServletResponse.SC_OK);
        mapper.writeValue(response.getWriter(), result);
    }
}
