package dk.lsd;

import dk.lsd.datalayer.DatabaseImpl;
import dk.lsd.rmi.RMIMethodsImpl;
import org.apache.log4j.Logger;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    //private final static Logger log =  Logger.getLogger(Main.class.getName());
    public static String getString(){
        return "Hello World";
    }

    public static Registry registry;
    static Logger log = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        try
        {
            log.info("RMI Registry starting...");

            // Create a server registry at default port 1099
            registry = LocateRegistry.createRegistry(1099);

            // Create engine of remote methods, running on the server
            RMIMethodsImpl remoteMethods = new RMIMethodsImpl();

            // Give a name to this engine
            String engineName = "moonlodge";

            // Register the engine by the name, which later will be given to the clients
            Naming.rebind("//localhost/" + engineName, remoteMethods);

            log.info("RMI Registry started...");
        }
        catch (Exception e)
        {
            log.error(e);
            System.err.println("Exception:" + e);
        }

    }
}
