package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.UsersDao;
import laptop.model.User;
import web.bean.UserBean;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

@WebServlet("/ResetServlet")
public class ResetServlet extends HttpServlet {
    private static final UserBean uB=UserBean.getInstance();
    private static final User u= User.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pass=req.getParameter("pass");
        String aggiorna=req.getParameter("reset");
        String indietro=req.getParameter("indietro");
        RequestDispatcher view;

        try {
            if (aggiorna != null && aggiorna.equals("reset")) {
                u.setEmail(uB.getEmailB());
                UsersDao.pickData(u);


                uB.setPassB(pass);
                u.setPassword(uB.getPassB());



                if(!Objects.equals(UsersDao.aggiornaUtente(u, uB.getEmailB()).getEmail(), ""))
                {
                    uB.setMexB(" password changed correctly");
                    req.setAttribute("beanUb",uB);
                    view=getServletContext().getRequestDispatcher("/reset.jsp");
                    view.forward(req,resp);
                }
                else throw new SQLException(" data is wrong !!");


            }
            if(indietro!=null && indietro.equals("indietro"))
            {
                pulisci();
                view=getServletContext().getRequestDispatcher("/login.jsp");
                view.forward(req,resp);
            }
        }catch (SQLException e)
        {
            uB.setMexB(e.toString());
            req.setAttribute("beanUb",uB);
            view=getServletContext().getRequestDispatcher("/reset.jsp");
            view.forward(req,resp);
        }
    }
    private void pulisci() {
        u.setId(-1);
        u.setIdRuolo("");
        u.setNome("");
        u.setCognome("");
        u.setEmail("");
        u.setPassword("");
        u.setDescrizione("");
        u.setDataDiNascita(LocalDate.of(1900,1,1));

        uB.setIdB(u.getId());
        uB.setRuoloB(u.getIdRuolo());
        uB.setNomeB(u.getNome());
        uB.setCognomeB(u.getCognome());
        uB.setEmailB(u.getEmail());
        uB.setPassB(u.getPassword());
        uB.setDescrizioneB(u.getDescrizione());
        uB.setDataDiNascitaB(u.getDataDiNascita());
    }
}
