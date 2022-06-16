package ro.ubb.subiect927JSP.Controller;

import ro.ubb.subiect927JSP.DB.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddKeywordController extends HttpServlet {
    public AddKeywordController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("keyAdd");
        String value = request.getParameter("valueAdd");

        DBManager dbManager = new DBManager();

        if (dbManager.addKeyword(key, value)) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('keyword added');");
            out.println("window.location.replace('index.jsp');");
            out.println("</script>");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('error');");
            out.println("window.location.replace('index.jsp');");
            out.println("</script>");
        }
    }
}
