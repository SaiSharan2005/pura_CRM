package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.CartItem;
import java.util.List;

public interface CartItemService {
    CartItem createCartItem(CartItem cartItem);
    CartItem updateCartItem(Long id, CartItem cartItem);
    CartItem getCartItemById(Long id);
    List<CartItem> getAllCartItems();
    void deleteCartItem(Long id);
}
