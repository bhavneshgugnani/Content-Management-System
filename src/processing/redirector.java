package processing;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.userVO;
public class redirector extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public redirector() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	userVO user = (userVO) request.getSession().getAttribute("user");
	if(user.getType().equalsIgnoreCase("user"))
	{
	RequestDispatcher view = request.getRequestDispatcher("/userview.jsp");
    view.forward(request,response);  
	}
	else if(user.getType().equalsIgnoreCase("admin"))
	{
	RequestDispatcher view = request.getRequestDispatcher("/adminview.jsp");
	view.forward(request,response);
	}
	}
}