package com.stackbilly.compose_tutorial

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.stackbilly.compose_tutorial.models.Houses

class DBHandler(context:Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase){
        val query = ("CREATE TABLE "+ TABLE_NAME+ " ("
                +ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +DESCRIPTION_COL + " TEXT,"
                +IMAGE_COL + " TEXT,"
                +LOCATION_COL + " TEXT,"
                +NAME_COL + " TEXT,"
                +PRICE_COL + " TEXT,"
                +URL_COL+" TEXT)")
        db.execSQL(query)
    }

    fun addNewHouse(houses:List<Houses>?){
        val db = this.writableDatabase

        for (house in houses!!){
            val values = ContentValues()

            values.put(DESCRIPTION_COL, house.Description)
            values.put(IMAGE_COL, house.Image)
            values.put(LOCATION_COL, house.Location)
            values.put(NAME_COL, house.Name)
            values.put(PRICE_COL, house.Price)
            values.put(URL_COL, house.Url)

            db.insert(TABLE_NAME, null, values)
        }

        db.close()
    }

    override fun onUpgrade(db:SQLiteDatabase, oldVersion:Int, newVersion:Int){
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object{
        private const val DB_NAME = "houseDB"
        private const val DB_VERSION = 1

        private const val TABLE_NAME = "houses"

        private const val ID_COL = "id"

        private const val DESCRIPTION_COL = "Description"
        private const val NAME_COL = "Name"
        private const val IMAGE_COL = "Image"
        private const val LOCATION_COL = "Location"
        private const val PRICE_COL = "Price"
        private const val URL_COL = "Url"
    }

    fun readHouses():MutableList<Houses>{
        val db = this.readableDatabase

        val cursorHouses:Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val houseMutableList:MutableList<Houses> = mutableListOf()

        if (cursorHouses.moveToFirst()){
            do {
                houseMutableList.add(
                    Houses(
                        cursorHouses.getString(1),
                        cursorHouses.getString(2),
                        cursorHouses.getString(3),
                        cursorHouses.getString(4),
                        cursorHouses.getString(5),
                        cursorHouses.getString(6)
                    )
                )
            }while (cursorHouses.moveToNext())
        }
        cursorHouses.close()
        return houseMutableList
    }
}