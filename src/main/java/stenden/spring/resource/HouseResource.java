package stenden.spring.resource;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stenden.spring.data.House;
import stenden.spring.data.model.AnnotatedHouse;
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

    @GetMapping("/jpa/{id}")
    public House getJpaHouse(@PathVariable("id") Long id) {
        return houseService.getJpaHouse(id);
    }

    @PostMapping
    public House postJpaHouse(@RequestBody @Valid AnnotatedHouse house) {
        return houseService.addHouse(house);
    }

}
