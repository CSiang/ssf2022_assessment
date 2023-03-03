package ssf2022.assessment.Services;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import ssf2022.assessment.Quotation;

@Service
public class QuotationService {
    
    public Optional <Quotation> getQuotations(List<String> items) throws Exception {

        String QsyUrl = "https://quotation.chuklee.com";

        JsonArrayBuilder jArr = Json.createArrayBuilder();
        for (int i=0; i< items.size(); i++){
            jArr.add(items.get(i));
        }

        JsonArray json = jArr.build();

        System.out.println(json.toString());

        RequestEntity<String> req = RequestEntity.post(QsyUrl).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .header("Accept", "Application/json").body(json.toString(),String.class);

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;

		String payload="";
		int statusCode=500;
		try {
			resp = template.exchange(req, String.class);
			payload = resp.getBody();
			statusCode = resp.getStatusCode().value();

		} catch (HttpClientErrorException ex) {
            payload = ex.getResponseBodyAsString();
			//statusCode = ex.getRawStatusCode();
			statusCode = ex.getStatusCode().value();
            // return Optional<T>.empty();
		} finally{
            System.out.printf(">>> status code: %d\n", statusCode);
            System.out.printf(">>> payload: \n%s\n", payload);
        }

        if(statusCode == 200){
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject jo = reader.readObject();

            Quotation qo = new Quotation();
            qo.setQuoteId(jo.getString("quoteId"));
            
            JsonObject quoteObject = jo.getJsonObject("quotations");
            Map<String, Float> quotations = new HashMap<>();

            for(String name: items){
                quoteObject.getJsonNumber(name).doubleValue();
                quotations.put(name, (float)quoteObject.getJsonNumber(name).doubleValue());
            }

            qo.setQuotations(quotations);
            return Optional.of(qo);
        }

        return Optional.empty();
    }


}