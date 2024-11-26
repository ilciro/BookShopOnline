package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.PagamentoDao;
import laptop.database.UsersDao;
import laptop.exception.IdException;
import laptop.model.Pagamento;
import laptop.model.User;
import web.bean.PagamentoBean;
import web.bean.UserBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/OrdiniServlet")
public class OrdiniServlet extends HttpServlet {
    private static final UserBean uB=UserBean.getInstance();
    private static final PagamentoBean pB=new PagamentoBean();
    private static final PagamentoDao pD=new PagamentoDao();
    private static final Pagamento p=new Pagamento();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String genera=req.getParameter("genera");
        String elimina=req.getParameter("elimina");
        String indietro=req.getParameter("indietro");
        String id=req.getParameter("idP");
        try{
            if(genera!=null && genera.equals("genera"))
            {
                generaLista(req,resp);

            }
            if(elimina!=null && elimina.equals("elimina"))
            {
                elimina(id,req,resp);
            }
            if(indietro!=null && indietro.equals( "indietro"))
            {
               indietro(req,resp);
            }

        }catch (SQLException | IOException|ServletException |IdException e)
        {
            Logger.getLogger("do post").log(Level.SEVERE," exception has occurred!!. ",e);
        }
        //pagamento su email


    }
    private void indietro(HttpServletRequest req,HttpServletResponse resp) throws SQLException, ServletException, IOException {
        RequestDispatcher view;
        User u= User.getInstance();
        u.setEmail(uB.getEmailB());
        String ruolo= UsersDao.getRuolo(u);
        switch (ruolo)
        {
            case "UTENTE","U"->{
                req.setAttribute("beanUB",uB);
                view = getServletContext().getRequestDispatcher("/utente.jsp");
                view.forward(req,resp);
            }
            case "SCRITTORE","EDITORE","W","S","E"->
            {
                req.setAttribute("beanUB",uB);
                view = getServletContext().getRequestDispatcher("/scrittoreEditore.jsp");
                view.forward(req,resp);
            }
            default -> throw new SQLException(" role is invalid !!");

        }
    }

    private void elimina(String id,HttpServletRequest req,HttpServletResponse resp) throws SQLException, ServletException, IOException, IdException {
        int idPagamento=Integer.parseInt(id);
        p.setEmail(uB.getEmailB());
        if(idPagamento<=0 ) throw new IdException(" id is wrong !");
        pB.setIdB(idPagamento);
        p.setIdPag(pB.getIdB());
        if(pD.cancellaPagamento(p))
        {
            req.setAttribute("beanP",pB);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/ordini.jsp");
            view.forward(req,resp);
        }
        else throw new SQLException(" payment not deleted !!");
    }
    private void generaLista(HttpServletRequest req,HttpServletResponse resp) throws  ServletException, IOException {
        p.setEmail(uB.getEmailB());
        pB.setListaPagamentiB(pD.listPagamenti(p));

            req.setAttribute("beanP",pB);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/ordini.jsp");
            view.forward(req,resp);



    }
}
