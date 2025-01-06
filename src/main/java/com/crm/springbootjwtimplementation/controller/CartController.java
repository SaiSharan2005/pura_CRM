package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.Cart;
import com.crm.springbootjwtimplementation.domain.CartItem;
import com.crm.springbootjwtimplementation.domain.dto.CartDTO;
import com.crm.springbootjwtimplementation.domain.dto.CartRequest;
import com.crm.springbootjwtimplementation.domain.dto.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private AuthService AuthService;

    // Create a new cart for the user
    @PostMapping("/create")
    public ResponseEntity<Cart> createCart() {
        TokenResponseDTO userToken = AuthService.getAuthenticatedUser();
        Cart cart = cartService.createCart(userToken.getId());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(@RequestBody CartRequest cartRequest) {
        Cart cart = cartService.addItemToCart(cartRequest.getUserId(), cartRequest.getProductId(),
                cartRequest.getQuantity());
        return ResponseEntity.ok(cart);
    }

    // Remove an item from the cart
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Cart> removeItemFromCart(@RequestParam Long userId, @PathVariable Long cartItemId) {
        Cart cart = cartService.removeItemFromCart(userId, cartItemId);
        return ResponseEntity.ok(cart);
    }

    // Update the quantity of an item in the cart
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<Cart> updateCartItem(@RequestParam Long userId, @PathVariable Long cartItemId,
            @RequestParam int quantity) {
        Cart cart = cartService.updateCartItem(userId, cartItemId, quantity);
        return ResponseEntity.ok(cart);
    }

      // Get carts by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartDTO>> getCartsByUserId(@PathVariable Long userId) {
        List<CartDTO> carts = cartService.getCartsByUserId(userId);
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }
    // Retrieve all items in the user's cart
    @GetMapping("/items/{userId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long userId) {
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }
}
