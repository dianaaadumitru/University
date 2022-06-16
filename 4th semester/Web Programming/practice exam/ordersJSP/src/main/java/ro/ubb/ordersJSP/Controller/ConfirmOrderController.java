package ro.ubb.ordersJSP.Controller;

import ro.ubb.ordersJSP.DB.DBManager;
import ro.ubb.ordersJSP.Domain.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderController extends HttpServlet {
    public ConfirmOrderController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> order = (ArrayList<Order>) request.getSession().getAttribute("partialOrder");

        System.out.println("orders from final step: " + order.size());

        DBManager dbManager = new DBManager();

        if (dbManager.addOrder(order)) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('orders added');");
            out.println("window.location.replace('displayProducts.jsp');");
            out.println("</script>");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('error');");
            out.println("window.location.replace('displayProducts.jsp');");
            out.println("</script>");
        }

    }
}
