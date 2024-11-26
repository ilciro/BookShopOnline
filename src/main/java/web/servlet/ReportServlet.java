package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.ReportDao;
import laptop.database.UsersDao;
import laptop.model.TempUser;
import web.bean.ReportBean;
import web.bean.TempUserBean;
import web.bean.UserBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
    private static final ReportDao rD=new ReportDao();
    private static final ReportBean rB=new ReportBean();
    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";
    private static final String REPORT="/report.jsp";
    private static final String BEANR="beanR";
    private static final TempUserBean tUB=new TempUserBean();



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String l = req.getParameter("libri");
        String g = req.getParameter("giornali");
        String r = req.getParameter("riviste");
        String u = req.getParameter("utenti");
        String i = req.getParameter("indietro");
        RequestDispatcher view;
        try {
            if (l != null && l.equals("libri")) {
                rD.report(LIBRO);
                rB.setElencoReportBRepo(rD.report(LIBRO));
                req.setAttribute(BEANR, rB);
                view = getServletContext().getRequestDispatcher(REPORT);
                view.forward(req, resp);
            }
            if (g != null && g.equals("giornali")) {
                rD.report(GIORNALE);
                rB.setElencoReportBRepo(rD.report(GIORNALE));
                req.setAttribute(BEANR, rB);
                view = getServletContext().getRequestDispatcher(REPORT);
                view.forward(req, resp);
            }
            if (r != null && r.equals("riviste")) {
                rD.report(RIVISTA);
                rB.setElencoReportBRepo(rD.report(RIVISTA));
                req.setAttribute(BEANR, rB);
                view = getServletContext().getRequestDispatcher(REPORT);
                view.forward(req, resp);
            }
            if (u != null && u.equals("utenti")) {

                tUB.setLista(list());
            req.setAttribute("tempUb",tUB);
            view = getServletContext().getRequestDispatcher(REPORT);
            view.forward(req, resp);


            }
            if(i!=null && i.equals("indietro"))
            {
                req.setAttribute("beanUb", UserBean.getInstance());
                view = getServletContext().getRequestDispatcher("/admin.jsp");
                view.forward(req, resp);
            }
        }catch (SQLException |NullPointerException e)
        {
            Logger.getLogger("report").log(Level.SEVERE," exception" , e);
        }
    }


    private ObservableList<TempUserBean> list() throws SQLException {
        ObservableList<TempUser> lista=UsersDao.getUserList();
        ObservableList<TempUserBean> listaB=FXCollections.observableArrayList();

        for (TempUser tempUser : lista) {
            TempUserBean uTB=new TempUserBean();
            uTB.setIdBNOS(tempUser.getId());
            uTB.setRuoloBNOS(tempUser.getIdRuoloT());
            uTB.setNomeBNOS(tempUser.getNomeT());
            uTB.setCognomeBNOS(tempUser.getCognomeT());
            uTB.setEmailBNOS(tempUser.getEmailT());
            uTB.setPassBNOS(tempUser.getPasswordT());
            uTB.setDescrizioneBNOS(tempUser.getDescrizioneT());
            uTB.setDataDiNascitaBNOS(tempUser.getDataDiNascitaT());
            listaB.add(uTB);


        }
        return listaB;
    }


}
