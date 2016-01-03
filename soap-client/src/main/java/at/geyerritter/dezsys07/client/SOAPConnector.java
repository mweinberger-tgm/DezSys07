package at.geyerritter.dezsys07.client;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * Creates a connection to the server and sends the generated SOAP request
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 * @version 20151217.1
 */
public class SOAPConnector {

    private String webServiceURL;

    private SOAPMessageCreator messageCreator;

    public SOAPConnector(SOAPMessageCreator messageCreator, String webServiceURL) {
        this.messageCreator = messageCreator;
        this.webServiceURL = webServiceURL;
    }

    /**
     * Sends the request to the given server and returns the received response
     *
     * @return The received response
     * @throws SOAPException Any SOAP error
     */
    public SOAPMessage call() throws SOAPException {
        // Create SOAP Connection
        SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = connectionFactory.createConnection();

        // Send SOAP Message to the SOAP Server
        SOAPMessage message = connection.call(this.messageCreator.create(), this.webServiceURL);

        // Close the connection
        connection.close();

        return message;
    }

    public String getWebServiceURL() {
        return webServiceURL;
    }

    public void setWebServiceURL(String webServiceURL) {
        this.webServiceURL = webServiceURL;
    }

    public SOAPMessageCreator getMessageCreator() {
        return messageCreator;
    }

    public void setMessageCreator(SOAPMessageCreator messageCreator) {
        this.messageCreator = messageCreator;
    }
}
