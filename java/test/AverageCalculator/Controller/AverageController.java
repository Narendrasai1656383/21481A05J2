package test.AverageCalculator.Controller;

import test.AverageCalculator.Service.AverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class AverageController {
    @Autowired
    private AverageService averageService;

    @GetMapping("/calculate-average")
    public Double calculateAverage() {
        return averageService.calculateAverage();
    }
}