package ro.ubb.ordersJSP.Controller;

import ro.ubb.ordersJSP.DB.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DisplayProductsController extends HttpServlet {
    public DisplayProductsController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("startsWith");

        request.getSession().setAttribute("startsWith", text);

        response.sendRedirect("displayProducts.jsp");

    }
}
