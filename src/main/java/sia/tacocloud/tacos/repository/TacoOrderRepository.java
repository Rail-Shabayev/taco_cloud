package sia.tacocloud.tacos.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.tacos.model.TacoOrder;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, Long> {
}