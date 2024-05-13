package rzepiszczak.damian.tripmaker.attractions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttractionsController {

    @GetMapping("/attractions")
    public String getAttractions() {
        return "List of attractions";
    }
}
