import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;

public class StringServer {
    public static void main(String[] args) {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(args, null);

            // Get reference to root POA and activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create servant (implementation object)
            StringOperationImpl stringOpImpl = new StringOperationImpl();

            // Register the servant with the ORB
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(stringOpImpl);

            // Get reference to Naming Service
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Bind the object reference in Naming
            String name = "StringOperation";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, ref);

            System.out.println("Server ready and waiting...");

            // Wait for incoming requests
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
