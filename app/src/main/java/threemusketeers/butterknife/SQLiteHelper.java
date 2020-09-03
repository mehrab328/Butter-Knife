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

    public Cursor getsubImagePart(String s, String difficulty) {
        SQLiteDatabase database = getReadableDatabase();
        //return database.rawQuery("SELECT * FROM SUBCAPTCHA WHERE part_alpha = '" + s + "'", null);

        return database.rawQuery("SELECT * FROM SUBCAPTCHA WHERE part_alpha = '" + s + "' and subcaptcha_difficulty = '" + difficulty + "' ORDER BY RANDOM() LIMIT 4", null);
    }

    public Cursor getExtraSubImagePart(String s, String difficulty) {
        SQLiteDatabase database = getReadableDatabase();
        //return database.rawQuery("SELECT * FROM SUBCAPTCHA WHERE part_alpha = '" + s + "'", null);

        return database.rawQuery("SELECT * FROM SUBCAPTCHA WHERE part_alpha != '" + s + "' and subcaptcha_difficulty = '" + difficulty + "' ORDER BY RANDOM() LIMIT 12", null);

    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor getAlphabet(String difficulty) {
        SQLiteDatabase database = getReadableDatabase();
        //return database.rawQuery("select * from CAPTCHA where id = " + String.valueOf(i) + " and captcha_difficulty = '" + difficulty + "'", null);
        //return database.rawQuery("select * from CAPTCHA where captcha_difficulty = '" + difficulty + "' ORDER BY RANDOM() LIMIT 1", null);
        return database.rawQuery("select * from CAPTCHA where captcha_difficulty = '" + difficulty + "' and alphabet = 'E' ", null);
    }

    public void insertData(String text, byte[] image, String difficulty) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO CAPTCHA VALUES (?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, text);
        statement.bindBlob(2, image);
        statement.bindString(3, difficulty);

        statement.executeInsert();
    }

    public void insertData_subcaptcha(String text, byte[] image, String alphabet, String difficulty) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO SUBCAPTCHA VALUES (?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, text);
        statement.bindBlob(2, image);
        statement.bindString(3, alphabet);
        statement.bindString(4, difficulty);

        statement.executeInsert();
    }

    public Cursor matchCaptcha(String alphabet, String difficulty) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("select part_name, part_image from SUBCAPTCHA where part_name in (?,?,?,?) and subcaptcha_difficulty = '" + difficulty + "'", new String[]{alphabet + "1", alphabet + "2", alphabet + "3", alphabet + "4"});
    }

    public void insertDataStat(String age, String gender, String type, String vision, String mobility, String difficulty,
                               String solve_time, String attempt, String success) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO STAT (age, gender, user_type, vision_problem, mobility_problem, difficulty, solve_time, attempt, success)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, age);
        statement.bindString(2, gender);
        statement.bindString(3, type);
        statement.bindString(4, vision);
        statement.bindString(5, mobility);
        statement.bindString(6, difficulty);
        statement.bindString(7, solve_time);
        statement.bindString(8, attempt);
        statement.bindString(9, success);

        statement.executeInsert();
    }

    public Cursor getStat() {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("SELECT * FROM STAT", null);
    }
}
