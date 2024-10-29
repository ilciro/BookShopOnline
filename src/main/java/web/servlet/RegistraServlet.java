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
import java.util.regex.Pattern;

@WebServlet("/RegistraServlet")
public class RegistraServlet extends HttpServlet {
    private static final UserBean uB=UserBean.getInstance();
    private static final User u=User.getInstance();
    private  boolean state=false;
    private static final String BEANUB="beanUb";
    private static final String REGISTRA="/registra.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruolo=req.getParameter("ruolo");
        String nome=req.getParameter("nome");
        String cognome=req.getParameter("cognome");
        String email=req.getParameter("email");
        String pwd=req.getParameter("pwd");
        String desc=req.getParameter("textarea");
        String data=req.getParameter("data");
        String registra=req.getParameter("registra");
        String indietro=req.getParameter("indietro");
        RequestDispatcher view;

        try{
            if(registra!=null && registra.equals("registra")) {
                uB.setRuoloB(ruolo.substring(0, 1).toUpperCase());
                uB.setNomeB(nome);
                uB.setCognomeB(cognome);
                uB.setEmailB(email);
                uB.setPassB(pwd);
                uB.setDescrizioneB(desc);
                uB.setDataDiNascitaB(LocalDate.parse(data));

                u.setIdRuolo(uB.getRuoloB());
                u.setNome(uB.getNomeB());
                u.setCognome(uB.getCognomeB());
                u.setEmail(uB.getEmailB());
                u.setPassword(uB.getPassB());
                u.setDescrizione(uB.getDescrizioneB());
                u.setDataDiNascita(uB.getDataDiNascitaB());

                if (checkData(u.getNome(), u.getCognome(), u.getEmail(), u.getPassword())) {
                    if (UsersDao.createUser(u))
                    {
                        uB.setMexB(" user correctly registered !!!");
                        req.setAttribute(BEANUB,uB);
                        view=getServletContext().getRequestDispatcher(REGISTRA);
                        view.forward(req,resp);
                    }
                    else
                    {
                        uB.setMexB(new SQLException(" check data !!!").toString());
                        req.setAttribute(BEANUB,uB);
                        view=getServletContext().getRequestDispatcher(REGISTRA);
                        view.forward(req,resp);


                    }
                }else
                {
                    uB.setMexB(new SQLException(" user not registered  data !!!").toString());
                    req.setAttribute(BEANUB,uB);
                    view=getServletContext().getRequestDispatcher(REGISTRA);
                    view.forward(req,resp);
                }
            }
            if(indietro!=null && indietro.equals("indietro"))
            {
                pulisci();
                req.setAttribute(BEANUB,uB);
                view=getServletContext().getRequestDispatcher("/login.jsp");
                view.forward(req,resp);
            }



        }catch (SQLException  e)
        {
            uB.setMexB(e.toString());
            req.setAttribute(BEANUB,uB);
            view=getServletContext().getRequestDispatcher(REGISTRA);
            view.forward(req,resp);
        }
    }
    private boolean checkData (String n, String c, String email, String pwd)
    // controll  all data
    {
        if(checkEmail(email) && checkPassword(pwd) && checkNomeCognome(n,c))
        {
            state=true;
        }
        return state;
    }

    private boolean checkEmail(String email)
    {
        Pattern pattern;

        String emailRegex;
        emailRegex= "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }

    private boolean checkPassword(String pwd )
    {
        if(pwd.length()>=8 ) {
            state= true;
        }
        return state;
    }

    private boolean checkNomeCognome(String n, String c)
    {
        if (n != null && c != null)
        {
            state= true;
        }
        return state;
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
