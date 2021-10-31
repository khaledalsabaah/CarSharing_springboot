package de.thb.carsharing.service;

import de.thb.carsharing.entity.Car;
import de.thb.carsharing.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAllCars() {
        return (List<Car>) carRepository.findAll();
    }

    public Optional<Car> getCarById(long id) {
        return carRepository.findById(id);
    }

    public Car addCar(String model, Color color) {
        return carRepository.save(Car.builder()
                .model(model)
                .color(color)
                .build());
    }
}
