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
import laptop.exception.IdException;
import laptop.model.TempUser;
import web.bean.SystemBean;
import web.bean.TempUserBean;
import web.bean.UserBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/GestioneUtentiServlet")
public class GestioneUtentiServlet extends HttpServlet {
    private static final TempUserBean tUB=new TempUserBean();
    private static final UserBean uB=UserBean.getInstance();
    private static final SystemBean sB=SystemBean.getInstance();
    private static final String INSERISCI="inserisci";
    private static final String MODIFICA="modifica";
    private static final String TEMPUB="tempUb";
    private static final String BEANS="beanS";
    private static final String AGGIUNGIMODIFICA="/aggiungiModificaUtente.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view;
        try {

            String genera = req.getParameter("genera");
            String ins = req.getParameter(INSERISCI);
            String mod = req.getParameter(MODIFICA);
            String ind = req.getParameter("indietro");
            String id = req.getParameter("idUtente");
            String elimina=req.getParameter("elimina");
            if (genera != null && genera.equals("genera")) {
                tUB.setLista(list());
                req.setAttribute(TEMPUB, tUB);
                view = getServletContext().getRequestDispatcher("/gestioneUtenti.jsp");
                view.forward(req, resp);
            }
            if (ins != null && ins.equals(INSERISCI)) {
                sB.setTypeOfModif(INSERISCI);
                req.setAttribute(BEANS,sB);
                view = getServletContext().getRequestDispatcher(AGGIUNGIMODIFICA);
                view.forward(req, resp);
            }
            if (mod != null && mod.equals(MODIFICA)) {

                    checkId(id);

                   sB.setIdB(Integer.parseInt(id));


                    tUB.setIdB(UsersDao.getUserList().get(sB.getIdB()).getId());
                    tUB.setRuoloB(UsersDao.getUserList().get(sB.getIdB()).getIdRuoloT());
                    tUB.setNomeB(UsersDao.getUserList().get(sB.getIdB()).getNomeT());
                    tUB.setCognomeB(UsersDao.getUserList().get(sB.getIdB()).getCognomeT());
                    tUB.setEmailB(UsersDao.getUserList().get(sB.getIdB()).getEmailT());
                    tUB.setPassB(UsersDao.getUserList().get(sB.getIdB()).getPasswordT());
                    tUB.setDescrizioneB(UsersDao.getUserList().get(sB.getIdB()).getDescrizioneT());
                    tUB.setDataDiNascitaB(UsersDao.getUserList().get(sB.getIdB()).getDataDiNascitaT());


                    sB.setTypeOfModif(MODIFICA);
                    req.setAttribute(BEANS,sB);
                    req.setAttribute(TEMPUB,tUB);

                    view=getServletContext().getRequestDispatcher(AGGIUNGIMODIFICA);
                    view.forward(req,resp);

            }
            if(elimina!=null && elimina.equals("elimina"))
            {

                tUB.setIdB(Integer.parseInt(id)+1);
                TempUser tU=new TempUser();
                tU.setId(tUB.getIdB());


               if(UsersDao.deleteTempUser(tU))
               {
                   req.setAttribute(BEANS,sB);
                   req.setAttribute(TEMPUB,tUB);
                   view=getServletContext().getRequestDispatcher("/admin.jsp");
                   view.forward(req,resp);

               }
               else{
                   view=getServletContext().getRequestDispatcher(AGGIUNGIMODIFICA);
                   view.forward(req,resp);
               }


            }
            if(ind!=null && ind.equals("indietro"))
            {
                sB.setIdB(-1);
                req.setAttribute(BEANS,sB);
                req.setAttribute("beanU",uB);
                view=getServletContext().getRequestDispatcher("/admin.jsp");
                view.forward(req,resp);
            }
        } catch (SQLException | ServletException | IOException | IdException e) {
            Logger.getLogger("doPost").log(Level.SEVERE, " exception", e);
            view = getServletContext().getRequestDispatcher("/gestioneUtenti.jsp");
            view.forward(req, resp);
        }

    }

    private ObservableList<TempUserBean> list() throws SQLException {
        ObservableList<TempUser> lista= UsersDao.getUserList();
        ObservableList<TempUserBean> listaB= FXCollections.observableArrayList();

        for (TempUser tempUser : lista) {
            TempUserBean uTB=new TempUserBean();
            uTB.setIdB(tempUser.getId());
            uTB.setRuoloB(tempUser.getIdRuoloT());
            uTB.setNomeB(tempUser.getNomeT());
            uTB.setCognomeB(tempUser.getCognomeT());
            uTB.setEmailB(tempUser.getEmailT());
            uTB.setPassB(tempUser.getPasswordT());
            uTB.setDescrizioneB(tempUser.getDescrizioneT());
            uTB.setDataDiNascitaB(tempUser.getDataDiNascitaT());
            listaB.add(uTB);


        }
        return listaB;
    }

    private void checkId(String id) throws IdException, SQLException {
        if (Integer.parseInt(id) < 0 || Integer.parseInt(id) > UsersDao.getUserList().size()) {
            throw new IdException(" id out of range");
        }
    }
}
