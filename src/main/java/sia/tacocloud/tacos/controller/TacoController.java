package sia.tacocloud.tacos.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sia.tacocloud.tacos.model.Taco;
import sia.tacocloud.tacos.repository.TacoRepository;

@Controller
@RequestMapping("/recent")
//@RequestMapping(path = "/api/tacos", produces = "application/json")
//@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoController {
    private TacoRepository tacoRepo;

    public TacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping
    public String recentTacos(Model model) {
        PageRequest page = PageRequest.of(
                0, 12);
        Iterable<Taco> tacos = tacoRepo.findAll(page).getContent();
        model.addAttribute("tacos", tacos);
        return "recent";
    }
}


