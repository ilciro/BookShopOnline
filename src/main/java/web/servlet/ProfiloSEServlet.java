package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.UsersDao;
import laptop.model.TempUser;
import laptop.model.User;
import web.bean.TempUserBean;
import web.bean.UserBean;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ProfiloSEServlet")

public class ProfiloSEServlet extends HttpServlet {
    private static final TempUserBean tUB=new TempUserBean();
    private static final UserBean uB=UserBean.getInstance();
    private static final String PROFILOSE="/profiloSE.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String genera=req.getParameter("genera");
    String modifica=req.getParameter("modifica");
    String cancella=req.getParameter("cancella");
    String indietro=req.getParameter("indietro");
    RequestDispatcher view;

    try{
        if(genera!=null && genera.equals("genera"))
        {
            tUB.setEmailBNOS(uB.getEmailB());
            tUB.setLista(userToBean());
            req.setAttribute("beanTub",tUB);
            view= getServletContext().getRequestDispatcher(PROFILOSE);
            view.forward(req,resp);
        }
        if(modifica!=null && modifica.equals("modifica"))
        {
            String ruolo=req.getParameter("ruoloN");
            String nome=req.getParameter("nomeN");
            String cognome=req.getParameter("cognomeN");
            String mail=req.getParameter("mailN");
            String pass=req.getParameter("passN");
            String desc=req.getParameter("descN");
            String data=req.getParameter("dataN");

            uB.setRuoloB(ruolo);
            uB.setNomeB(nome);
            uB.setCognomeB(cognome);
            uB.setEmailB(mail);
            uB.setPassB(pass);
            uB.setDescrizioneB(desc);
            uB.setDataDiNascitaB(LocalDate.parse(data));

            UsersDao.aggiornaUtente(userToBeanModif(), tUB.getEmailBNOS());
            view = getServletContext().getRequestDispatcher(PROFILOSE);
            view.forward(req,resp);
        }
        if(cancella!=null && cancella.equals("cancella"))
        {
            User u=User.getInstance();
            u.setEmail(uB.getEmailB());
            if(UsersDao.deleteUser(u))
            {
                Logger.getLogger("cancella ").log(Level.INFO," user deleted successfully !!");
                view= getServletContext().getRequestDispatcher("/index.jsp");
                view.forward(req,resp);
            }
            else {
                Logger.getLogger("cancella exc").log(Level.SEVERE," error has occurred");
                view= getServletContext().getRequestDispatcher(PROFILOSE);
                view.forward(req,resp);
                throw new SQLException(" error with delete!!");
            }
        }
        if (indietro!=null && indietro.equals("indietro"))
        {
            req.setAttribute("beanUb",uB);
            view= getServletContext().getRequestDispatcher("/scrittoreEditore.jsp");
            view.forward(req,resp);
        }

    }catch (IOException | ServletException | SQLException e)
    {
        Logger.getLogger("do post").log(Level.SEVERE," exception has occurred !!.",e);
    }
    }

    private ObservableList<TempUserBean> userToBean() throws SQLException {
        TempUser tU=new TempUser();
        tUB.setEmailBNOS(uB.getEmailB());
        tU.setEmailT(tUB.getEmailBNOS());
        int id=UsersDao.getTempUserSingolo(tU).getId();
        String ruolo=UsersDao.getTempUserSingolo(tU).getIdRuoloT();
        String nome=UsersDao.getTempUserSingolo(tU).getNomeT();
        String cognome=UsersDao.getTempUserSingolo(tU).getCognomeT();
        String email=UsersDao.getTempUserSingolo(tU).getEmailT();
        String pass=UsersDao.getTempUserSingolo(tU).getPasswordT();
        String desc=UsersDao.getTempUserSingolo(tU).getDescrizioneT();
        LocalDate date=UsersDao.getTempUserSingolo(tU).getDataDiNascitaT();

        ObservableList<TempUserBean> list= FXCollections.observableArrayList();
        tUB.setIdBNOS(id);
        tUB.setRuoloBNOS(ruolo);
        tUB.setNomeBNOS(nome);
        tUB.setCognomeBNOS(cognome);
        tUB.setEmailBNOS(email);
        tUB.setPassBNOS(pass);
        tUB.setDescrizioneBNOS(desc);
        tUB.setDataDiNascitaBNOS(date);
        list.add(tUB);
        return list;
    }

    private User userToBeanModif()
    {
        User u= User.getInstance();
        u.setIdRuolo(ProfiloSEServlet.uB.getRuoloB());
        u.setNome(ProfiloSEServlet.uB.getNomeB());
        u.setCognome(ProfiloSEServlet.uB.getCognomeB());
        u.setEmail(ProfiloSEServlet.uB.getEmailB());
        u.setPassword(ProfiloSEServlet.uB.getPassB());
        u.setDescrizione(ProfiloSEServlet.uB.getDescrizioneB());
        u.setDataDiNascita(ProfiloSEServlet.uB.getDataDiNascitaB());
        return u;
    }
}
