package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.database.ReportDao;
import laptop.database.UsersDao;
import laptop.database.csvreport.CsvReport;
import laptop.database.csvusers.CsvUtente;
import laptop.model.Report;
import laptop.model.TempUser;
import laptop.model.User;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ControllerReport {
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ReportDao rDao;
    private final CsvUtente csvUser;




    public ObservableList<Report> reportTotale() throws CsvValidationException, IOException {

        ObservableList<Report> list;
        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
            list= rDao.getReportFromDB();
        else
            list=CsvReport.returnReportIDTipoTitolo(0,null,"");
        return list;
    }

    public ObservableList<TempUser> reportUser() throws SQLException, CsvValidationException, IOException {
       ObservableList<TempUser> list;
        if(vis.getTypeOfDb().equalsIgnoreCase("db"))

               list= UsersDao.getUserList();
        else
            list=csvUser.getUserData();
        return list;
    }


    public ObservableList<Report> reportLGR() throws CsvValidationException, IOException {
        ObservableList<Report> list;
        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
         list= rDao.getReportFromDBLGR();
        else list=CsvReport.returnReportIDTipoTitolo(0,vis.getType(),"");
    return list;
    }
    public ControllerReport()
    {
        rDao=new ReportDao();
        csvUser=new CsvUtente();


    }

}
