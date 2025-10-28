package mk.ukim.finki.wp2025.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp2025.model.User;

import java.io.IOException;

// Filter configured to intercept all requests except those to the login page
@WebFilter(filterName = "auth-filter", urlPatterns = "/*",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD},
        initParams = @WebInitParam(name = "ignore-path", value = "/login")
)
public class LoginFilter implements Filter {
    private String ignorePath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.ignorePath = filterConfig.getInitParameter("ignore-path");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Check if the user is logged in
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        User loggedInUser = (User) req.getSession().getAttribute("user");

        String path = req.getServletPath();

        if (loggedInUser == null && !this.ignorePath.startsWith(path)) {
            // If the user is not logged in and the requested path is not the login page, redirect the user to the login page
            resp.sendRedirect("/login");
        } else {
            // If the user is logged in, continue with the request
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
