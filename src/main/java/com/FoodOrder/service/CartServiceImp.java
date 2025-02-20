package com.FoodOrder.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FoodOrder.model.Cart;
import com.FoodOrder.model.CartItem;
import com.FoodOrder.model.Food;
import com.FoodOrder.model.User;
import com.FoodOrder.repository.CartItemRepository;
import com.FoodOrder.repository.CartRepository;
import com.FoodOrder.request.AddCartItemRequest;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(request.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getFood().equals((food))) {
                int quantity = cartItem.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), quantity);
            }
        }

        CartItem newCartItem = CartItem.builder()
                .food(food)
                .quantity(request.getQuantity())
                .ingredients(request.getIngredients())
                .cart(cart)
                .totalPrice(request.getQuantity() * food.getPrice())
                .build();

        CartItem saveCartItem = cartItemRepository.save(newCartItem);
        cart.getCartItems().add(saveCartItem);

        return saveCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
        if (opt.isEmpty()) {
            throw new Exception("Cart item not found");
        }
        CartItem cartItem = opt.get();
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(quantity * cartItem.getFood().getPrice());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, User user) throws Exception {
        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
        if (opt.isEmpty()) {
            throw new Exception("Cart item not found");
        }
        CartItem cartItem = opt.get();

        cart.getCartItems().remove(cartItem);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;
        for (CartItem cartItem : cart.getCartItems()) {
            total += cartItem.getQuantity() * cartItem.getFood().getPrice();
        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> opt = cartRepository.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("Cart not found");
        }
        Cart cart = opt.get();

        return cart;
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart = findCartByUserId(userId);
        cart.getCartItems().clear();

        return cartRepository.save(cart);
    }
}
