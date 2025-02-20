package com.FoodOrder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FoodOrder.model.Cart;
import com.FoodOrder.model.CartItem;
import com.FoodOrder.model.User;
import com.FoodOrder.request.AddCartItemRequest;
import com.FoodOrder.request.UpdateCartItemRequest;
import com.FoodOrder.service.CartService;
import com.FoodOrder.service.UserService;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestHeader("Authorization") String jwt, @RequestBody AddCartItemRequest request) throws Exception {
        CartItem cartItem = cartService.addItemToCart(request, jwt);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestHeader("Authorization") String jwt, @RequestBody UpdateCartItemRequest request) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@RequestHeader("Authorization") String jwt, @PathVariable Long id)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.removeItemFromCart(id, user);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> getCartUser(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
