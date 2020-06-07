package test.event;


import org.springframework.context.ApplicationEvent;
import test.Animal;
import test.dto.Food;
import test.service.ZooService;
import test.service.ZooServiceImpl;

import java.util.List;

public class ZooEvent extends ApplicationEvent {

    private List<Animal> hungryAnimals;

    public ZooEvent(Object source, List<Animal> hungryAnimals) {
        super(source);
        this.hungryAnimals = hungryAnimals;
    }

    public List<Animal> getHungryAnimals() {
        return hungryAnimals;
    }
}
