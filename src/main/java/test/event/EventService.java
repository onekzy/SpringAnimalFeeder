package test.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import test.dto.Food;
import test.dto.FoodType;
import test.service.ZooServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class EventService {
    private final ZooServiceImpl zooService;

    private static List<FoodType> list = new LinkedList<>(Arrays.asList(FoodType.values()));

    @Autowired
    public EventService(ZooServiceImpl zooService) {
        this.zooService = zooService;
    }

    @EventListener(ZooEvent.class)
    public void onApplicationEvent(ZooEvent zooEvent) {
        list.removeIf(x -> x.equals(zooEvent.getFood().getFoodType()));
        Food food = new Food();
        food.setFoodType(list.stream().findAny().get());
        food.setExpirationDate(LocalDateTime.now().plusHours(6));
        zooService.feed(food);
    }
}
