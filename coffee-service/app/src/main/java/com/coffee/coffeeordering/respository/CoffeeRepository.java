package com.coffee.coffeeordering.respository;

import com.coffee.coffeeordering.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    List<Coffee> findByRoast(String roast); // findAllByRoast... findCoffeeByRoast
}
