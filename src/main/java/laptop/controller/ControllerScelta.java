package laptop.controller;

import laptop.exception.PersistenzaException;
import laptop.utilities.ConnToDb;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerScelta {
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    public String getTypeDb(String type) {


        try{
            if(type.equalsIgnoreCase("db")) {

                vis.setTypeOfDb("db");
                ConnToDb.creaPopolaDb();


            }
            else if (type.equalsIgnoreCase("file")) {

                vis.setTypeOfDb("file");


            }
            else throw new PersistenzaException();


        }catch (PersistenzaException | FileNotFoundException e)
        {
            Logger.getLogger("Type of db").log(Level.SEVERE," check persisyency", e);

        }

            return vis.getTypeOfDb();

    }



}
