package at.geyerritter.dezsys07.soa;

import at.geyerritter.dezsys07.data.DataRecord;

import java.util.ArrayList;
import java.util.List;
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
    "dataRecord"
})
@XmlRootElement(name = "getDataRecordResponse")
public class GetDataRecordResponse {

    @XmlElement(required = true)
    protected List<DataRecord> dataRecord;

    /**
     * Returns the value of the dataRecord field.
     * If the field was not initialized before, a new ArrayList will be assigned.
     *
     * @return The DataRecords that match the give name
     */
    public List<DataRecord> getDataRecord() {
        if (dataRecord == null) {
            dataRecord = new ArrayList<>();
        }
        return this.dataRecord;
    }

    public void setDataRecord(List<DataRecord> dataRecord) {
        this.dataRecord = dataRecord;
    }
}
