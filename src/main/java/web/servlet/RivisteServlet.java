package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.util.logging.Level;
import java.util.logging.Logger;


import laptop.database.RivistaDao;
import laptop.model.raccolta.Rivista;
import web.bean.RivistaBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.bean.UserBean;

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
                    req.setAttribute("beanS",sB);

                    RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
                    view.forward(req,resp);
                }



            }
            if(a!=null && a.equals("indietro"))
            {
                RequestDispatcher view;
                if(sB.isLoggedB())
                {
                    switch (UserBean.getInstance().getRuoloB()) {
                        case "ADMIN", "A" -> {
                            view = getServletContext().getRequestDispatcher("/admin.jsp");
                            view.forward(req, resp);

                        }
                        case "SCRITTORE", "EDITORE", "W", "S", "E" ->
                        {
                            view = getServletContext().getRequestDispatcher("/scrittoreEditore.jsp");
                            view.forward(req, resp);
                        }
                        case "UTENTE","U"->
                        {
                            view = getServletContext().getRequestDispatcher("/utente.jsp");
                            view.forward(req, resp);
                        }
                        default -> Logger.getLogger(" indietro ").log(Level.SEVERE," type is not correct");
                    }
                }
                view = getServletContext().getRequestDispatcher("/index.jsp");
                view.forward(req, resp);
            }



        } catch (NumberFormatException |ServletException|IOException e) {
            Logger.getLogger("do post").log(Level.SEVERE," exception has occurred !! .",e);

        }
    }


}