package threemusketeers.butterknife;

import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Status extends AppCompatActivity implements View.OnClickListener {

    SQLiteHelper db;

    TextView tage, tgender, tusertype, tvision, tmobility, tdifficulty, ttime, tattempt, toutcome;

    Button try_again, exit;

    String coords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        init();

        coords = "";

        fetch();

        try_again.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    private void fetch() {
        Cursor c = db.getStat();

        if (c.moveToLast()) {
            //tid.setText(String.valueOf(c.getInt(c.getColumnIndex("id"))));
            tage.setText(c.getString(c.getColumnIndex("age")));
            tgender.setText(c.getString(c.getColumnIndex("gender")));
            tusertype.setText(c.getString(c.getColumnIndex("user_type")));
            tvision.setText(c.getString(c.getColumnIndex("vision_problem")));
            tmobility.setText(c.getString(c.getColumnIndex("mobility_problem")));
            tdifficulty.setText(c.getString(c.getColumnIndex("difficulty")));
            ttime.setText(c.getString(c.getColumnIndex("solve_time")));
            tattempt.setText(c.getString(c.getColumnIndex("attempt")));

            if (c.getString(c.getColumnIndex("success")).equals("1"))
                toutcome.setText("SUCCESSFUL");
            else toutcome.setText("UNSUCCESSFUL");

            coords = "Age : " + tage.getText().toString() + ", Gender : " + tgender.getText().toString() + ", Vision : " + tvision.getText().toString()
                    + ", Hand : " + tmobility.getText().toString() + ", Mode : " + tdifficulty.getText().toString()
                    + ", Attempt: " + tattempt.getText().toString() + "\n" + MainActivity.xyData.toString() + "\n" + toutcome.getText().toString() + "\n\n";
        }

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File myExFile = new File(cw.getExternalFilesDir("/HandMotionData"), "motionData.txt");

        try {
            FileOutputStream fos = new FileOutputStream(myExFile, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            outputStreamWriter.append(coords);
            outputStreamWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {

        db = new SQLiteHelper(this, "CaptchaDB.sqlite", null, 1);

        tage = (TextView) findViewById(R.id.tage);
        tgender = (TextView) findViewById(R.id.tgender);
        tusertype = (TextView) findViewById(R.id.tusertype);
        tvision = (TextView) findViewById(R.id.tvision);
        tmobility = (TextView) findViewById(R.id.tmobility);
        tdifficulty = (TextView) findViewById(R.id.tdifficulty);
        ttime = (TextView) findViewById(R.id.ttime);
        tattempt = (TextView) findViewById(R.id.tattempt);
        toutcome = (TextView) findViewById(R.id.tsuccess);

        try_again = (Button) findViewById(R.id.btry);
        exit = (Button) findViewById(R.id.bexit);
    }

    @Override
    public void onClick(View view) {
        if (view == try_again) {
            Intent intent = new Intent(getApplicationContext(), Info.class);
            startActivity(intent);
            finish();
        } else if (view == exit) {
            System.exit(0);
        }
    }
}
