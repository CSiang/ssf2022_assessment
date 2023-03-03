package ssf2022.assessment.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.validation.FieldError;

import ssf2022.assessment.Models.Item;

public class ItemService {
    
    private final String [] itemNames = {"apple", "orange", "bread", "cheese", "chicken", "mineral_water", "instant_noodles"};
    private final List<String> itemList = Arrays.asList(itemNames);

    public Optional<FieldError> getError(Item item){
        if(itemList.contains(item.getItem())){
            FieldError err = new FieldError("item", "item", "We do not stock %s".formatted(item.getItem()));
            return Optional.of(err);
        }
        return Optional.empty()
    }


}
