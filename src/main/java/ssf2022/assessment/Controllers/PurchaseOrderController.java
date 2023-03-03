package ssf2022.assessment.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ssf2022.assessment.Quotation;
import ssf2022.assessment.Models.Item;
import ssf2022.assessment.Models.shipDetail;
import ssf2022.assessment.Repositories.ItemRepository;
import ssf2022.assessment.Services.ItemService;
import ssf2022.assessment.Services.QuotationService;

@Controller
@RequestMapping(path = "/")
public class PurchaseOrderController {
    
    @Autowired
    ItemRepository itemRepo;

    @Autowired
    ItemService itemSvc;

    @Autowired
    QuotationService qoSvc;

    @GetMapping()
    public String view1(Model model, HttpSession session){

        itemRepo = (ItemRepository)session.getAttribute("items");

        if(null == itemRepo){
            itemRepo = new ItemRepository();
            session.setAttribute("items", itemRepo);
        }

        model.addAttribute("items", itemRepo.getItemList());
        model.addAttribute("item", new Item());
        return "view1";
    }

    @PostMapping(path = "/addItem")
    public String addItem(@Valid Item item, BindingResult result, Model model, HttpSession session){
        
        if(result.hasErrors()){
            return "view1";
        }
        
        Optional<FieldError> opt = itemSvc.getError(item);
        if (!opt.isEmpty()){
            result.addError(opt.get());
            return "view1";
        }

        itemRepo = (ItemRepository)session.getAttribute("items");
        itemRepo.addItem(item);

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

    @PostMapping(path = "/checkout")
    public String view3 (@Valid shipDetail detail, BindingResult result, HttpSession session, Model model) throws Exception{

        if(result.hasErrors()){
            return "view2";
        }

        itemRepo = (ItemRepository)session.getAttribute("items");
        
        Optional <Quotation> opt = qoSvc.getQuotations(itemRepo.getItemNames());

        if (opt.isEmpty()){
            
            ObjectError err = new ObjectError("globalError", "Invalid resources");
            result.addError(err);
            
            model.addAttribute("detail", detail);
            return "view2";
        }
        
        Quotation qo = opt.get();
        float totalCost = qoSvc.calculateCost(qo, itemRepo);

        model.addAttribute("ID", qo.getQuoteId());
        model.addAttribute("detail", detail);
        model.addAttribute("totalCost", totalCost);

        session.invalidate();

        return "view3";
    }

}
