package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import test.Animal;
import test.Zoo;
import test.dto.Food;
import test.event.ZooEvent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZooServiceImpl implements ZooService {
    private final ApplicationEventPublisher publisher;

    @Autowired
    public ZooServiceImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void feed(List<Animal> hungryAnimals, Food food) {
        hungryAnimals = hungryAnimals
                .stream()
                .peek(animal -> animal.eat(food))
                .filter(Animal::isHungry)
                .collect(Collectors.toList());
        if (hungryAnimals.isEmpty()) {
            System.out.println("All animals are fed up");
        } else {
            System.out.println("Hungry animal(s): " + hungryAnimals);
            publisher.publishEvent(new ZooEvent(this, hungryAnimals));
        }
    }
}
