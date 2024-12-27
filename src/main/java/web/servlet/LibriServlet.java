package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import laptop.database.LibroDao;
import laptop.model.raccolta.Libro;
import web.bean.LibroBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.bean.UserBean;

@WebServlet("/LibriServlet")
public class LibriServlet extends HttpServlet implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final transient LibroBean lB=new LibroBean();
    private static final  String LIBRI="/libri.jsp";
    private final transient LibroDao lD=new LibroDao();
    private final transient Libro l=new Libro();
    private static final String BEANL="beanL";
    private static final SystemBean sB=SystemBean.getInstance();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String i=req.getParameter("procedi");
        String g=req.getParameter("genera lista");
        String a=req.getParameter("annulla");
        String id=req.getParameter("idOgg");

       try {


           if (g != null && g.equals("genera lista")) {


               lB.setElencoLibriB(lD.getLibri());
               req.setAttribute(BEANL, lB);
               RequestDispatcher view = getServletContext().getRequestDispatcher(LIBRI);
               view.forward(req, resp);


           }
           if (i != null && i.equals("procedi")) {
               sB.setTypeAsBook();

               int idO = Integer.parseInt(id);
               if ((idO >= 1) && (idO <= lD.getLibri().size())) {


                   lB.setIdB(Integer.parseInt(id));
                   l.setId(lB.getIdB());
                   lB.setTitoloB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo());
                   sB.setIdB(lB.getIdB());
                   sB.setTitoloB(lB.getTitoloB());
                   req.setAttribute(BEANL, lB);
                   req.setAttribute("beanS", sB);

                   RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
                   view.forward(req, resp);
               }


           }
           if (a != null && a.equals("indietro")) {
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
       }catch (ServletException |IOException e)
       {
          Logger.getLogger("do post").log(Level.SEVERE," exception has occurred !! .",e);
       }


    }


}