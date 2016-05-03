package im.r_c.android.clearweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import im.r_c.android.clearweather.model.Consts;
import im.r_c.android.clearweather.model.County;

/**
 * ClearWeather
 * Created by richard on 16/5/2.
 */
public class CountyDAO {
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public CountyDAO(Context context) {
        mContext = context;
        DatabaseHelper helper = new DatabaseHelper(mContext, Consts.DATABASE_FILE_NAME, null, Consts.DATABASE_VERSION);
        mDatabase = helper.getWritableDatabase();
    }

    public long insert(County county) {
        ContentValues values = new ContentValues();
        values.put(County.KEY_NAME, county.getName());
        values.put(County.KEY_NAME_EN, county.getNameEn());
        values.put(County.KEY_CITY, county.getCity());
        values.put(County.KEY_PROVINCE, county.getProvince());
        values.put(County.KEY_CODE, county.getCode());
        return mDatabase.insert(Consts.DATABASE_TABLE_COUNTY, null, values);
    }

    public List<County> queryAll() {
        Cursor cursor = mDatabase.query(
                Consts.DATABASE_TABLE_COUNTY,
                new String[]{County.KEY_NAME, County.KEY_NAME_EN, County.KEY_CITY, County.KEY_PROVINCE, County.KEY_CODE},
                null, null, null, null, null);
        List<County> countyList = new LinkedList<>();
        while (cursor.moveToNext()) {
            County county = new County();
            county.setName(cursor.getString(cursor.getColumnIndex(County.KEY_NAME)));
            county.setNameEn(cursor.getString(cursor.getColumnIndex(County.KEY_NAME_EN)));
            county.setCity(cursor.getString(cursor.getColumnIndex(County.KEY_CITY)));
            county.setProvince(cursor.getString(cursor.getColumnIndex(County.KEY_PROVINCE)));
            county.setCode(cursor.getString(cursor.getColumnIndex(County.KEY_CODE)));
            countyList.add(county);
        }
        cursor.close();
        return countyList;
    }

    public County query(String code) {
        Cursor cursor = mDatabase.query(
                Consts.DATABASE_TABLE_COUNTY,
                new String[]{County.KEY_NAME, County.KEY_NAME_EN, County.KEY_CITY, County.KEY_PROVINCE, County.KEY_CODE},
                "code = ?",
                new String[]{code},
                null, null, null);
        if (cursor.moveToFirst()) {
            County county = new County();
            county.setName(cursor.getString(cursor.getColumnIndex(County.KEY_NAME)));
            county.setNameEn(cursor.getString(cursor.getColumnIndex(County.KEY_NAME_EN)));
            county.setCity(cursor.getString(cursor.getColumnIndex(County.KEY_CITY)));
            county.setProvince(cursor.getString(cursor.getColumnIndex(County.KEY_PROVINCE)));
            county.setCode(cursor.getString(cursor.getColumnIndex(County.KEY_CODE)));
            cursor.close();
            return county;
        }
        return null;
    }
}
