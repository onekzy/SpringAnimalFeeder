package test.service;

import test.Animal;
import test.dto.Food;

import java.util.List;

public interface ZooService {
    void feed(List<Animal> hungryAnimals, Food food);
}
