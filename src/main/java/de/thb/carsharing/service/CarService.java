package de.thb.carsharing.service;

import de.thb.carsharing.entity.Booking;
import de.thb.carsharing.entity.Car;
import de.thb.carsharing.entity.Type.CarColor;
import de.thb.carsharing.entity.Type.FuelType;
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

    public Car addCar(String model, CarColor carColor, short yearBuilt, FuelType fuelType,
                      double xCoordinates, double yCoordinates, boolean isAutomatic) {
        return carRepository.save(Car.builder()
                .model(model)
                .carColor(carColor)
                .yearBuilt(yearBuilt)
                .fuelType(fuelType)
                .xCoordinates(xCoordinates)
                .yCoordinates(yCoordinates)
                .isAutomatic(isAutomatic)
                .isAvailable(true)
                .isInService(true)
                .isOpen(false)
                .build());
    }

    public void deleteCarById(long id) {
        carRepository.deleteById(id);
    }

    public boolean openCar(long id) {
        if (carRepository.findById(id).isPresent()) {
            Car car = carRepository.findById(id).get();
            car.setOpen(true);
            carRepository.save(car);
            return true;
        } else
            return false;
    }

    public boolean closeCar(long id) {
        if (carRepository.findById(id).isPresent()) {
            Car car = carRepository.findById(id).get();
            car.setOpen(false);
            carRepository.save(car);
            return true;
        } else
            return false;
    }

    public boolean setCarInService(long id) {
        if (carRepository.findById(id).isPresent()) {
            Car car = carRepository.findById(id).get();
            car.setInService(true);
            carRepository.save(car);
            return true;
        } else
            return false;
    }

    public boolean setCarOutOfService(long id) {
        if (carRepository.findById(id).isPresent()) {
            Car car = carRepository.findById(id).get();
            car.setInService(true);
            carRepository.save(car);
            return true;
        } else
            return false;
    }

}
