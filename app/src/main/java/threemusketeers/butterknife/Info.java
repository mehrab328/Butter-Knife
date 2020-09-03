package threemusketeers.butterknife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Info extends AppCompatActivity implements View.OnClickListener {

    RadioGroup radioGroupGender, radioGroupVision, radioGroupDifficulty, radioGroupMobility, radioGroupUserType;
    Button next, exit, insert;
    EditText e1;
    RadioButton r1;

    public static String age, gender, usertype, vision, mobility, difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        init();

        next.setOnClickListener(this);
        exit.setOnClickListener(this);
        insert.setOnClickListener(this);
    }

    private void init() {
        e1 = (EditText) findViewById(R.id.e1age);

        radioGroupGender = (RadioGroup) findViewById(R.id.radioSex);
        radioGroupUserType = (RadioGroup) findViewById(R.id.radioType);
        radioGroupVision = (RadioGroup) findViewById(R.id.radioVision);
        radioGroupMobility = (RadioGroup) findViewById(R.id.radioMobility);
        radioGroupDifficulty = (RadioGroup) findViewById(R.id.radioDifficulty);

        next = (Button) findViewById(R.id.next);
        exit = (Button) findViewById(R.id.exit);
        insert = (Button) findViewById(R.id.insert);
    }

    @Override
    public void onClick(View view) {
        if (view == exit) finish();
        else if (view == next) {

            if (e1.getText().toString().length() == 0 || radioGroupGender.getCheckedRadioButtonId() == -1 || radioGroupUserType.getCheckedRadioButtonId() == -1 ||
                    radioGroupVision.getCheckedRadioButtonId() == -1 || radioGroupMobility.getCheckedRadioButtonId() == -1 ||
                    radioGroupDifficulty.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Empty field detected", Toast.LENGTH_LONG).show();
                return;
            }

            age = e1.getText().toString();

            //gender
            r1 = (RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId());
            gender = r1.getText().toString();

            //User Type
            r1 = (RadioButton) findViewById(radioGroupUserType.getCheckedRadioButtonId());
            usertype = r1.getText().toString();

            //vision
            r1 = (RadioButton) findViewById(radioGroupVision.getCheckedRadioButtonId());
            vision = r1.getText().toString();

            //mobility
            r1 = (RadioButton) findViewById(radioGroupMobility.getCheckedRadioButtonId());
            mobility = r1.getText().toString();

            //difficulty
            r1 = (RadioButton) findViewById(radioGroupDifficulty.getCheckedRadioButtonId());
            difficulty = r1.getText().toString();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else if (view == insert) {
            Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
