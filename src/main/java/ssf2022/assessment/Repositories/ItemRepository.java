package ssf2022.assessment.Repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ssf2022.assessment.Models.Item;

@Repository
public class ItemRepository {
    
    private List<Item> itemList;

    public ItemRepository(){
        itemList= new LinkedList<>();
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }


    @Override
    public String toString() {
        return "ItemRepository [itemList=" + itemList + "]";
    }


    public List<String> getItemNames(){
        List<String> nameList= new LinkedList<>();
        for(int i=0; i<itemList.size(); i++){
            nameList.add(itemList.get(i).getItem());
        }

        return nameList;
    }

    public void addItem(Item item){
        if (!itemList.stream().anyMatch(e -> e.getItem().equalsIgnoreCase(item.getItem()))) {
            itemList.add(item);
        } else {
            Item oldItem =  itemList.stream().filter(e -> e.getItem().equalsIgnoreCase(item.getItem())).findFirst().get();
            int newQty = oldItem.getQuantity() + item.getQuantity();
            oldItem.setQuantity(newQty);
        }
    }

    

}
