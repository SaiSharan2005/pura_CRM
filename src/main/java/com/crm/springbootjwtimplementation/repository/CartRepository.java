package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.Cart;
import com.crm.springbootjwtimplementation.domain.CartStatus;
import com.crm.springbootjwtimplementation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Return a list of carts (not a list of Optionals)
    List<Cart> findByUserAndStatus(User user, CartStatus status);
    List<Cart> findByUserIdAndStatus(Long userId, CartStatus status);
    List<Cart> findByUserId(Long userId);
}
