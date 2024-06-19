package rzepiszczak.damian.tripmaker.attractions;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Attractions", description = "Trip attractions catalogue")
@RestController
public class AttractionsController {

    @GetMapping("/attractions")
    public String getAttractions() {
        return "List of attractions";
    }
}
