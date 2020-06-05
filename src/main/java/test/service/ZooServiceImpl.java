package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import test.Animal;
import test.Zoo;
import test.dto.Food;
import test.event.ZooEvent;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZooServiceImpl implements ZooService {
    private final Zoo zoo;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public ZooServiceImpl(Zoo zoo, ApplicationEventPublisher publisher) {
        this.zoo = zoo;
        this.publisher = publisher;
    }

    @Override
    public void feed(Food food) {
        List<Animal> hungryAnimals = zoo.getAnimals()
                .stream()
                .filter(Animal::isHungry)
                .peek(animal -> animal.eat(food))
                .filter(Animal::isHungry)
                .collect(Collectors.toList());
        if (hungryAnimals.isEmpty()) {
            System.out.println("All animals are fed up");
        } else {
            System.out.print(String.format("Hungry animal%s %s",
                    hungryAnimals.size() < 2 ? " is" : "s are",
                    hungryAnimals
                            .stream()
                            .map(x -> x.toString())
                            .collect(Collectors.joining(", ", "", "\n"))));
            publisher.publishEvent(new ZooEvent(this, food, this));
        }
    }
}
