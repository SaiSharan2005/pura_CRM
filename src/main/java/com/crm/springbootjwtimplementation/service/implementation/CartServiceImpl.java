package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Cart;
import com.crm.springbootjwtimplementation.domain.CartItem;
import com.crm.springbootjwtimplementation.domain.CartStatus;
import com.crm.springbootjwtimplementation.domain.Product;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.CartDTO;
import com.crm.springbootjwtimplementation.domain.dto.CartItemDTO;
import com.crm.springbootjwtimplementation.repository.CartItemRepository;
import com.crm.springbootjwtimplementation.repository.CartRepository;
import com.crm.springbootjwtimplementation.repository.ProductRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Cart createCart(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setStatus(CartStatus.ACTIVE);
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getActiveCartsForUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        List<Cart> activeCarts = cartRepository.findByUserAndStatus(user, CartStatus.ACTIVE);
        if (activeCarts.isEmpty()) {
            throw new RuntimeException("No active cart found for user");
        }
        return activeCarts;
    }

    @Override
    public List<CartDTO> getCartsByUserId(Long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        return carts.stream()
                .map(this::mapToCartDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll()
                .stream()
                .map(this::mapToCartDTO)
                .collect(Collectors.toList());
    }

    private CartDTO mapToCartDTO(Cart cart) {
        return new CartDTO(
                cart.getId(),
                cart.getUser().getId(),
                cart.getCreatedAt(),
                cart.getStatus().name(),
                cart.getUpdatedAt(),
                cart.getCartItems().stream()
                    .map(this::mapToCartItemDTO)
                    .collect(Collectors.toList())
        );
    }

    private CartItemDTO mapToCartItemDTO(CartItem cartItem) {
        return new CartItemDTO(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getProductName(),
                cartItem.getQuantity(),
                cartItem.getPrice(),
                cartItem.getTotalPrice()
        );
    }

    @Override
    @Transactional
    public Cart addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
            .filter(item -> item.getProduct().equals(product))
            .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.calculateTotalPrice();
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice());
            cartItem.calculateTotalPrice();
            cartItemRepository.save(cartItem);
        }
        return cart;
    }

    @Override
    @Transactional
    public Cart removeItemFromCart(Long userId, Long cartItemId) {
        List<Cart> activeCarts = getActiveCartsForUser(userId);
        Cart cart = activeCarts.stream()
            .filter(c -> c.getCartItems().stream().anyMatch(item -> item.getId().equals(cartItemId)))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Cart item does not belong to any active cart"));
        
        CartItem cartItem = cart.getCartItems().stream()
            .filter(item -> item.getId().equals(cartItemId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        cart.removeCartItem(cartItem);
        return cart;
    }

    @Override
    @Transactional
    public Cart updateCartItem(Long userId, Long cartItemId, int quantity) {
        List<Cart> activeCarts = getActiveCartsForUser(userId);
        Cart matchingCart = activeCarts.stream()
            .filter(cart -> cart.getCartItems().stream()
                .anyMatch(item -> item.getId().equals(cartItemId)))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Cart item does not belong to any active cart"));

        CartItem cartItem = matchingCart.getCartItems().stream()
            .filter(item -> item.getId().equals(cartItemId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
    
        cartItem.setQuantity(quantity);
        cartItem.calculateTotalPrice();
        cartItemRepository.save(cartItem);
    
        return matchingCart;
    }
    
    @Override
    public List<CartItem> getCartItems(Long userId) {
        List<Cart> activeCarts = getActiveCartsForUser(userId);
        List<CartItem> allItems = new ArrayList<>();
        activeCarts.forEach(cart -> {
            cart.getCartItems().size();
            allItems.addAll(cart.getCartItems());
        });
        return allItems;
    }
    
    // New method to delete a cart by cartId
    @Override
    @Transactional
    public void deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cartRepository.delete(cart);
    }
}
