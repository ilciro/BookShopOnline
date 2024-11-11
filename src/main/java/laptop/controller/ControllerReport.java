package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.ReportDao;
import laptop.database.UsersDao;
import laptop.database.csvreport.CsvReport;
import laptop.database.csvusers.CsvUtente;
import laptop.model.Report;
import laptop.model.TempUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerReport {
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ReportDao rDao;
    private final CsvUtente csvUser;




    public ObservableList<Report> reportTotale() throws CsvValidationException, IOException, SQLException {

        ObservableList<Report> list=FXCollections.observableArrayList();
        if(vis.getTypeOfDb().equalsIgnoreCase("db")) {
            vis.setTypeAsBook();
            list.addAll(reportLGR());
            vis.setTypeAsDaily();
            list.addAll(reportLGR());
            vis.setTypeAsMagazine();
            list.addAll(reportLGR());
            reportUser();
        }
        else {
            list = CsvReport.returnReportIDTipoTitolo(0, null, "");
            reportUser();
        }
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
        ObservableList<Report> list= FXCollections.observableArrayList();
        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
        {
            if (vis.getType().equals("libro") || vis.getType().equals("giornale") || vis.getType().equals("rivista")) {
                list.addAll(rDao.report(vis.getType()));
            } else {
                Logger.getLogger("reportLGR").log(Level.SEVERE, " type is incorrect");
            }
        }


        else list=CsvReport.returnReportIDTipoTitolo(0,vis.getType(),"");
        return list;
    }




    public ControllerReport()
    {
        rDao=new ReportDao();
        csvUser=new CsvUtente();
        //creo le view
        rDao.reportLGR("libro");
        rDao.reportLGR("giornale");
        rDao.reportLGR("rivista");


    }

}
