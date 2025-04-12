package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.Cart;
import com.crm.springbootjwtimplementation.domain.CartItem;
import com.crm.springbootjwtimplementation.domain.dto.CartDTO;
import java.util.List;

public interface CartService {
    Cart createCart(Long userId);
    List<Cart> getActiveCartsForUser(Long userId);
    List<CartDTO> getAllCarts();
    List<CartDTO> getCartsByUserId(Long userId);
    // Changed parameter from productId to variantId.
    Cart addItemToCart(Long cartId, Long variantId, int quantity);
    Cart removeItemFromCart(Long userId, Long cartItemId);
    Cart updateCartItem(Long userId, Long cartItemId, int quantity);
    List<CartItem> getCartItems(Long userId);
    
    // New method to delete a cart by its id
    CartDTO getCartById(Long cartId);
    void deleteCart(Long cartId);
}
