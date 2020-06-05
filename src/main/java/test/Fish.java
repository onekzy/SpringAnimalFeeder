package test;

import org.springframework.stereotype.Component;
import test.dto.Food;
import test.dto.FoodType;

import java.util.Arrays;
import java.util.List;

@Component
public class Fish implements Animal {
    private boolean hungry = true;

    @Override
    public void voice() {
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
        return Arrays.asList(FoodType.WORMS);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
