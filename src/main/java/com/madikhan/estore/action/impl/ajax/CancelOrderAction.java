package com.madikhan.estore.action.impl.ajax;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.OrderService;
import com.madikhan.estore.service.impl.OrderServiceImpl;
import com.madikhan.estore.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

public class CancelOrderAction implements Action {

    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {

        Long orderID = Long.valueOf(request.getParameter(ORDER_ID_ATTRIBUTE));
        User user = SessionUtil.getCurrentUser(request);
        if (user.getIsAdmin()) {
            orderService.updateStatus(orderID, CANCELED_STATUS_ID);
        } else {
            throw new AccessDeniedException(YOU_ARE_NOT_ADMIN_MESSAGE);
        }
    }
}
