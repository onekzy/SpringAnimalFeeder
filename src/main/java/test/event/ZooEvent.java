package test.event;


import org.springframework.context.ApplicationEvent;
import test.dto.Food;
import test.service.ZooService;
import test.service.ZooServiceImpl;

public class ZooEvent extends ApplicationEvent {

    private Food food;

    public ZooEvent(Object source, Food food, ZooServiceImpl zooService) {
        super(source);
        this.food = food;
    }

    public Food getFood() {
        return food;
    }
}
