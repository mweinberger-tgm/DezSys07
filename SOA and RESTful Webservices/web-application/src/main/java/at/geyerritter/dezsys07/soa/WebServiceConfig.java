package at.geyerritter.dezsys07.soa;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Required configuration to run a SOAP service under Spring
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 * @version 20151217.1
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    /**
     * This class creates a new bean to overwrite Spring's default DispatcherServlet
     * which is required to run the SOAP application
     *
     * @param applicationContext The context of the current application
     * @return The new servlet dispatcher
     */
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/datarecords/search/*");
    }

    /**
     * Generates a WSDL 1.1 definition using the XSD schema which was created beforehand
     *
     * @param dataRecordsSchema the defined XSD schema
     * @return A WSDL definition
     */
    @Bean(name = "dataRecords")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema dataRecordsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("DataRecordsPort");
        wsdl11Definition.setLocationUri("/datarecords/search");
        wsdl11Definition.setTargetNamespace(DataRecordEndpoint.NAMESPACE_URI);
        wsdl11Definition.setSchema(dataRecordsSchema);
        return wsdl11Definition;
    }

    /**
     * Loads the XSD schema and parses it into an object
     *
     * @return The loaded XSD schema
     */
    @Bean
    public XsdSchema dataRecordsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("/contracts/datarecords.xsd"));
    }
}