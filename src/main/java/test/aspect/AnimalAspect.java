package test.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import test.*;
import test.dto.Food;

import java.time.LocalDateTime;

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

    @AfterThrowing(value = "eatPoint()", throwing = "ex")
    public void eatFailed(Throwable ex) {
        System.out.println("eat failed: " + ex.getMessage());
    }

    @Around(value = "eatPoint() && args(food)")
    public Object proceedFeeding(ProceedingJoinPoint proceedingJoinPoint, Food food) throws Throwable {
        Animal animal = null;
        Object pointObject = proceedingJoinPoint.getTarget();
        if (pointObject instanceof Bird)
            animal = (Bird) pointObject;
        else if (pointObject instanceof Fish)
            animal = (Fish) pointObject;
        else if (pointObject instanceof Cat)
            animal = (Cat) pointObject;
        else if (pointObject instanceof Dog)
            animal = (Dog) pointObject;

        if (animal != null)
            return animal.getPossibleFoodTypes().stream().anyMatch(x -> x.equals(food.getFoodType())) ?
                    eatAround(proceedingJoinPoint, food) : false;
        return false;
    }

    private Object eatAround(ProceedingJoinPoint proceedingJoinPoint, Food food) throws Throwable {
        String target = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        if (LocalDateTime.now().isAfter(food.getExpirationDate()))
            return false;
        try {
            Object result = proceedingJoinPoint.proceed();
            System.out.println(String.format("The %s has eaten %s successfully",
                    target,
                    food.getFoodType().toString().toLowerCase()));
            return result;
        } catch (Throwable e) {
            throw e;
        }
    }
}