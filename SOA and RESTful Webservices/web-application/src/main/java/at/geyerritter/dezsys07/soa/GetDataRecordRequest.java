package at.geyerritter.dezsys07.soa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A generated class that appends the required XML annotations to the class
 * and makes it fit the predefined XSD schema
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 * @version 20151217.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name"
})
@XmlRootElement(name = "getDataRecordRequest")
public class GetDataRecordRequest {

    @XmlElement(required = true)
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

}
