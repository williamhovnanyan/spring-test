package beans.aspects;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public ModelAndView handleIOException(HttpServletRequest req,
                                          Exception e) {
        logger.log(Level.SEVERE, "Exception handler executed");
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", e);
        mav.addObject("request_url", req.getRequestURI());
        return mav;
    }
}
