package skot.datadebaser;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Food Item
 * (or maybe more accurately, "list item")
 * Describes a repeatedly-purchased item and when it was initially added.
 */

@Entity(tableName = "fooditem", primaryKeys = {"itemname"})
public class FoodItem {


    @ColumnInfo(name = "itemname")
    @NonNull
    private String itemName;

    @ColumnInfo(name = "datecreated")
    private long dateCreated;

    public FoodItem(@NonNull String itemName) {
        this.itemName = itemName;
    }

    @NonNull
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateCreatedInterp() {

        Date date = new Date(dateCreated);

        String formatted = DateFormat.getDateInstance().format(date);
        System.out.println(">>> " + formatted);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format2 = simpleDateFormat.format(date);
        System.out.println("format2 = " + format2);

        return date;
    }

    public String getDateAsString() {
        Date date = new Date(dateCreated);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);

    }

}
