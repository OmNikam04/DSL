import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import StringModule.StringOperation;
import StringModule.StringOperationHelper;
import java.util.Scanner;

public class StringClient {
    public static void main(String[] args) {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(args, null);

            // Get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Resolve the object reference
            StringOperation stringOp = StringOperationHelper.narrow(ncRef.resolve_str("StringOperation"));

            Scanner sc = new Scanner(System.in);

            System.out.println("Enter a string:");
            String input = sc.nextLine();

            System.out.println("Choose operation:");
            System.out.println("1. Reverse String");
            System.out.println("2. Uppercase String");
            int choice = sc.nextInt();

            String result = "";

            if (choice == 1) {
                result = stringOp.reverseString(input);
            } else if (choice == 2) {
                result = stringOp.uppercaseString(input);
            } else {
                System.out.println("Invalid choice");
                return;
            }

            System.out.println("Result: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
