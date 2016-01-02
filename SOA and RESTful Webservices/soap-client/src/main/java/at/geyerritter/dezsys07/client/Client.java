package at.geyerritter.dezsys07.client;

import org.apache.commons.cli.ParseException;

import javax.xml.soap.SOAPMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * This class launches the SOAP client
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 * @version 20151217.1
 */
public class Client {

    public static String NAME;

    public static String HOST = "localhost";

    public static String PORT = "8080";

    public static String DUMP_PATH = "";

    public static void main(String args[]) {
        CLIProcessor processor = new CLIProcessor();

        try {
            // parse the args
            processor.parseArguments(args);
            // setup dat weird soap stuff
            SOAPMessageCreator messageCreator = new DezSysSOAPMessageCreator(NAME);
            SOAPConnector connector = new SOAPConnector(messageCreator, "http://" + HOST + ":" + PORT + "/datarecords/search");

            SOAPMessage message = connector.call();
            String messageString = ClientUtils.soapMessageToString(message, true);

            if (DUMP_PATH.isEmpty() || new File(DUMP_PATH).exists()) {
                // check if the user wants to save the output to a file
                System.out.println(messageString);
            } else {
                // otherwise print the output to the console
                System.out.println("Dumping response to file: " + DUMP_PATH);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DUMP_PATH)));
                writer.write(messageString);
                writer.flush();
                writer.close();
            }
        } catch (ParseException e) {
            // wrong user input
            System.out.println(e.getMessage());
            processor.printHelp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
