package de.thb.carsharing.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("cars")
    public String showCars(Model model){
        List<Car> carList= carService.getAllCars();
        model.addAttribute("cars", carList);
        return "cars";
    }
    @GetMapping("availablecars")
    public String showAvailableCars(Model model){
        List<Car> avaliableCarList= carService.getAllAvailableCars();
        model.addAttribute("availableCars", avaliableCarList);
        return "cars";
    }
    @GetMapping("cars/{id}")
    public String showCarDetail(@PathVariable("id") Long id, Model model){

        Car car;
        if(carService.getCarById(id).isPresent())
            car= carService.getCarById(id).get();
        else
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        model.addAttribute("car", car);
        return "html/cardetails";
    }
    @PostMapping("addcar")
    public String addCar(@Valid AddCarForm form, BindingResult result){
        //if(result.hasErrors())
        carService.addCar(form.getModel(), form.getCarColor(),form.getYearBuilt(), form.getFuelType(),
                form.getXCoordinates(), form.getYCoordinates(), form.isAutomatic());
        return "redirect:/cars";
    }
    @PostMapping("/deletecar/{id}")
    public String deleteCar(@PathVariable("id") Long id){
        carService.deleteCarById(id);
       // if (result.hasErrors())
        //    throw new ResponseStatusException((HttpStatus.NOT_FOUND));

        return "redirect:/cars";
    }
    @RequestMapping("opencar")
    public String openCar(@RequestParam("id") Long id){
        boolean exist = carService.openCar(id);
        if(!exist)
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        else
            return "redirect:/cars";
    }
    @RequestMapping("closecar")
    public String closeCar(@RequestParam("id") Long id){
        boolean exist = carService.closeCar(id);
        if(!exist)
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        else
            return "redirect:/cars";
    }
}