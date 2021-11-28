package de.thb.carsharing.service;

import de.thb.carsharing.entity.Car;
import de.thb.carsharing.entity.Type.CarColor;
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

    public Optional<Car> getCarById(long id) {
        return carRepository.findById(id);
    }

    public List<Car> getAllCars() {
        return (List<Car>) carRepository.findAll();
    }

    public List<Car> getAllAvailableCars() {
        return carRepository.findByisAvailableTrue();
    }

    public Car addCar(String model, CarColor carColor) {
        return carRepository.save(Car.builder()
                .model(model)
                .carColor(carColor)
                .build());
    }

    public void deleteCarById(long id) { carRepository.deleteById(id);}




}
