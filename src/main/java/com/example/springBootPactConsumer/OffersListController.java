package com.example.springBootPactConsumer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OffersListController {

    private final SealOffersGateway sealOffersGateway;

    public OffersListController(SealOffersGateway sealOffersGateway) {
        this.sealOffersGateway = sealOffersGateway;
    }

    @GetMapping("/")
    public ModelAndView list(){
        ModelAndView modelAndView = new ModelAndView("offers/list");
        ModelMap model = modelAndView.getModelMap();
        List<TravelOfferEntity> offers = sealOffersGateway.fetchOffers();
        model.addAttribute("offers", offers);
        return modelAndView;
    }

}
