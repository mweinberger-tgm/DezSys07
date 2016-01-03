package at.geyerritter.dezsys07.soa;

import at.geyerritter.dezsys07.data.DataRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * The endpoint for the SOA service. <br>
 * Request are mapped to their related responses in this class
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 * @version 20151217.1
 */
@Endpoint
public class DataRecordEndpoint {
    public static final String NAMESPACE_URI = "http://at/geyerritter/dezsys07/soa";

    private DataRecordRepository dataRecordRepository;

    /**
     * Spring uses this constructor to auto-inject a repository
     *
     * @param dataRecordRepository Injected repository
     */
    @Autowired
    public DataRecordEndpoint(DataRecordRepository dataRecordRepository) {
        this.dataRecordRepository = dataRecordRepository;
    }

    /**
     * Maps the DataRecord search request. <br>
     * To be exact, a request with a GatDataRecord request payload will be mapped
     * to a related response.
     *
     * @param request The SOAP Request
     * @return A response with the first 100 entries
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDataRecordRequest")
    @ResponsePayload
    public GetDataRecordResponse getDataRecord(@RequestPayload GetDataRecordRequest request) {
        GetDataRecordResponse response = new GetDataRecordResponse();
        response.setDataRecord(dataRecordRepository.findTop100ByNameContainingIgnoreCase(request.getName()));

        return response;
    }
}
