package sia.tacocloud.tacos.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.tacos.model.Ingredient;
import sia.tacocloud.tacos.model.Ingredient.Type;
import sia.tacocloud.tacos.model.Taco;
import sia.tacocloud.tacos.model.TacoOrder;
import sia.tacocloud.tacos.model.User;
import sia.tacocloud.tacos.repository.IngredientRepository;
import sia.tacocloud.tacos.repository.TacoRepository;
import sia.tacocloud.tacos.repository.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder") //SA worked after replacing TacoOrder with tacoOrder😁
public class DesignTacoController {
    private TacoRepository tacoRepo;
    private IngredientRepository ingredientRepo;
    private UserRepository userRepo;

    public DesignTacoController(TacoRepository tacoRepo, IngredientRepository ingredientRepo, UserRepository userRepo) {
        this.tacoRepo = tacoRepo;
        this.userRepo = userRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @ModelAttribute("tacoOrder")
    public TacoOrder order() {
        System.out.println("tacoOrder is made");

        return new TacoOrder();
    }

    @ModelAttribute("taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute("user")
    public User user(Principal principal) {
        String username = principal.getName();
        User user = userRepo.findByUsername(username).get();
        return user;
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(
            @Valid Taco taco, Errors errors,
            @ModelAttribute TacoOrder order) {

        if (errors.hasErrors()) {
            return "design";
        }
        Taco saved = tacoRepo.save(taco);
        order.addTaco(saved);
        return "redirect:/orders/current";
    }


    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
