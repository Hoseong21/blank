package com.example.practice;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView titleTextView = findViewById(R.id.detailRecipeTitle);
        String recipeTitle = getIntent().getStringExtra("recipeTitle");
        titleTextView.setText(recipeTitle);
    }
}
