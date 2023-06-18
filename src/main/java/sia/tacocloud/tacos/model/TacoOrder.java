package sia.tacocloud.tacos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class TacoOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = 6L;

    @ManyToOne
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Название доставки необходимо")
    private String deliveryName;

    @NotBlank(message = "Название улицы необходимо")
    private String deliveryStreet;

    @NotBlank(message = "Город необходим")
    private String deliveryCity;

    @NotBlank(message = "Район необходим")
    private String deliveryState;

    @NotBlank(message = "Почтовый индекс необходим")
    private String deliveryZip;

    @CreditCardNumber(message = "Неправильный номер карты")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message = "Несоответсвует формату MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Неправильный код CVV")
    private String ccCVV;
    @ManyToMany(targetEntity=Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

}
