package no.vejmon.dommern.view;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class AdviseConfig {

    @ExceptionHandler(ServletException.class)
    public Object handleServletException(ServletException ex, HttpServletRequest request) {
        return handleNotFound(request);
    }

    private Object handleNotFound(HttpServletRequest request) {
        boolean isGet = "GET".equalsIgnoreCase(request.getMethod());
        String accept = request.getHeader("Accept");
        boolean wantsHtml = accept != null && accept.contains("text/html");

        if (isGet && wantsHtml) {
            ModelAndView mav = new ModelAndView("forward:/index.html");
            mav.setStatus(HttpStatus.OK);
            return mav;
        }
        return null;
    }
}
