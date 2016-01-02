package at.geyerritter.dezsys07.client;

import javax.xml.soap.*;

/**
 * The creates the SOAP request envelope with the required information
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 * @version 20151217.1
 */
public class DezSysSOAPMessageCreator implements SOAPMessageCreator {

    private String name;

    private static final String NAMESPACE = "http://at/geyerritter/dezsys07/soa";

    public DezSysSOAPMessageCreator(String name) {
        this.name = name;
    }

    /**
     * @see SOAPMessageCreator#create
     */
    @Override
    public SOAPMessage create() throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("gs", NAMESPACE);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement requestElement = soapBody.addChildElement("getDataRecordRequest", "gs");
        SOAPElement nameElement = requestElement.addChildElement("name", "gs");
        nameElement.addTextNode(this.name);

        // Headers
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", NAMESPACE + "getDataRecordRequest");

        soapMessage.saveChanges();

        return soapMessage;
    }
}
