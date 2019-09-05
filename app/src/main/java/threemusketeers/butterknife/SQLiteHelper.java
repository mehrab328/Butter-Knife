package threemusketeers.butterknife;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getsubImagePart(String s) {
        SQLiteDatabase database = getReadableDatabase();
        //return database.rawQuery("SELECT * FROM SUBCAPTCHA WHERE part_alpha = '" + s + "'", null);

        return database.rawQuery("SELECT * FROM SUBCAPTCHA WHERE part_alpha = '" + s + "' ORDER BY RANDOM()", null);
    }

    public Cursor getExtraSubImagePart(String s) {
        SQLiteDatabase database = getReadableDatabase();
        //return database.rawQuery("SELECT * FROM SUBCAPTCHA WHERE part_alpha = '" + s + "'", null);

        return database.rawQuery("SELECT * FROM SUBCAPTCHA WHERE part_alpha != '" + s + "' ORDER BY RANDOM() LIMIT 12", null);
    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor getAlphabet(int i) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("select * from CAPTCHA where id = " + String.valueOf(i), null);
    }

    public void insertData(String text, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO CAPTCHA VALUES (NULL, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, text);
        statement.bindBlob(2, image);

        statement.executeInsert();
    }

    public void insertData_subcaptcha(String text, byte[] image, String alphabet) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO SUBCAPTCHA VALUES (?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, text);
        statement.bindBlob(2, image);
        statement.bindString(3, alphabet);

        statement.executeInsert();
    }

    public Cursor matchCaptcha(String alphabet) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("select part_name, part_image from SUBCAPTCHA where part_name in (?,?,?,?)", new String[]{alphabet + "1", alphabet + "2", alphabet + "3", alphabet + "4"});
    }
}
