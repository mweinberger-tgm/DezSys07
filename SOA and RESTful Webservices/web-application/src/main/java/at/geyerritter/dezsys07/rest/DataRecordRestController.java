package at.geyerritter.dezsys07.rest;

import at.geyerritter.dezsys07.data.DataRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import java.util.List;

/**
 * This class represents a RESTful API to create/read/update/delete a data record object. <br>
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 *
 * @version 20151211.1
 */
@RestController
public class DataRecordRestController {

    @Autowired
    private MongoDBDataRecordService service;

    /**
     * Handles GET requests of the URL "/datarecords"<br>
     * This method is used to retrieve data records.
     * The GET parameter name is used for filtering the result. If this parameter is not provided
     * all results are returned.
     *
     * @param name Filter for field name
     * @return List including retrieved data records + status 200 OK
     */
    @RequestMapping(value = "/kd", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<DataRecord>> findDataRecordsByName(@RequestParam(value = "name", defaultValue = "") String name) {
        if (name.length() == 0)
            return new ResponseEntity<>(service.findTop100(), HttpStatus.OK);
        else
            return new ResponseEntity<>(service.findTop100ByNameContainingIgnoreCase(name), HttpStatus.OK);
    }

    /**
     * Handles POST requests of the URL "/datarecords"<br>
     * This method is used to create a new data record. Therefore the datarecord object
     * needs to be specified in the request body.
     *
     * @param dataRecord The data record object that will be created
     * @return The created data record + status 201 CREATED
     */
    @RequestMapping(value="/kd", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<DataRecord> createDataRecord(@RequestBody DataRecord dataRecord) {

        // the id will be set when inserting into the database, so we set it to null now
        dataRecord.setId(null);

        service.create(dataRecord);
        return new ResponseEntity<>(dataRecord, HttpStatus.CREATED);
    }

    /**
     * Handles GET requests of the URL "/datarecords/{id}"<br>
     * This method is used to retrieve a data record specified by the id. The id is part of the URL.<br>
     * If the id does not exist an error is returned.
     *
     * @param id The id of the data record that will be retrieved
     * @return the retrieved object + status 200 OK or an error + status 404 NOT FOUND
     */
    @RequestMapping(value = "/kd/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<DataRecord> findDataRecord(@PathVariable String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    /**
     * Handles PUT requests of the URL "/datarecords/{id}"<br>
     * This method is used to update an existing data record specified by the id. The id is part of the URL.
     * The new data record is part of the body. <br>
     * If the id does not exist an error is returned.
     *
     * @param id The id of the data record that will be updated.
     * @param newDataRecord The data record containing the new information
     * @return the updated object + status 200 OK or an error + status 404 NOT FOUND
     */
    @RequestMapping(value = "/kd/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<DataRecord> updateDataRecord(@PathVariable String id, @RequestBody DataRecord newDataRecord) {
        DataRecord dataRecord = service.findById(id);
        dataRecord.setName(newDataRecord.getName());
        dataRecord.setDescription(newDataRecord.getDescription());

        return new ResponseEntity<>(service.update(dataRecord), HttpStatus.OK);
    }

    /**
     * Handles DELETE requests of the URL "/datarecords/{id}"<br>
     * This method is used to remove an existing data record specified by the id. The id is part of the URL.
     * If the id does not exist an error is returned.
     *
     * @param id The id of the data record that will be removed.
     * @return status 200 OK if the data record has been deleted successfully
     */
    @RequestMapping(value = "/kd/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<DataRecord> updateDataRecord(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method is called if an exception occurs that should lead to a 400 BAD REQUEST error.
     *
     * @param ex Exception to handle
     * @return A 400 BAD REQUEST response + detailed information
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequest(Exception ex) {
        return generateJsonErrorResponse(HttpStatus.BAD_REQUEST, ex);
    }

    /**
     * This method is called if an exception occurs that should lead to a 404 NOT FOUND error.
     *
     * @param ex Exception to handle
     * @return A 404 NOT FOUND response + detailed information
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFound(Exception ex) {
        return generateJsonErrorResponse(HttpStatus.NOT_FOUND, ex);
    }

    /**
     * Generates a json error response with the given status code + exception.
     *
     * @param status http status code
     * @param ex exception
     * @return error response
     */
    private ResponseEntity<String> generateJsonErrorResponse(HttpStatus status, Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(Json.createBuilderFactory(null).createObjectBuilder()
                .add("code", status.value())
                .add("message", ex.getMessage()).build().toString(), headers, status);

    }


}
