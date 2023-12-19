package servlet;

import dbHandler.MenuDbService;
import dbHandler.OrderDbService;
import entity.Order;
import exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/showOrdersForAdmin")
public class ShowOrdersServlet extends HttpServlet {
    private static Logger logger = LogManager.getLogger();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        logger.info(session.getAttribute("name"));
        logger.info(session.getAttribute("password"));
        try {
            ResultSet resultSet = OrderDbService.showAllOrdersForAdmin();
            List<Order> finalResList = new ArrayList<>();
            while (resultSet.next()){

                logger.info(resultSet.getInt(1));
                logger.info(resultSet.getInt(2));
                logger.info(resultSet.getInt(3));
                logger.info(resultSet.getInt(4));


                Order order = new Order(resultSet.getInt(1), MenuDbService.getNameById(resultSet.getInt(2)), resultSet.getInt(3),resultSet.getInt(4) );
                finalResList.add(order);

            }
            session.setAttribute("ResultList",finalResList);
            resp.sendRedirect("/jsp/ordersListForAdmin.jsp");
        } catch (CustomException e) {
            logger.error(e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
