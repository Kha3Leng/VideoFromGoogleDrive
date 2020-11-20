package com.example.videofromgoogledrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MaterialDesign extends AppCompatActivity {

    RecyclerView rc;
    CardViewRecyclerAdapter adapter;
    ArrayList<Card> cardArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        cardArrayList = new ArrayList<>();

        rc = findViewById(R.id.recyclerView);
        adapter = new CardViewRecyclerAdapter(this, cardArrayList);
        initializeCards();



        rc.setAdapter(adapter);
        rc.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeCards() {
        TypedArray imgRess = getResources().obtainTypedArray(R.array.photos);
        String[] titles = {"Hey", "How're you doing", "have a good day"};
        for (int i=0; i< 3; i++){
            cardArrayList.add(new Card(titles[i], imgRess.getResourceId(i, R.drawable.alena)));
        }

        imgRess.recycle();
        adapter.notifyDataSetChanged();
    }

    private static class CardViewRecyclerAdapter extends RecyclerView.Adapter<CardViewRecyclerAdapter.CardViewHolder>{

        LayoutInflater layoutInflater;
        ArrayList<Card> cardsList;

        public CardViewRecyclerAdapter(Context context, ArrayList<Card> cards) {
            this.layoutInflater = LayoutInflater.from(context);
            this.cardsList = cards;
        }

        @NonNull
        @Override
        public CardViewRecyclerAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View holder = layoutInflater.inflate(R.layout.one_card, parent, false);
            return new CardViewHolder(holder);
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewRecyclerAdapter.CardViewHolder holder, int position) {
            Card card = cardsList.get(position);
            holder.title.setText(card.getTitle());
            Glide.with(layoutInflater.getContext()).load(card.getImgRes()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return (cardsList == null)?0: cardsList.size();
        }

        public class CardViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView imageView;

            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                imageView = itemView.findViewById(R.id.photo);
            }
        }
    }


    public class Card{
        String title;
        int imgRes;

        public String getTitle() {
            return title;
        }

        public int getImgRes() {
            return imgRes;
        }

        public Card(String title, int imgRes) {
            this.title = title;
            this.imgRes = imgRes;
        }
    }


}