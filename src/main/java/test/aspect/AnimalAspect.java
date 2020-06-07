package test.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import test.Animal;
import test.dto.Food;

import java.time.LocalDateTime;
import java.util.Objects;

@Aspect
@Component
public class AnimalAspect {

    @Pointcut("execution(* test.Animal.eat(..))")
    public void eatPoint() {
    }

    @Before(value = "eatPoint()")
    public void beforeEat() {
        System.out.println("start eat");
    }

    @After(value = "eatPoint()")
    public void afterEat() {
        System.out.println("end eat");
    }

    @AfterReturning(value = "eatPoint()")
    public void returnEat(JoinPoint joinPoint) {
        Animal animal = (Animal) joinPoint.getTarget();
        Food food = (Food) joinPoint.getArgs()[0];
        String log,
                animalName = animal.getClass().getSimpleName().toLowerCase(),
                foodName = food.getFoodType().toString().toLowerCase();
        if (animal.isHungry()) {
            log = String.format("The %s cannot eat %s", animalName, foodName);
        } else {
            log = String.format("The %s has eaten %s successfully", animalName, foodName);
        }
        System.out.println(log);
    }

    @AfterThrowing(value = "eatPoint()", throwing = "ex")
    public void eatFailed(Throwable ex) {
        System.out.println("eat failed: " + ex.getMessage());
    }

    @Around(value = "eatPoint() && args(food)")
    public Object proceedFeeding(ProceedingJoinPoint proceedingJoinPoint, Food food) throws Throwable {
        Animal animal = null;
        if (Objects.nonNull(animal = (Animal) proceedingJoinPoint.getTarget())) {
            return animal.getPossibleFoodTypes().contains(food.getFoodType()) &&
                    !LocalDateTime.now().isAfter(food.getExpirationDate()) ? proceedingJoinPoint.proceed() : false;
        }
        return false;
    }
}