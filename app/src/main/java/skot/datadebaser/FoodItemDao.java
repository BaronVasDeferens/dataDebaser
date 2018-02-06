package skot.datadebaser;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by skot on 11/26/17.
 */
@Dao
public interface FoodItemDao {

    @Query("SELECT itemName, dateCreated FROM foodItem ORDER BY itemName ASC")
    List<FoodItem> getAllFoodItems();

    @Insert
    void addFoodItem(FoodItem foodItem);

    @Delete
    void removeFoodItem(FoodItem foodItem);
}
