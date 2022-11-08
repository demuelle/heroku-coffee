package com.coffee.coffeeordering.controller;

import com.coffee.coffeeordering.model.Coffee;
import com.coffee.coffeeordering.respository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {
    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Coffee getCoffeeById(@PathVariable Long id) {
        Optional<Coffee> returnVal = coffeeRepository.findById(id);
        if (returnVal.isPresent() == false) {
            throw new IllegalArgumentException("No coffee with id " + id);
        }
        return returnVal.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addNewCoffee(@RequestBody Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoffee(@PathVariable Long id) {
        coffeeRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCoffee(@PathVariable Long id, @RequestBody Coffee coffee) {
        // maybe add a check that the id does not conflict with coffee.getId()
        if (coffee.getId().equals(id) == false) {
            throw new IllegalArgumentException("Request body and path variable indicate different ids. Path variable: " + id +"; Request body: " + coffee.getId());
        }
        coffee.setId(id);
        coffeeRepository.save(coffee);
    }
}
