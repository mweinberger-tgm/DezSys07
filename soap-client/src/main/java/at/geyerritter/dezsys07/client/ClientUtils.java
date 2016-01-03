package at.geyerritter.dezsys07.client;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Utility methods for the Client
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 * @version 20151217.1
 */
public class ClientUtils {

    /**
     * Converts a existing SOAP message into its String representation
     *
     * @param message The message that will be converted
     * @param beautify Beautify the output String or not
     * @return The converted String
     * @throws Exception Will be thrown if any exceptions occur during the process
     */
    public static String soapMessageToString(SOAPMessage message, boolean beautify) throws Exception {
        String output;
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = message.getSOAPPart().getContent();
        StringWriter writer = new StringWriter();
        transformer.transform(sourceContent, new StreamResult(writer));
        output = writer.getBuffer().toString().replaceAll("\n|\r", "");

        if (beautify) {
            InputSource src = new InputSource(new StringReader(output));
            Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            Boolean keepDeclaration = output.startsWith("<?xml");

            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            LSSerializer serializer = impl.createLSSerializer();

            // Beautify the XML-Document
            serializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
            // Keep the declaration
            serializer.getDomConfig().setParameter("xml-declaration", keepDeclaration);

            output = serializer.writeToString(document);
        }

        return output;
    }
}
