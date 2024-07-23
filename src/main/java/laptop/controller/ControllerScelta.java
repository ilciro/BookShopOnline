package laptop.controller;

import laptop.exception.PersistenzaException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerScelta {
    private ControllerSystemState vis=ControllerSystemState.getInstance();
    public boolean getTypeDb(String type)
    {
        boolean state = false;

        try{
            if(type.equalsIgnoreCase("file")) {
                state = true;
                vis.setTypeOfDb("file");
            }
            else if (type.equalsIgnoreCase("db")) {
                state=true;
                vis.setTypeOfDb("db");

            }
            else throw new PersistenzaException();


        }catch (PersistenzaException e)
        {
            Logger.getLogger("Type of db").log(Level.SEVERE," check persisyency", e);

        }
        return state;
    }
}
