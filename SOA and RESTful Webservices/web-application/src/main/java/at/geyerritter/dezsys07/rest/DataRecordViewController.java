package at.geyerritter.dezsys07.rest;

import at.geyerritter.dezsys07.data.DataRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;

import java.util.List;

/**
 * This controller handles HTTP GET requests of the browser (with accept text/html header).<br>
 * Therefore the created thymeleaf templates are filled with data of the REST controller and then returned as a HTML document.
 * In order to access the REST controller, an attribute is injected via the @Autowired annotation.<br>
 * <br>
 * Unfortunately, there is a bug in IntelliJ IDEA so context variables do not get resolved in thymeleaf templates <br>
 * It will be fixed in version 15.1 (release on March 1st, 2016) <br>
 * For more info visit https://youtrack.jetbrains.com/issue/IDEA-132738 <br>
 * As a workaround you have to add all attributes again in a new WebContext (see if statements in the methods of this class)
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 *
 * @version 20151217.1
 */
@Controller
public class DataRecordViewController {

    @Autowired
    private DataRecordRestController restController;

    /**
     * Handles browser GET request (with accept text/html header) to the path "/". <br>
     * The user gets redirected to the url "/datarecords"
     *
     * @return redirect to "/datarecords"
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html")
    public String redirectToDisplayDataRecords() {
        return "redirect:datarecords";
    }

    /**
     * Handles browser GET request (with accept text/html header) to the path "/datarecords". <br>
     * This page is used to show data records in a table. Therefore data records are retrieved
     * and additionally filtered by the given get parameter name.
     * They are added as an attribute to the model, so they can be accessed by the thymeleaf templates. <br>
     * The rendered "overview" template is returned by returning a string with the file name.
     *
     * @param name GET parameter name to filter the results
     * @param model model that is used by the templates
     * @return string with the filename of the template (gets rendered through the framework)
     */
    @RequestMapping(value = "/datarecords", method = RequestMethod.GET, produces = "text/html")
    public String displayDataRecords(@RequestParam(value = "name", defaultValue = "") String name, Model model) {
        List<DataRecord> dataRecords = restController.findDataRecordsByName(name).getBody();
        model.addAttribute("dataRecords", dataRecords);

        // IntelliJ bug workaround (see class comment)
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("dataRecords", dataRecords);
        }

        return "overview";
    }

    /**
     * Handles browser GET request (with accept text/html header) to the path "/datarecords/create". <br>
     * This page is used to show a form in order to create a new data record. <br>
     * The rendered "create" template is returned by returning a string with the file name.
     *
     * @return string with the filename of the template (gets rendered through the framework)
     */
    @RequestMapping(value = "/datarecords/create", method = RequestMethod.GET, produces = "text/html")
    public String displayCreateDataRecords() {
        return "create";
    }

    /**
     * Handles browser GET request (with accept text/html header) to the path "/datarecords/{id}". <br>
     * This page is used to show a specific data record so it can be edited. Therefore the data record with the
     * id given in the URL is retrieved. It is then added as an attribute to the model so it can be accessed by the
     * thymeleaf templates. <br>
     *
     * The rendered "edit" template is returned by returning a string with the file name.
     *
     * @param id id of the data record that will be shown.
     * @param model model that is used by the templates
     * @return string with the filename of the template (gets rendered through the framework)
     */
    @RequestMapping(value = "/datarecords/{id}", method = RequestMethod.GET, produces = "text/html")
    public String displayDataRecord(@PathVariable String id, Model model) {
        DataRecord dataRecord = restController.findDataRecord(id).getBody();
        model.addAttribute("dataRecord", dataRecord);

        // IntelliJ bug workaround (see class comment)
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("dataRecord", dataRecord);
        }

        return "edit";
    }

    /**
     * This method is used to handle an EmptyResultDataAccessException. This Exception is thrown if an data record
     * can not be found in the database, so a 404 page is returned to the user.
     *
     * @param ex Exception that should be handled
     * @return 404 not found page
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(Exception ex) {
        return "notfound";
    }

}
