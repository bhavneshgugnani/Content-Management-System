package processing;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class logoutaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public logoutaction() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.getSession().removeAttribute("user");
	request.getSession().removeAttribute("step");
	response.sendRedirect("login.jsp");
	return;
	}
}
