package threemusketeers.butterknife;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Listener, View.OnClickListener {

    @BindView(R.id.rvTop)
    RecyclerView rvTop;
    @BindView(R.id.rvBottom)
    RecyclerView rvBottom;
    @BindView(R.id.tvEmptyListTop)
    TextView tvEmptyListTop;
    @BindView(R.id.tvEmptyListBottom)
    TextView tvEmptyListBottom;

    Button b2, b3;
    Random rand;
    SQLiteHelper db;
    ImageView captcha_image;

    Chronometer chronometer;

    public static List<String> xyData;

    int attempted = 0, success = 0;

    //public static ImageView test;
    public static Context ctx;

    String letter;
    Bitmap b;
    List<Bitmap> topList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        init();

        xyData = new ArrayList<String>();

        viewImage();

        initTopRecyclerView();
        initBottomRecyclerView();

        //isExternalStorage();

        tvEmptyListTop.setVisibility(View.GONE);
        tvEmptyListBottom.setVisibility(View.GONE);


        //b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

    }

    private void init() {
        db = new SQLiteHelper(this, "CaptchaDB.sqlite", null, 1);

        //b1 = (Button) findViewById(R.id.insert);
        b2 = (Button) findViewById(R.id.recaptcha);
        b3 = (Button) findViewById(R.id.submit);
        captcha_image = (ImageView) findViewById(R.id.captcha_image);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        //chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        //test = (ImageView) findViewById(R.id.test);
        ctx = getApplicationContext();

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 76);

    }

    public void viewImage() {
        //rand = new Random();
        Cursor c = db.getAlphabet(Info.difficulty.trim());


        if (c.moveToNext()) {
            byte[] image = c.getBlob(1);
            letter = c.getString(c.getColumnIndex("alphabet")).trim();

            //Toast.makeText(this, "LETTER = " + letter, Toast.LENGTH_LONG).show();

            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            //b=bmp;
            captcha_image.setImageBitmap(bmp);
            attempted++;

            //Toast.makeText(this, letter, Toast.LENGTH_SHORT).show();

        } else {
            captcha_image.setImageResource(R.mipmap.ic_launcher);
            //Toast.makeText(this, "SAME", Toast.LENGTH_SHORT).show();
        }


    }

    private void initTopRecyclerView() {

        //rvTop.setHasFixedSize(true);
        //rvTop.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        //rvTop.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvTop.setLayoutManager(new GridLayoutManager(this, 2));
        //rvTop.setBackgroundColor(0xFFE8E6E7);

        rvTop.addItemDecoration(new SpacesItemDecoration());

        //List<String> topList = new ArrayList<>();
        topList = new ArrayList<>();


        //topList.add("A");
        //topList.add("B");


        ListAdapter topListAdapter = new ListAdapter(topList, this);
        rvTop.setAdapter(topListAdapter);
        tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
        rvTop.setOnDragListener(topListAdapter.getDragInstance());

    }

    /*private void initBottomRecyclerView() {
        rvBottom.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false));

        List<String> bottomList = new ArrayList<>();
        bottomList.add("C");
        bottomList.add("D");

        ListAdapter bottomListAdapter = new ListAdapter(bottomList, this);
        rvBottom.setAdapter(bottomListAdapter);
        tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance());
        rvBottom.setOnDragListener(bottomListAdapter.getDragInstance());
    }*/

    private void initBottomRecyclerView() {
        //rvBottom.setLayoutManager(new GridLayoutManager(this, 4));

        //rvBottom.setHasFixedSize(true);
        //rvBottom.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        //rvBottom.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //rvBottom.addItemDecoration(new SpacesItemDecoration());
        rvBottom.setLayoutManager(new GridLayoutManager(this, 4));


        List<Bitmap> bottomList = new ArrayList<>();

        // Toast.makeText(this, "LETTER = "+letter, Toast.LENGTH_LONG).show();

        Cursor c = db.getsubImagePart(letter.trim(), Info.difficulty.trim());

        while (c.moveToNext()) {
            byte[] blob = c.getBlob(1);
            //Toast.makeText(getApplicationContext(), c.getString(2), Toast.LENGTH_LONG).show();
            //ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
            //Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            b = BitmapFactory.decodeByteArray(blob, 0, blob.length);

            bottomList.add(b);

            //captcha_image.setImageBitmap(bmp);


        }

        c = db.getExtraSubImagePart(letter.trim(), Info.difficulty.trim());

        while (c.moveToNext()) {
            byte[] blob = c.getBlob(1);
            //Toast.makeText(getApplicationContext(), c.getString(2), Toast.LENGTH_LONG).show();
            //ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
            //Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            b = BitmapFactory.decodeByteArray(blob, 0, blob.length);

            bottomList.add(b);

            //captcha_image.setImageBitmap(bmp);


        }

       // Collections.shuffle(bottomList);
        ListAdapter bottomListAdapter = new ListAdapter(bottomList, this);
        rvBottom.setAdapter(bottomListAdapter);
        tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance());
        rvBottom.setOnDragListener(bottomListAdapter.getDragInstance());

    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        tvEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvTop.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        tvEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvBottom.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        /*if (view == b1) {
            Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
            startActivity(intent);
            finish();
        }*/
        if (view == b2) {

            viewImage();
            initBottomRecyclerView();
            initTopRecyclerView();
        }
        if (view == b3) {
            if (topList.size() != 4) {
                Toast.makeText(getApplicationContext(), "Pick Exactly Four Items!!!", Toast.LENGTH_SHORT).show();
            } else {
                byte[] b1 = bitmapTobyte(DragListener.p1);
                byte[] b2 = bitmapTobyte(DragListener.p2);
                byte[] b3 = bitmapTobyte(DragListener.p3);
                byte[] b4 = bitmapTobyte(DragListener.p4);

                Cursor c = db.matchCaptcha(letter.trim(), Info.difficulty.trim());

                byte[] c1, c2, c3, c4;

                c.moveToNext();
                c1 = c.getBlob(1);

                c.moveToNext();
                c2 = c.getBlob(1);

                c.moveToNext();
                c3 = c.getBlob(1);

                c.moveToNext();
                c4 = c.getBlob(1);

                if (Arrays.equals(b1, c1) && Arrays.equals(b2, c2) && Arrays.equals(b3, c3) && Arrays.equals(b4, c4)) {
                    Toast.makeText(getApplicationContext(), "Captcha Matched!!!", Toast.LENGTH_LONG).show();
                    success = 1;
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Captcha!!!", Toast.LENGTH_LONG).show();
                }
                chronometer.stop();
                inserttoDB();
                Intent intent = new Intent(this, Status.class);
                startActivity(intent);
                finish();
            }

        }
    }

    private void inserttoDB() {

        db.queryData("CREATE TABLE IF NOT EXISTS STAT(age VARCHAR, gender VARCHAR, user_type VARCHAR, " +
                "vision_problem VARCHAR, mobility_problem VARCHAR, difficulty VARCHAR, solve_time VARCHAR, attempt VARCHAR, " +
                "success VARCHAR)");

        int elapsedMillis = (int) (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;

        //Toast.makeText(getApplicationContext(), String.valueOf(elapsedMillis), Toast.LENGTH_LONG).show();


        //String temp_attempt = String.valueOf(attempted);
        //String temp_success = String.valueOf(success);


        db.insertDataStat(Info.age, Info.gender, Info.usertype, Info.vision, Info.mobility, Info.difficulty, String.valueOf(elapsedMillis),
                String.valueOf(attempted), String.valueOf(success));

        //Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
    }

    public static byte[] bitmapTobyte(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Info.class);
        startActivity(intent);
        finish();
    }
}
    



