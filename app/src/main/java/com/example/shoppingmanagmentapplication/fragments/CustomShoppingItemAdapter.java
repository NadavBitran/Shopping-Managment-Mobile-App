package com.example.shoppingmanagmentapplication.fragments;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingmanagmentapplication.R;
import com.example.shoppingmanagmentapplication.model.ShoppingItem;
import com.example.shoppingmanagmentapplication.model.ShoppingItemList;
import com.example.shoppingmanagmentapplication.model.User;
import com.example.shoppingmanagmentapplication.model.eShoppingType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class CustomShoppingItemAdapter extends RecyclerView.Adapter<CustomShoppingItemAdapter.MyHolderView> {

    private List<ShoppingItem> usershoppingItemList;
    private User user;

    public CustomShoppingItemAdapter(User user , List<ShoppingItem> usersShoppingList)
    {
        this.usershoppingItemList = usersShoppingList;
        this.user = user;
    }

    public static class MyHolderView extends RecyclerView.ViewHolder {
        private final ImageView itemImage;
        private final TextView itemName;
        private final TextView itemAmount;
        private final ImageButton itemDelete;

        public MyHolderView(View view){
            super(view);

            itemImage = view.findViewById(R.id.itemImage);
            itemName = view.findViewById(R.id.itemName);
            itemAmount = view.findViewById(R.id.itemAmount);
            itemDelete = view.findViewById(R.id.itemDelete);
        }

    }

    @NonNull
    @Override
    public CustomShoppingItemAdapter.MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_items_card , parent , false);

        return new MyHolderView(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CustomShoppingItemAdapter.MyHolderView holder, int position) {
        holder.itemName.setText(usershoppingItemList.get(position).getItemName());
        holder.itemAmount.setText(String.format("+%d" , usershoppingItemList.get(position).getAmount()));
        holder.itemImage.setImageResource(shoppingTypeToImageResource(usershoppingItemList.get(position).getItemType()));
        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingItemList.removeShoppingItemOfUser(user, holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return usershoppingItemList.size();
    }

    private int shoppingTypeToImageResource(eShoppingType shoppingType)
    {
        switch(shoppingType)
        {
            case Groceries:
                return R.drawable.groceries;

            case Clothing:
                return R.drawable.clothing;

            case Electronics:
                return R.drawable.electronics;

            case Furniture:
                return R.drawable.furniture;

            case ToysAndGames:
                return R.drawable.toysandgames;

            case Other:
                return R.drawable.other;

            default:
                return R.drawable.other;
        }
    }

}
