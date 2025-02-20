package com.FoodOrder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodOrder.dto.RestaurantDto;
import com.FoodOrder.model.Address;
import com.FoodOrder.model.Restaurant;
import com.FoodOrder.model.User;
import com.FoodOrder.repository.AddressRepository;
import com.FoodOrder.repository.RestaurantRepository;
import com.FoodOrder.repository.UserRepository;
import com.FoodOrder.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest request, User user) throws Exception {
        Address address = addressRepository.save(request.getAddress());
        Restaurant restaurant = Restaurant.builder()
                .address(address)
                .contactInformation(request.getContactInformation())
                .cuisineType(request.getCuisineType())
                .description(request.getDescription())
                .images(request.getImages())
                .name(request.getName())
                .openingHours(request.getOpeningHours())
                .registrationDate(LocalDateTime.now())
                .owner(user)
                .build();

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestuarant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType((updateRestaurant.getCuisineType()));
        }
        if (restaurant.getDescription() != null) {
            restaurant.setDescription(updateRestaurant.getDescription());
        }
        if (restaurant.getName() != null) {
            restaurant.setName(updateRestaurant.getName());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() throws Exception {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) throws Exception {
        return restaurantRepository.findSearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("Restaurant not found with id " + id);
        }

        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found with owner id " + userId);
        }

        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto restaurantDto = RestaurantDto.builder()
                .description(restaurant.getDescription())
                .images(restaurant.getImages())
                .title(restaurant.getName())
                .id(restaurantId)
                .build();

        boolean isFavourites = false;
        for (RestaurantDto dto : user.getFavourites()) {
            if (dto.getId().equals(restaurantId)) {
                isFavourites = true;
                break;
            }
        }
        if (!isFavourites) {
            user.getFavourites().add(restaurantDto);
        } else {
            user.getFavourites().removeIf(favourite -> favourite.getId().equals(restaurantId));
        }

        userRepository.save(user);

        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());

        return restaurantRepository.save(restaurant);
    }
}
