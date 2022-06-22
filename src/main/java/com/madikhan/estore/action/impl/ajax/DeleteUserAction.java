package com.madikhan.estore.action.impl.ajax;

import static com.madikhan.estore.constants.NamesConstants.*;

import com.madikhan.estore.action.Action;
import com.madikhan.estore.exception.AccessDeniedException;
import com.madikhan.estore.model.User;
import com.madikhan.estore.service.OrderService;
import com.madikhan.estore.service.UserService;
import com.madikhan.estore.service.impl.OrderServiceImpl;
import com.madikhan.estore.service.impl.UserServiceImpl;
import com.madikhan.estore.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteUserAction implements Action {

    private final UserService userService = UserServiceImpl.getInstance();
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException, ServletException {
        User user = SessionUtil.getCurrentUser(request);
        if (user.getIsAdmin()) {
            Long userID = Long.valueOf(request.getParameter(USER_ID_ATTRIBUTE));
            Long countOrder = orderService.countUserOrders(userID);
            if (countOrder == null || countOrder == ZERO) {
                userService.delete(userID);
            } else {
                throw new Error();
            }
        } else {
            throw new AccessDeniedException(YOU_ARE_NOT_ADMIN_MESSAGE);
        }
    }
}
