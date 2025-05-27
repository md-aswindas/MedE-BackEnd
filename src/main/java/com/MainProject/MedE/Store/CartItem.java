package com.MainProject.MedE.Store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private Long productId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private CartModel cart;



    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getProductId() {
        return productId;
    }



    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartModel getCart() {
        return cart;
    }

    public void setCart(CartModel cart) {
        this.cart = cart;
    }
}
