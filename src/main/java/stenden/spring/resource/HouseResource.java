package stenden.spring.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stenden.spring.data.model.House;
import stenden.spring.service.HouseService;

// We're telling Spring MVC that this is a REST controller
// This treats methods with @RequestMapping as if it had the @ResponseBody annotation
// which tells Spring that the return value should be transformed into a response
@RestController
// The base request mapping this controller listens to, we respond to anything starting with /data
@RequestMapping("/data")
public class HouseResource {

    private final HouseService houseService;

    @Autowired
    public HouseResource(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/templatejdbc/{id}")
    public House getTemplateJdbcHouse(@PathVariable("id") Long id) {
        return houseService.getTemplateJdbcHouse(id);
    }

}
