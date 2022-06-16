package ro.ubb.ordersJSP.Controller;

import ro.ubb.ordersJSP.Domain.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CancelOrderController extends HttpServlet {
    public CancelOrderController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Order> order = new ArrayList<>();
        request.getSession().setAttribute("partialOrder", order);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('order cancelled');");
        out.println("window.location.replace('displayProducts.jsp');");
        out.println("</script>");

    }
}
