package ssf2022.assessment;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ssf2022.assessment.Services.QuotationService;


@SpringBootApplication
public class AssessmentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

	@Autowired
	QuotationService QSvc;

	@Override
	public void run(String ... args) throws Exception {

		String [] itemNames = {"apple", "orange", "bread", "cheese", "chicken", "mineral_water", "instant_noodles"};
        List<String> itemList = Arrays.asList(itemNames);
		QSvc.getQuotations(itemList );

	}




}
