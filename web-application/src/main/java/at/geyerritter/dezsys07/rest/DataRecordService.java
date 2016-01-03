package at.geyerritter.dezsys07.rest;

import at.geyerritter.dezsys07.data.DataRecord;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

/**
 * The service uses a repository to create/read/update/delete data records.
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 * @version 20151217.1
 */
public interface DataRecordService {

    /**
     * Persist a DataRecord in the database
     *
     * @param record The record that will be created and persisted
     * @return The persisted DataRecord
     */
    DataRecord create(DataRecord record);

    /**
     * Deletes an existing record from the database.
     * The deleted record will be returned
     *
     * @param id The record that will be deleted
     * @return The deleted record
     */
    DataRecord delete(String id);

    /**
     * Finds a specific DataRecord
     *
     * @param id The id of the DataRecord
     * @return The DataRecord if found
     * @throws EmptyResultDataAccessException if the data record can not be found
     */
    DataRecord findById(String id) throws EmptyResultDataAccessException;

    /**
     * Finds every DataRecord that contains the given name up to a maximum of 100
     *
     * @param name The name that will be searched
     * @return A list of all DataRecords containing the given name
     */
    List<DataRecord> findTop100ByNameContainingIgnoreCase(String name);

    /**
     * Updates a existing DataRecord
     *
     * @param record The DataRecord that will be updated
     * @return The DTO of the updated DataRecord
     */
    DataRecord update(DataRecord record);

    List<DataRecord> findTop100();

}
