package iknowsystems;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IKnowSystemsController {

    @RequestMapping("/dezsys")
    public IKnowSystems iKnowSystems(@RequestParam(value="id", defaultValue="id") String id, @RequestParam(value="name", defaultValue="name") String name,
                                     @RequestParam(value="tags", defaultValue="tags") String tags, @RequestParam(value="text", defaultValue="text") String text) {
        return new IKnowSystems(id, name, tags, text);
    }
}
