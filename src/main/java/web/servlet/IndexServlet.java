package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.bean.SystemBean;

import java.io.IOException;

@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
    private static final SystemBean sB=SystemBean.getInstance();
    private static final String BEANS="beanS";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String libro=req.getParameter("buttonL");
        String rivista=req.getParameter("buttonR");
        String giornale=req.getParameter("buttonG");
        String login=req.getParameter("buttonLogin");
        String ricerca=req.getParameter("buttonRic");
        RequestDispatcher view;

        if(libro!=null && libro.equals("libri"))
        {

            sB.setTypeB("libro");
            req.setAttribute(BEANS,sB);
             view= getServletContext().getRequestDispatcher("/libri.jsp");
            view.forward(req,resp);
        }
        if(giornale!=null && giornale.equals("giornali"))
        {
            sB.setTypeB("giornale");
            req.setAttribute(BEANS,sB);
             view= getServletContext().getRequestDispatcher("/giornali.jsp");
            view.forward(req,resp);
        }
        if(rivista!=null && rivista.equals("riviste"))
        {
            sB.setTypeB("rivista");
            req.setAttribute(BEANS,sB);
             view= getServletContext().getRequestDispatcher("/riviste.jsp");
            view.forward(req,resp);
        }
        if(login!=null && login.equals("login"))
        {
            view= getServletContext().getRequestDispatcher("/login.jsp");
            view.forward(req,resp);
        }
        if(ricerca!=null && ricerca.equals("ricerca"))
        {
            view= getServletContext().getRequestDispatcher("/ricerca.jsp");
            view.forward(req,resp);
        }
    }
}
