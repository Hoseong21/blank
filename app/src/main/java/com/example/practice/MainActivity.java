package com.example.practice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recipeRecyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레시피 RecyclerView 설정
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        recipeList = new ArrayList<>();
        recipeList.add(new Recipe("Spaghetti", R.drawable.spaghetti));
        recipeList.add(new Recipe("Caesar Salad", R.drawable.caesar_salad));
        recipeList.add(new Recipe("Tacos", R.drawable.tacos));
        recipeList.add(new Recipe("Sushi", R.drawable.sushi));

        recipeAdapter = new RecipeAdapter(this, recipeList);
        recipeRecyclerView.setAdapter(recipeAdapter);

        // 버튼 설정: Google 앱으로 이동
        Button searchRecipeButton = findViewById(R.id.searchRecipeButton);
        searchRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Google 앱 열기
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.googlequicksearchbox");
                if (intent != null) {
                    startActivity(intent);
                } else {
                    // Google 앱이 없으면 Play Store로 이동
                    Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.googlequicksearchbox"));
                    startActivity(playStoreIntent);
                }
            }
        });
    }
}