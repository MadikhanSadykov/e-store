package com.madikhan.estore.filter;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.exception.*;
import com.madikhan.estore.util.RoutingUtil;
import com.madikhan.estore.util.UrlUtil;
import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ErrorHandlerFilter extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, new ThrowExceptionInsteadOfSendErrorResponse(resp));
        } catch (Throwable th) {
            String requestUrl = req.getRequestURI();
            if (th instanceof ValidationException) {
                LOGGER.warn(REQUEST_NOT_VALID_MESSAGE + th.getMessage());
            } else {
                LOGGER.error("Request " + requestUrl + " failed: " + th.getMessage(), th);
            }
            handleException(requestUrl, th, req, resp);
        }
    }

    private int getStatusCode(Throwable th) {
        if (th instanceof AbstractApplicationException) {
            return ( ((AbstractApplicationException) th).getCode() );
        } else {
            return (HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(String requestUrl, Throwable th, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int statusCode = getStatusCode(th);
        resp.setStatus(statusCode);
        if (UrlUtil.isAjaxJsonUrl(requestUrl)) {
            JSONObject json = new JSONObject();
            json.put(MESSAGE, th instanceof ValidationException ? th.getMessage() : INTERNAL_ERROR);
            RoutingUtil.sendJSON(json, req, resp);
        } else if (UrlUtil.isAjaxHtmlUrl(requestUrl)) {
            RoutingUtil.sendHTMLFragment(INTERNAL_ERROR, req, resp);
        } else {
            req.setAttribute(STATUS_CODE, statusCode);
            RoutingUtil.forwardToPage(ERROR_JSP, req, resp);
        }
    }

    private static class ThrowExceptionInsteadOfSendErrorResponse extends HttpServletResponseWrapper {
        public ThrowExceptionInsteadOfSendErrorResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void sendError(int sc) throws IOException {
            sendError(sc, INTERNAL_ERROR);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            switch (sc) {
                case 403: {
                    throw new AccessDeniedException(msg);
                }
                case 404: {
                    throw new ResourceNotFoundException(msg);
                }
                case 400: {
                    throw new ValidationException(msg);
                }
                default:
                    throw new InternalServerErrorException(msg);
            }
        }
    }
}