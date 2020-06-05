package test;

import org.springframework.stereotype.Component;
import test.dto.Food;
import test.dto.FoodType;

import java.util.Arrays;
import java.util.List;

@Component
public class Cat implements Animal {
    private boolean hungry = true;

    public void voice() {
        System.out.println("mi");
    }

    @Override
    public boolean eat(Food food) {
        hungry = false;
        return isHungry();
    }

    @Override
    public boolean isHungry() {
        return hungry;
    }

    @Override
    public List<FoodType> getPossibleFoodTypes() {
        return Arrays.asList(FoodType.CHICKEN,
                FoodType.FISH,
                FoodType.MEAT,
                FoodType.VEGGIES);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
