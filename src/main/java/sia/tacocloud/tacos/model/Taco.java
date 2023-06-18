package sia.tacocloud.tacos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@Data
@Entity
@RestResource(rel="tacos", path="tacos")
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 5, message = "Имя должно содержать как минимум 5 букв!")
    private String name;


    @NotNull
    @ManyToMany(targetEntity=Ingredient.class)
    @Size(min = 1, message = "Вы должны выбрать хотя бы один ингредиент!")
    private List<Ingredient> ingredients;
}
