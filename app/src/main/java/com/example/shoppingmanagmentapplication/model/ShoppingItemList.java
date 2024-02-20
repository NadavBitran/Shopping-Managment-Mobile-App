package com.example.shoppingmanagmentapplication.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ShoppingItemList {
    private static final Map<User, List<ShoppingItem>> shoppingItemListOfUser = new HashMap<>();

    private ShoppingItemList() {
    }

    public static void initializeFakeShoppingItemList()
    {
        User fake1U = new User("dani" , "dani@gmail.com" , "@Dani123");
        User fake2U = new User("avi" , "avi@gmail.com" , "@Avi123");
        User fake3U = new User("shlomi" , "shlomi@gmail.com" , "@Shlomi123");

        List<ShoppingItem> fake1L = new ArrayList<>();
        List<ShoppingItem> fake2L = new ArrayList<>();
        List<ShoppingItem> fake3L = new ArrayList<>();

        fake1L.add(new ShoppingItem(eShoppingType.Groceries , 2 , "dani apples"));
        fake1L.add(new ShoppingItem(eShoppingType.Clothing , 5 , "dani tshirt"));
        fake1L.add(new ShoppingItem(eShoppingType.ToysAndGames , 1 , "dani pc"));

        fake2L.add(new ShoppingItem(eShoppingType.Groceries , 2 , "avi apples"));
        fake2L.add(new ShoppingItem(eShoppingType.Clothing , 5 , "avi tshirt"));
        fake2L.add(new ShoppingItem(eShoppingType.ToysAndGames , 1 , "avi pc"));

        fake3L.add(new ShoppingItem(eShoppingType.Groceries , 2 , "shlomi apples"));
        fake3L.add(new ShoppingItem(eShoppingType.Clothing , 5 , "shlomi tshirt"));
        fake3L.add(new ShoppingItem(eShoppingType.ToysAndGames , 1 , "shlomi pc"));

        shoppingItemListOfUser.put(fake1U , fake1L);
        shoppingItemListOfUser.put(fake2U , fake2L);
        shoppingItemListOfUser.put(fake3U , fake3L);
    }

    public static List<ShoppingItem> getShoppingItemListOfUser(User user)
    {
        if(user == null) return null;

        return shoppingItemListOfUser.get(user);
    }

    public static void addShoppingItemOfUser(User user , ShoppingItem newShoppingItem)
    {
        if(user == null || newShoppingItem == null) return;

        if(!UserList.isUserExist(user.getEmail() , user.getPassword())) return;

        List<ShoppingItem> usersShoppingList = shoppingItemListOfUser.get(user);

        if(usersShoppingList == null)
        {
            List<ShoppingItem> newShoppingList = new ArrayList<>();
            newShoppingList.add(newShoppingItem);

            shoppingItemListOfUser.put(user , newShoppingList);
        }
        else
        {
            usersShoppingList.add(newShoppingItem);
        }
    }

    public static boolean removeShoppingItemOfUser(User user, int position)
    {
        if(user == null) return false;

        if(!UserList.isUserExist(user.getEmail() , user.getPassword()))  return false;

        List<ShoppingItem> usersShoppingList = shoppingItemListOfUser.get(user);

        if(usersShoppingList == null) return false;

        if(position < 0 || position >= usersShoppingList.size()) return false;

        usersShoppingList.remove(position);

        return true;
    }

}
