package ro.ubb.assetsJSP.Controller;

import ro.ubb.assetsJSP.DB.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginController extends HttpServlet {
    public LoginController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String password = request.getParameter("password");

        DBManager dbManager = new DBManager();
        if(dbManager.login(user,password)) {

            request.getSession().setAttribute("username", user);
            request.getSession().setAttribute("password", password);

            response.sendRedirect("user.jsp");
        }
        else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('invalid credentials');");
            out.println("window.location.replace('index.jsp');");
            out.println("</script>");
//            response.sendRedirect("index.jsp");
        }
    }
}
