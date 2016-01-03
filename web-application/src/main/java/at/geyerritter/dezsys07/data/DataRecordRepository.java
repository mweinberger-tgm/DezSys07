package at.geyerritter.dezsys07.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class communicates with the Mongo Database. This class is marked as Spring repository.
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 * @version 20151205.1
 */
@Repository
public interface DataRecordRepository extends MongoRepository<DataRecord, String> {

    /**
     * Looks for a object with the given name.
     *
     * @param name The name of the {@link DataRecord}
     * @return The DataRecord if found, null otherwise
     */
    List<DataRecord> findTop100ByNameContainingIgnoreCase(@Param("name") String name);

}
