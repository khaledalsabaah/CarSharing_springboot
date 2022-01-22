package de.thb.carsharing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import de.thb.carsharing.entity.*;
import de.thb.carsharing.service.*;
import de.thb.carsharing.controller.form.*;

import java.util.List;


@Controller

@AllArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/")
    public String showHome(Model model) {
        List<Car> cars = carService.getAllAvailableCars();
        String carAsString = "";
        if (cars.size() > 0)
            carAsString = getCarsAsString(cars);
        model.addAttribute("cars", carAsString);
        return "index";
    }

    @GetMapping("addcar")
    public String showAddCar(Model model) {
        return "addcar";
    }

    @PostMapping("addcarform")
    public String addcarform(@Valid AddCarForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            String Message= "bitte die Felder mit gültigen Daten ausfüllen!";
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                Message +=  ", "+error.getDefaultMessage();
            }
            //result.getFieldError();
            model.addAttribute("Message", Message);
            return "addcar";
        }
        carService.addCar(form.getModel(), form.getCarColor(), form.getYearBuilt(), form.getFuelType(),
                form.getXCoordinates(), form.getYCoordinates(), form.getPreisPerHour(), form.isAutomatic());
        //request.setAttribute("SuccessMessage", "Sie haben ein Auto erfolgreich hinzufügt!");
        return "redirect:/";
    }

    @GetMapping("cars")
    public String showAllCars(Model model) {
        List<Car> carList = carService.getAllCars();
        model.addAttribute("cars", carList);
        return "cars";
    }

    @GetMapping("availablecars")
    public String showAvailableCars(Model model) {
        List<Car> avaliableCarList = carService.getAllAvailableCars();
        model.addAttribute("availableCars", avaliableCarList);
        return "availablecars";
    }

    @GetMapping("cars/{id}")
    public String showCarDetail(@PathVariable("id") Long id, Model model) {

        Car car;
        if (carService.getCarById(id).isPresent())
            car = carService.getCarById(id).get();
        else
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        model.addAttribute("car", car);
        return "cardetails";
    }

    @PostMapping("/deletecar/{id}")
    public String deleteCar(@PathVariable("id") Long id) {
        carService.deleteCarById(id);
        // if (result.hasErrors())
        //    throw new ResponseStatusException((HttpStatus.NOT_FOUND));

        return "redirect:/cars";
    }

    @RequestMapping("opencar")
    public String openCar(@RequestParam("id") Long id) {
        boolean exist = carService.openCar(id);
        if (!exist)
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        else
            return "redirect:/cars";
    }

    @RequestMapping("closecar")
    public String closeCar(@RequestParam("id") Long id) {
        boolean exist = carService.closeCar(id);
        if (!exist)
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        else
            return "redirect:/cars";
    }

    @RequestMapping("setcarinservice")
    public String setCarInService(@RequestParam("id") Long id) {
        boolean exist = carService.setCarInService(id);
        if (!exist)
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        else
            return "redirect:/cars";
    }

    @RequestMapping("setcaroutofservice")
    public String setCarOutOfService(@RequestParam("id") Long id) {
        boolean exist = carService.setCarOutOfService(id);
        if (!exist)
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        else
            return "redirect:/cars";
    }

    public String getCarsAsString(List<Car> cars) {
        String carAsString = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            carAsString = String.format("{ \"%d\": ", cars.get(0).getId());
            for (int i = 0; i < cars.size(); i++) {
                cars.get(i).setBookingList(null);
                carAsString += objectMapper.writeValueAsString(cars.get(i));
                if (i + 1 != cars.size()) {
                    cars.get(i + 1).setBookingList(null);
                    carAsString += String.format(",\"%d\": ", cars.get(i + 1).getId());
                }
            }
            carAsString += "}";
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Json Convertor Error", e);
        }
        return carAsString;
    }

}