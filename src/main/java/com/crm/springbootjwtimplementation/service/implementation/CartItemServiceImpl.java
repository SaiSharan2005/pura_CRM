package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.CartItem;
import com.crm.springbootjwtimplementation.repository.CartItemRepository;
import com.crm.springbootjwtimplementation.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long id, CartItem cartItem) {
        CartItem existingCartItem = cartItemRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("CartItem not found with ID: " + id));
        existingCartItem.setQuantity(cartItem.getQuantity());
        existingCartItem.setPrice(cartItem.getPrice());
        return cartItemRepository.save(existingCartItem);
    }

    @Override
    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("CartItem not found with ID: " + id));
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}
