package be.ehb.enterpriseapplicationsproject.Controller;

import be.ehb.enterpriseapplicationsproject.DAO.EvenementDAO;
import be.ehb.enterpriseapplicationsproject.DAO.LocatieDAO;
import be.ehb.enterpriseapplicationsproject.Model.Evenement;
import be.ehb.enterpriseapplicationsproject.Model.Locatie;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EvenementController {

    @Autowired
    private EvenementDAO evenementDAO;

    @Autowired
    private LocatieDAO locatieDAO;

    @GetMapping("/")
    public String index(Model model) {
        List<Evenement> evenementen = evenementDAO.findTop10ByOrderByIdDesc();
        model.addAttribute("evenementen", evenementen);
        return "index";
    }

    //Details pagina voor één evenement via id
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") long id, Model model){
        Evenement evenement = evenementDAO.findById(id).orElse(null);
        if(evenement == null){
            return "redirect:/";
        }
        Locatie locatie = locatieDAO.findById(evenement.getLocatie().getId()).orElse(null);
        model.addAttribute("evenement", evenement);
        model.addAttribute("locatie", locatie);
        return "details";
    }


    //Toon formulier voor nieuw evenement
    @GetMapping("/new")
    public String newEvenementForm(Model model){
       model.addAttribute("evenement", new Evenement());
       model.addAttribute("locaties", locatieDAO.findAll());
       return "new";
    }

    //verwerk formulier nieuw evenement
    @PostMapping("/new")
    public String newEvenementSubmit(@Valid @ModelAttribute("evenement") Evenement evenement, BindingResult bindingResult, Model model) {
        // Controleer of er validatiefouten zijn
        if (bindingResult.hasErrors()) {
            model.addAttribute("locaties", locatieDAO.findAll());
            return "new"; // Toon het formulier opnieuw met fouten
        }
        evenementDAO.save(evenement);
        return "redirect:/";
    }

    //Alle evenementen
    @GetMapping("/alle")
    public String alleEvenementen(Model model){
        List<Evenement> evenementen = evenementDAO.findAllByOrderByTitelAsc();
        model.addAttribute("evenementen", evenementen);
        return "alle";
    }

    //About pagina
    @GetMapping("/about")
    public String about(){
        return "about";
    }

}
