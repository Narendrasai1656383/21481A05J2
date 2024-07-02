package test.AverageCalculator.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AverageService {
    @Value("${http://20.244.56.144/test/primes}")
    private String api1Url;

    @Value("${http://20.244.56.144/test/fibo}")
    private String api2Url;

    @Value("${http://20.244.56.144/test/even}")
    private String api3Url;

    @Value("${http://20.244.56.144/test/rand}")
    private String api4Url;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final int MAX_SIZE = 15;
    public Double calculateAverage() {
        List<Double> allNumbers = new ArrayList<>();
        Set<Double> uniqueNumbers = new LinkedHashSet<>(); // Maintain insertion order

        addUniqueNumbers(allNumbers, uniqueNumbers, fetchNumbersFromApi(api1Url));
        addUniqueNumbers(allNumbers, uniqueNumbers, fetchNumbersFromApi(api2Url));
        addUniqueNumbers(allNumbers, uniqueNumbers, fetchNumbersFromApi(api3Url));
        addUniqueNumbers(allNumbers, uniqueNumbers, fetchNumbersFromApi(api4Url));

        double sum = 0.0;
        int count = 0;

        for (Double number : uniqueNumbers) {
            sum += number;
            count++;
        }

        return count == 0 ? 0.0 : sum / count;
    }

    private void addUniqueNumbers(List<Double> allNumbers, Set<Double> uniqueNumbers, List<Double> numbers) {
        for (Double number : numbers) {
            if (!uniqueNumbers.contains(number)) {
                if (uniqueNumbers.size() >= MAX_SIZE) {
                    Double oldestNumber = allNumbers.remove(0); // Remove oldest element from list
                    uniqueNumbers.remove(oldestNumber); // Remove oldest element from set
                }
                uniqueNumbers.add(number);
                allNumbers.add(number); // Add new element to list
            }
        }
    }

    private List<Double> fetchNumbersFromApi(String apiUrl) {
        Double[] numbers = restTemplate.getForObject(apiUrl, Double[].class);
        List<Double> numberList = new ArrayList<>();
        if (numbers != null) {
            Collections.addAll(numberList, numbers);
        }
        return numberList;
    }
}