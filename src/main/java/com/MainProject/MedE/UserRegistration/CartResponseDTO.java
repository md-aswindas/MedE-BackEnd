package com.MainProject.MedE.UserRegistration;

import java.util.List;

public class CartResponseDTO {
    private Long cartId;
    private Long userId;
    private List<CartItemWithProductDTO> items;

    public CartResponseDTO(CartModel cart, List<CartItemWithProductDTO> items) {
        this.cartId = cart.getCartId();
        this.userId = cart.getUserId();
        this.items = items;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItemWithProductDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemWithProductDTO> items) {
        this.items = items;
    }
}
