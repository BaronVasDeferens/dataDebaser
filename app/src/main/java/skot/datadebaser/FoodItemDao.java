package skot.datadebaser;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

/**
 * Created by skot on 11/26/17.
 */
@Dao
public abstract class FoodItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void addFoodItem(FoodItem foodItem);

    @Delete
    public abstract void removeFoodItem(FoodItem foodItem);

    @Query("SELECT itemName, dateCreated FROM foodItem ORDER BY itemName ASC")
    public abstract List<FoodItem> getAllFoodItems();

    @Transaction
    public void addNewFoodItem(FoodItem foodItem) {
        foodItem.setDateCreated(System.currentTimeMillis());
        addFoodItem(foodItem);
    }
}
