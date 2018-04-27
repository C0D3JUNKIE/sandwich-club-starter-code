package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {

  private static List<String> akaList = new ArrayList<String>();
  private static List<String> ingredients = new ArrayList<String>();

  private static String name;
  private static String poo;
  private static String description;
  private static String imagePath;

  /**
   * Method is to parse JSON Sandwich data from the strings.xml file detailed
   * in sandwich_details items.
   *
   * @param json
   * @return Sandwich object
   *
   */
  public static Sandwich parseSandwichJson(String json) {

    try {

      //Creating JSON Object for sandwich root and sandwich name
      JSONObject sandwich_root = new JSONObject(json);
      JSONObject sandwich_name = sandwich_root.getJSONObject("name");

      //Creating JSON Array for aka array object and FOR loop to iterate over it.
      JSONArray akaArray = sandwich_name.getJSONArray("alsoKnownAs");
        for(int i = 0; i < akaArray.length(); i++){
          akaList.add(akaArray.getString(i));
        }

      //Assigning String key/values to previously created Strings.
      name = sandwich_name.getString("mainName");
      poo = sandwich_root.getString("placeOfOrigin");
      description = sandwich_root.getString("description");
      imagePath = sandwich_root.getString("image");

      //Creating JSON Array for ingredient array object and FOR loop to iterate over it.
      JSONArray ingredientsArray = sandwich_root.getJSONArray("ingredients");
        for(int i = 0; i < ingredientsArray.length(); i++){
          ingredients.add(ingredientsArray.getString(i));
        }

    //Including catch for parseSandwichJson method
    }catch(JSONException jsonException){
      System.out.println("###### EXCEPTION THROWN:  JSON DATA DID NOT PARSE CORRECTLY #######");
      jsonException.printStackTrace();
    }

      //returning an individual Sandwich
      return new Sandwich(name, akaList, poo, description, imagePath, ingredients);

  }

}
