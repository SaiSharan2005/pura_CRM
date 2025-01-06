package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.Cart;
import com.crm.springbootjwtimplementation.domain.CartItem;
import com.crm.springbootjwtimplementation.domain.Product;
import com.crm.springbootjwtimplementation.domain.dto.CartDTO;

import java.util.List;

public interface CartService {
    Cart createCart(Long userId);
    Cart getActiveCartForUser(Long userId);
    List<CartDTO> getAllCarts() ;
    List<CartDTO> getCartsByUserId(Long userId) ;
    Cart addItemToCart(Long userId, Long productId, int quantity);
    Cart removeItemFromCart(Long userId, Long cartItemId);
    Cart updateCartItem(Long userId, Long cartItemId, int quantity);
    List<CartItem> getCartItems(Long userId);
}
