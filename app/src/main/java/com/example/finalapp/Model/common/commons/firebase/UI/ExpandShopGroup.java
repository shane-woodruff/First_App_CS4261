package com.example.finalapp.Model.common.commons.firebase.UI;

import com.example.finalapp.Model.common.commons.ShopItem;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ExpandShopGroup implements Comparable<ExpandShopGroup>, Serializable{
    private String shoppingID;
    private String date;
    private String name;
    private ArrayList<ShopItem> Items;
    @Override
    public int compareTo(ExpandShopGroup expandShopGroup) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy'T'HH:mm:ssZ");
        try {
            return sdf.parse(getDate()).compareTo(sdf.parse(expandShopGroup.getDate()));
        }
        catch (ParseException e) {
            return 1;
        }
    }

    public ExpandShopGroup() {
    }

    public ExpandShopGroup(String shoppingID, String date, String name, ArrayList<ShopItem> items) {
        this.shoppingID = shoppingID;
        this.date = date;
        this.name = name;
        Items = items;
    }

    public String getShoppingID() {
        return shoppingID;
    }

    public void setShoppingID(String shoppingID) {
        this.shoppingID = shoppingID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ShopItem> getItems() {
        return Items;
    }

    public void setItems(ArrayList<ShopItem> items) {
        Items = items;
    }
}
