package com.example.finalapp.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalapp.DetailActivity;
import com.example.finalapp.R;
import com.example.finalapp.model.Post;
import com.example.finalapp.ui.home.PostAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;


// Add items to shopping in the shopping fragment
// adapter: care about what you want to do in shopping_cart_items.xml
// shopFrgament - shopfragment.xml
// shop fragment add items to the order part of the database when hit submit
// adapter: plus or minus will connect with the shop database
// Once submit clear the clear the shopping database
//

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    public Context mContext;
    public List<Post> mPost;

    private FirebaseUser firebaseUser;

    public ShopAdapter(Context mContext, List<Post> mPost){
        this.mContext = mContext;
        this.mPost = mPost;
    }

    public void setData(List<Post> list){
        this.mPost = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shopping_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopAdapter.ViewHolder holder, final int position){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Post post = mPost.get(position);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (post.getImages() != null && post.getImages().size() != 0) {
            Glide.with(mContext).load(post.getImages().get(0)).into(holder.postImageShopping);
        }
        holder.postTitleShopping.setText(post.getTitle());
        holder.priceShopping.setText("$ "+Double.toString(post.getPrice()));
        // set the quantity of the textView
        holder.updateQuantityValue.setText(post.getQuantity().toString());

        // remove item from shopping cart when button removeFromShoppingList is clicked
        holder.removeFromShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    db.collection("shop").document(firebaseUser.getUid())
                            .update(post.getPostid(), FieldValue.delete())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    DocumentReference documentReference = db.collection("shop").document("user");
                                    mPost.remove(position);
                                    notifyItemRemoved(position);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Toast.makeText(); //finish making the toast
                                }
                            });
            }
        });


        // display details of the post when they click the post title in shopping cart
        holder.postTitleShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("postid", post.getPostid());
                mContext.startActivity(intent);
            }
        });

        //reduce the quantity of the item when you click reduce quantity button
        holder.reduceQuantityShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("shop").document(firebaseUser.getUid()).
                        update(post.getPostid(), FieldValue.increment(-1)).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Display the quantity in the textView
                        notifyItemChanged(position);
                    }
                });

            }
        });

        // increase the quantity of the item you want
        holder.addToShopQuantityShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("shop").document(firebaseUser.getUid()).update(post.getPostid(), FieldValue.increment(1));

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView postImageShopping, addToShopQuantityShopping, reduceQuantityShopping, removeFromShoppingList;
        public TextView postTitleShopping, priceShopping, submitShoppingCart, updateQuantityValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            removeFromShoppingList = itemView.findViewById(R.id.remove_from_shopping_list);
            postImageShopping = itemView.findViewById(R.id.post_image_shopping_cart);
            submitShoppingCart = itemView.findViewById(R.id.submit_shopping_cart);
            addToShopQuantityShopping = itemView.findViewById(R.id.add_quantity);
            postTitleShopping =  itemView.findViewById(R.id.postTitleShopping);
            reduceQuantityShopping = itemView.findViewById(R.id.reduce_quantity);
            priceShopping =  itemView.findViewById(R.id.price_shopping_cart);
            updateQuantityValue = itemView.findViewById(R.id.quantity_update_view);
        }

    }
    public void remove() {
        return;
    }
}