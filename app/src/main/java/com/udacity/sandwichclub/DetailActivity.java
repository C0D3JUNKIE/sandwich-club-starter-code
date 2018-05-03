package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import org.json.JSONException;


public class DetailActivity extends AppCompatActivity {

  public static final String EXTRA_POSITION = "extra_position";
  private static final int DEFAULT_POSITION = -1;

  private TextView origin;
  private TextView aka;
    private TextView description;
    private TextView ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.iv_image);

        //TextView assignments
        origin = (TextView) findViewById(R.id.tv_origin);
        aka = (TextView) findViewById(R.id.tv_aka);
        description = (TextView) findViewById(R.id.tv_description);
        ingredients = (TextView) findViewById(R.id.tv_ingredients);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
      Sandwich sandwich = null;

      //Try catch for JSONException
      try {
        sandwich = JsonUtils.parseSandwichJson(json);
      } catch (JSONException e) {
        e.printStackTrace();
      }

      if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //Passing sandwich object to populateUI method
        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

      //Create StringBuilder to hold aka values
      StringBuilder sbForAkaArray = new StringBuilder();
      //Conditional statement to know if aka is empty
      if(sandwich.getAlsoKnownAs().size() > 0) {
        //Iterate through aka values and append value and comma if multiple values exist.
        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
          if (sandwich.getAlsoKnownAs().size() > 1) {
            sbForAkaArray.append(sandwich.getAlsoKnownAs().get(i));
            sbForAkaArray.append(", ");
          } else {
            sbForAkaArray.append(sandwich.getAlsoKnownAs().get(i));
          }
        }
      }else{
        sbForAkaArray.append("No additional names known.");
      }
      //Setting aka value(s)
      aka.setText(sbForAkaArray);

      //Conditional statement to check if placeOfOrigin exists
      if(sandwich.getPlaceOfOrigin().isEmpty()){
        origin.setText("Not known.");
      }else{
        origin.setText(sandwich.getPlaceOfOrigin());
      }

      //Setting description value
      description.setText(sandwich.getDescription());

      //Create StringBuilder to hold ingredient values
      StringBuilder sbForIngredients = new StringBuilder();
      //Conditional statement to check if ingredients is above 0.
      if(sandwich.getIngredients().size() > 0){
        //Iterate through ingredients and append value with comma if multiple values
        for(int i = 0; i < sandwich.getIngredients().size(); i++){
          sbForIngredients.append(sandwich.getIngredients().get(i));
          sbForIngredients.append(", ");
        }
      }else{
        sbForIngredients.append("None.");
      }
      //Setting ingredientsList to ingredients field
      ingredients.setText(sbForIngredients);

    }

}
