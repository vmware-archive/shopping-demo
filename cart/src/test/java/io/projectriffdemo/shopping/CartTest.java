package io.projectriffdemo.shopping;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class CartTest {

    @Test
    public void addItem() {
        Cart cart = new Cart("1");
        cart.addItem(getMerchandise(1));
        assertEquals(1, cart.getItems().size());
        assertEquals("1", cart.getItems().get(0).getItemId());
    }

    @Test
    public void removeItem() {
        Cart cart = new Cart("1");
        Random r = new Random();
        cart.addItem(getMerchandise(r.nextInt()));
        cart.addItem(getMerchandise(r.nextInt()));
        int id = r.nextInt();
        cart.addItem(getMerchandise(id));
        cart.addItem(getMerchandise(r.nextInt()));
        assertEquals(4, cart.getItems().size());
        cart.removeItem(id+"");
        assertEquals(3, cart.getItems().size());

    }

    private Merchandise getMerchandise(int itemId) {
        Merchandise item = new Merchandise();
        item.setItemId(itemId+"");
        return item;
    }

}