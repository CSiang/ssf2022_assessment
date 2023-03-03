package ssf2022.assessment.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ssf2022.assessment.Models.Item;
import ssf2022.assessment.Models.shipDetail;
import ssf2022.assessment.Repositories.ItemRepository;
import ssf2022.assessment.Services.ItemService;

@Controller
@RequestMapping(path = "/")
public class PurchaseOrderController {
    
    @Autowired
    ItemRepository itemRepo;

    @Autowired
    ItemService itemSvc;

    @GetMapping()
    public String view1(Model model){

        model.addAttribute("items", itemRepo.getItemList());
        model.addAttribute("item", new Item());
        return "view1";
    }

    @PostMapping(path = "/addItem")
    public String addItem(@Valid Item item, BindingResult result, Model model){
        
        if(result.hasErrors()){
            return "view1";
        }
        
        Optional<FieldError> opt = itemSvc.getError(item);
        if (!opt.isEmpty()){
            result.addError(opt.get());
            return "view1";
        }
        
        itemRepo.addItem(item);

        model.addAttribute("items", itemRepo.getItemList());
        return "redirect:/";
    }

    @GetMapping(path ="/shippingaddress")
    public String view2(Model model){

        if(itemRepo.getItemList().isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("detail", new shipDetail());

        return "view2";
    }

}
