package skot.datadebaser;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by skot on 11/26/17.
 */

@Entity(tableName = "fooditem", primaryKeys = {"itemname"})
public class FoodItem {


    @ColumnInfo(name = "itemname")
    @NonNull
    private String itemName;

    @ColumnInfo(name = "datecreated")
    private Date dateCreated;

    public FoodItem(@NonNull String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDateCreated() {
        return "derp";
    }
    public void setDateCreated(Date dateCreated) {  }

}
