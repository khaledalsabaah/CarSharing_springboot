package de.thb.carsharing.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import de.thb.carsharing.entity.*;
import de.thb.carsharing.service.*;
import de.thb.carsharing.controller.form.*;

import java.util.List;


@Controller

@AllArgsConstructor
public class CarController {

    private final CarService carService;

    // */cars
    @GetMapping("cars")
    public String showCars(Model model){
        List<Car> CarList= carService.getAllCars();
        model.addAttribute("Cars", CarList);
        return "cars";
    }
    @GetMapping("cars/{id}")
    public String showCarDetail(@PathVariable("id") Long id, Model model){

        Car Car;
        if(carService.getCarById(id).isPresent())
            Car= carService.getCarById(id).get();
        else
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        model.addAttribute("Car", Car);
        return "details";
    }
    @PostMapping("addCar")
    public String addCar(@Valid AddCarForm form, BindingResult result){
        //if(result.hasErrors())
        //arService.addCar(form.getModel(), form.getYearBuilt(), form.getColor(), form.getFuelType(),
        //        form.getLocation(), form.isAutomatic(), form.isAvailable());

        return "redirect:/Cars";
    }
}