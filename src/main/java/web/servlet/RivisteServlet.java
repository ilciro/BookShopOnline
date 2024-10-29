package web.servlet;

import java.io.IOException;
import java.io.Serial;


import laptop.database.RivistaDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Rivista;
import web.bean.RivistaBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RivisteServlet")
public class RivisteServlet extends HttpServlet {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private static final RivistaBean  rB=new RivistaBean();
    private static final String RIVISTE="/riviste.jsp";
    private static final String BEANR="beanR";

    private static final Rivista r=new Rivista();
    private static final RivistaDao rD=new RivistaDao();
    private static final SystemBean sB=SystemBean.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String i=req.getParameter("procedi");
        String g=req.getParameter("genera lista");
        String a=req.getParameter("annulla");
        String id=req.getParameter("idOgg");


        try {
            if(g!=null && g.equals("genera lista"))
            {

                rB.setListaRivisteB(rD.getRiviste());
                req.setAttribute(BEANR,rB);
                RequestDispatcher view = getServletContext().getRequestDispatcher(RIVISTE);
                view.forward(req,resp);




            }





            if(i!=null && i.equals("procedi"))
            {
                sB.setTypeAsMagazine();

                int idO=Integer.parseInt(id);
                if((idO>=1) && (idO<=rD.getRiviste().size()))
                {


                    rB.setIdB(Integer.parseInt(id));
                    r.setId(rB.getIdB());
                    rB.setTitoloB(rD.getRivistaIdTitoloAutore(r).get(0).getTitolo());
                    SystemBean.getInstance().setIdB(rB.getIdB());
                    SystemBean.getInstance().setTitoloB(rB.getTitoloB());
                    req.setAttribute(BEANR,rB);
                    req.setAttribute("bean1",SystemBean.getInstance());

                    RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
                    view.forward(req,resp);
                }



            }
            if(a!=null && a.equals("indietro"))
            {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
                view.forward(req,resp);
            }


        } catch (NumberFormatException e) {
            rB.setMexB(new IdException("id nullo o eccede lista"));
            req.setAttribute(BEANR,rB);
            RequestDispatcher view = getServletContext().getRequestDispatcher(RIVISTE);
            view.forward(req,resp);
        }
    }


}