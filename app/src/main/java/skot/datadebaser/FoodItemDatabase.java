package skot.datadebaser;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by skot on 11/26/17.
 */

@Database(entities = {FoodItem.class}, version = 1)
public abstract class FoodItemDatabase extends RoomDatabase {
    public abstract FoodItemDao foodItemDao();
}
