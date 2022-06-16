package ro.ubb.ordersJSP.Controller;

import ro.ubb.ordersJSP.DB.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class AddProductController extends HttpServlet {
    public AddProductController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("product");
        String description = request.getParameter("description");

        DBManager dbManager = new DBManager();

        if(dbManager.addProduct(name, description)) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('product added');");
            out.println("window.location.replace('user.jsp');");
            out.println("</script>");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('error');");
            out.println("window.location.replace('user.jsp');");
            out.println("</script>");
        }
    }
}
