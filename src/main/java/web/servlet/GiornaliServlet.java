package web.servlet;

import java.io.IOException;
import java.io.Serial;


import laptop.database.GiornaleDao;
import laptop.exception.IdException;
import web.bean.GiornaleBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.model.raccolta.Giornale;


@WebServlet("/GiornaliServlet")
public class GiornaliServlet extends HttpServlet{

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String GIORNALI="/giornali.jsp";
    private static final GiornaleBean gB=new GiornaleBean();
    private static final GiornaleDao gD=new GiornaleDao();
    private static final Giornale giornale=new Giornale();
    private static final String BEANG="beanG";
    private final SystemBean sB=SystemBean.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String i=req.getParameter("procedi");
        String g=req.getParameter("genera lista");
        String a=req.getParameter("annulla");
        String id=req.getParameter("idOgg");


        try {
            if(g!=null && g.equals("genera lista"))
            {


                gB.setListaGiornaliB(gD.getGiornali());

                req.setAttribute(BEANG,gB);
                RequestDispatcher view = getServletContext().getRequestDispatcher(GIORNALI);
                view.forward(req,resp);


            }
            if(i!=null && i.equals("procedi"))
            {
                sB.setTypeAsDaily();

                int idO=Integer.parseInt(id);
                if((idO>=1) && (idO<=gD.getGiornali().size()))
                {


                    gB.setIdB(Integer.parseInt(id));
                    giornale.setId(gB.getIdB());
                    gB.setTitoloB(gD.getGiornaleIdTitoloAutore(giornale).get(0).getTitolo());
                    SystemBean.getInstance().setIdB(gB.getIdB());
                    SystemBean.getInstance().setTitoloB(gB.getTitoloB());
                    req.setAttribute(BEANG, gB);
                    req.setAttribute("bean1", SystemBean.getInstance());

                    RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
                    view.forward(req, resp);
                }


            }
            if(a!=null && a.equals("indietro"))
            {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
                view.forward(req,resp);
            }


        } catch (NumberFormatException | ServletException e) {
            gB.setMexB(new IdException("id nullo o eccede lista"));
            req.setAttribute("beanG",gB);
            RequestDispatcher view = getServletContext().getRequestDispatcher(GIORNALI);
            view.forward(req,resp);
        }
    }







}