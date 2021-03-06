package test.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import test.Animal;
import test.dto.Food;
import test.dto.FoodType;
import test.service.ZooService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class EventService {
    private final ZooService zooService;

    @Autowired
    public EventService(ZooService zooService) {
        this.zooService = zooService;
    }

    @EventListener(ZooEvent.class)
    public void onApplicationEvent(ZooEvent zooEvent) {
        FoodType relevantFoodType = zooEvent.getHungryAnimals()
                .stream()
                .map(Animal::getPossibleFoodTypes)
                .flatMap(Collection::stream)
                .distinct()
                .findAny()
                .orElseThrow(RuntimeException::new); //Exception could be customized
        Food food = new Food();
        food.setFoodType(relevantFoodType);
        food.setExpirationDate(LocalDateTime.now().plusHours(6));
        zooService.feed(zooEvent.getHungryAnimals(), food);
    }
}
