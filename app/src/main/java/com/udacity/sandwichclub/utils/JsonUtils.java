package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {

  //Constant keys
  public static final String IMAGE_KEY = "image";
  public static final String NAME_KEY = "name";
  public static final String MAIN_KEY = "mainName";
  public static final String AKA_KEY = "alsoKnownAs";
  public static final String PLACE_KEY = "placeOfOrigin";
  public static final String DESCRIPTION_KEY = "description";
  public static final String INGREDIENTS_KEY = "ingredients";

  /**
   * Method is to parse JSON Sandwich data from the strings.xml file detailed
   * in sandwich_details items.
   *
   * @param json
   * @return Sandwich object
   *
   */
  public static Sandwich parseSandwichJson(String json) throws JSONException{

    //Creating JSON Object for sandwich root and sandwich name
    JSONObject sandwich_root = new JSONObject(json);
    JSONObject sandwich_name = sandwich_root.getJSONObject(NAME_KEY);

    //Assign mainName to new string
    String displayName = sandwich_name.getString(MAIN_KEY);

    //Creating List and JSON Array for aka array object and FOR loop to iterate over it.
    List<String> akaList = new ArrayList<String>();
    JSONArray akaArray = sandwich_name.getJSONArray(AKA_KEY);
    for(int i = 0; i < akaArray.length(); i++){
      akaList.add(akaArray.getString(i));
    }

    //Assigning String key/values to previously created Strings.
    String displayPlace = sandwich_root.getString(PLACE_KEY);
    String displayDescription = sandwich_root.getString(DESCRIPTION_KEY);
    String imagePath = sandwich_root.getString(IMAGE_KEY);

    //Creating List and JSON Array for ingredient array object and FOR loop to iterate over it.
    List<String> ingredientList = new ArrayList<String>();
    JSONArray ingredientsArray = sandwich_root.getJSONArray(INGREDIENTS_KEY);
    for(int i = 0; i < ingredientsArray.length(); i++){
      ingredientList.add(ingredientsArray.getString(i));
    }

    //returning an individual Sandwich
    return new Sandwich(displayName, akaList, displayPlace, displayDescription, imagePath, ingredientList);

  }

}
