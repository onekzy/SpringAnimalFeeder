import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import test.Zoo;
import test.configuration.AnnotationConfiguration;
import test.dto.Food;
import test.dto.FoodType;
import test.service.ZooService;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = getAnnotationContext();
        feedAnimal(context);
    }

    public static void feedAnimal(ApplicationContext context) {
        ZooService service = context.getBean(ZooService.class);
        Zoo zoo = context.getBean(Zoo.class);
        Food food = new Food();
        food.setFoodType(FoodType.FISH);
        food.setExpirationDate(LocalDateTime.now().plusHours(6));
        service.feed(zoo.getAnimals(), food);
    }

    public static ApplicationContext getAnnotationContext() {
        return new AnnotationConfigApplicationContext(AnnotationConfiguration.class);
    }
}
