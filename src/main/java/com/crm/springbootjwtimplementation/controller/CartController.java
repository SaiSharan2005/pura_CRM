package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.Cart;
import com.crm.springbootjwtimplementation.domain.CartItem;
import com.crm.springbootjwtimplementation.domain.dto.CartDTO;
import com.crm.springbootjwtimplementation.domain.dto.CartRequest;
import com.crm.springbootjwtimplementation.domain.dto.users.TokenResponseDTO;
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
    private AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<Cart> createCart() {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Cart cart = cartService.createCart(userToken.getId());
        return ResponseEntity.ok(cart);
    }

    // Note: CartRequest now should include a 'variantId' field.
    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(@RequestBody CartRequest cartRequest) {

        Cart cart = cartService.addItemToCart(
            cartRequest.getCartId(), 
            cartRequest.getVariantId(),  // Updated to pass variantId
            cartRequest.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Cart> removeItemFromCart(@RequestParam Long userId, @PathVariable Long cartItemId) {
        Cart cart = cartService.removeItemFromCart(userId, cartItemId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<Cart> updateCartItem(@RequestParam Long userId, @PathVariable Long cartItemId, @RequestParam int quantity) {
        Cart cart = cartService.updateCartItem(userId, cartItemId, quantity);
        return ResponseEntity.ok(cart);
    }

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
    
    @GetMapping("/items/{userId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long userId) {
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }
    
    // New route to delete a cart using its cart id
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok("Cart with id " + cartId + " has been deleted");
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long cartId) {
        // If you updated the service to return a DTO:
        CartDTO cartDTO = cartService.getCartById(cartId);
        return ResponseEntity.ok(cartDTO);

        // If you kept the service to return the entity, then map it here:
        // Cart cart = cartService.getCartById(cartId);
        // return ResponseEntity.ok(mapToCartDTO(cart));
    }
}
