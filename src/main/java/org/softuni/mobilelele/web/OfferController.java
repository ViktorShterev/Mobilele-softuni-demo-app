package org.softuni.mobilelele.web;

import jakarta.validation.Valid;
import org.softuni.mobilelele.exceptions.ObjectNotFoundException;
import org.softuni.mobilelele.model.dto.OfferSummaryDTO;
import org.softuni.mobilelele.model.enums.EngineEnum;
import org.softuni.mobilelele.model.dto.CreateOfferDTO;
import org.softuni.mobilelele.model.enums.TransmissionEnum;
import org.softuni.mobilelele.service.BrandService;
import org.softuni.mobilelele.service.OfferService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private final OfferService offerService;
    private final BrandService brandService;

    public OfferController(OfferService offerService, BrandService brandService) {
        this.offerService = offerService;
        this.brandService = brandService;
    }


    @ModelAttribute("engines")
    public EngineEnum[] engineTypes() {
        return EngineEnum.values();
    }

    @ModelAttribute("transmissions")
    public TransmissionEnum[] transmissionTypes() {
        return TransmissionEnum.values();
    }

    @GetMapping("/add")
    public String add(Model model) {

        if (!model.containsAttribute("createOffer")) {
            model.addAttribute("createOffer", CreateOfferDTO.empty());
        }

        model.addAttribute("brands", this.brandService.getAllBrands());

        return "offer-add";
    }

    @PostMapping("/add")
    public String add(@Valid CreateOfferDTO createOfferDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @AuthenticationPrincipal UserDetails seller) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createOffer", createOfferDTO);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createOffer", bindingResult);

            return "redirect:/offer/add";
        }

        UUID offerUUID = this.offerService.createOffer(createOfferDTO, seller);

        return "redirect:/offer/" + offerUUID;
    }

    @GetMapping("/{uuid}")
    public String details(@PathVariable("uuid") UUID uuid, Model model,
                          @AuthenticationPrincipal UserDetails viewer) {

        OfferSummaryDTO offerSummaryDTO = this.offerService.getOfferDetail(uuid, viewer)
                .orElseThrow(() -> new ObjectNotFoundException("Offer with uuid " + uuid + " was not found!"));

        model.addAttribute("offer", offerSummaryDTO);

        return "details";
    }

    @PreAuthorize("@offerServiceImpl.isOwner(#uuid, #principal.username)")
    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") UUID uuid,
                         @AuthenticationPrincipal UserDetails principal) {

        this.offerService.deleteOffer(uuid);

        return "redirect:/offers/all";
    }
}
