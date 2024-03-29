package skot.datadebaser;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity implements  View.OnLongClickListener {

    public static final int FOOD_ITEM_DATA = 1;

    FoodItemDatabase foodItemDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodItemDatabase = Room.databaseBuilder(this, FoodItemDatabase.class, "appdatabase").build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FoodItemLoader().execute();
    }

    private void updateItemCountDisplay(final int count) {
        TextView itemCountDisplay = (TextView) findViewById(R.id.itemCountDisplay);
        itemCountDisplay.setText("TOTAL FOOD ITEMS: " + count);
    }

    private void displayFoodItems(List<FoodItem> items) {

        LinearLayout foodItemDisplayArea = (LinearLayout) findViewById(R.id.foodItemDisplayArea);
        foodItemDisplayArea.removeAllViews();

        LayoutInflater inflater = getLayoutInflater();
        for (FoodItem item : items) {

            LinearLayout newItemView = (LinearLayout) inflater.inflate(R.layout.fooditem_listing, foodItemDisplayArea, false);
            TextView nameDisplay = (TextView) newItemView.findViewById(R.id.itemNameDisplay);
            nameDisplay.setText(item.getItemName());

            View removeButton = (View) newItemView.findViewById(R.id.btnRemoveItem);

            removeButton.setTag(item.getItemName());
            removeButton.setOnLongClickListener(this);

            newItemView.setTag(item);
            newItemView.setOnLongClickListener(this);

            foodItemDisplayArea.addView(newItemView);
        }

    }

    // Triggered when user clicks "ADD TO LIST"
    public void addFoodItem(View view) {
        LinearLayout addItemArea = (LinearLayout) findViewById(R.id.addItemArea);
        addItemArea.setVisibility(View.VISIBLE);

        EditText editText = findViewById(R.id.editItemName);
        editText.requestFocus();
        showSoftKeyboard();
    }

    // Triggered when the user clicks on "ADD"
    public void createNewFoodItem(View view) {

        EditText editText = findViewById(R.id.editItemName);

        final String content = editText.getText().toString();
        if (content.length() > 0) {
            FoodItem item = new FoodItem(content);
            new FoodItemAdder().execute(item);
            editText.setText("");
        }
    }

    public void removeItem(View view) {
        String itemName = (String) view.getTag();
        FoodItem item = new FoodItem(itemName);
        new FoodItemRemover().execute(item);
    }

    // When user presses "FINISH"
    public void hideNewItem(View view) {

        // Submit any pending text for addition to the db...
        EditText editText = findViewById(R.id.editItemName);
        if (editText.getText().toString().length() > 0) {
            createNewFoodItem(null);
        }

        LinearLayout addItemArea = findViewById(R.id.addItemArea);
        addItemArea.setVisibility(View.GONE);

        editText.clearFocus();
        hideSoftKeyboard();
    }

    private void showSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    public boolean onLongClick(View view) {

        Object tag = view.getTag();

        if (tag == null) {
            System.out.println("NULL!");
            return false;
        }

        if (tag instanceof FoodItem) {
            FoodItem item = (FoodItem) tag;
            System.out.println(((FoodItem) tag).getItemName() + ":" + item.getDateAsString());

            // display modal
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                .setTitle(item.getItemName())
                .setMessage(item.getDateAsString())
                .show();
        }

        else if (tag instanceof String) {
            removeItem(view);
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        // just eat it
    }

    private class FoodItemLoader extends AsyncTask<Void, List<FoodItem>, List<FoodItem>> {
        @Override
        protected List<FoodItem> doInBackground(Void... voids) {
            return foodItemDatabase.foodItemDao().getAllFoodItems();
        }

        @Override
        protected void onPostExecute(List<FoodItem> foodItems) {
            updateItemCountDisplay(foodItems.size());
            displayFoodItems(foodItems);
        }
    }

    private class FoodItemAdder extends AsyncTask<FoodItem, Void, List<FoodItem>> {

        @Override
        protected List<FoodItem> doInBackground(FoodItem... foodItems) {
            foodItemDatabase.foodItemDao().addNewFoodItem(foodItems[0]);
            return foodItemDatabase.foodItemDao().getAllFoodItems();
        }

        @Override
        protected void onPostExecute(List<FoodItem> items) {
            updateItemCountDisplay(items.size());
            displayFoodItems(items);
        }
    }

    private class FoodItemRemover extends AsyncTask<FoodItem, Void, List<FoodItem>> {

        @Override
        protected List<FoodItem> doInBackground(FoodItem... foodItems) {
            foodItemDatabase.foodItemDao().removeFoodItem(foodItems[0]);
            return foodItemDatabase.foodItemDao().getAllFoodItems();
        }

        @Override
        protected void onPostExecute(List<FoodItem> items) {
            updateItemCountDisplay(items.size());
            displayFoodItems(items);
        }
    }


}
