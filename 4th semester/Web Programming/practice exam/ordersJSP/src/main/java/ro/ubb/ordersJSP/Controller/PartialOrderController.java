package ro.ubb.ordersJSP.Controller;

import ro.ubb.ordersJSP.Domain.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartialOrderController extends HttpServlet {
    private List<Order> order;
    public PartialOrderController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("username");
        String product = request.getParameter("productName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        order = (ArrayList<Order>) request.getSession().getAttribute("partialOrder");

        order.add(new Order(user, product, quantity));

        System.out.println("order size: " + order.size());

        request.getSession().setAttribute("partialOrder", order);

        response.sendRedirect("displayProducts.jsp");

    }
}
