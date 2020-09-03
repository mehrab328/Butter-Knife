package threemusketeers.butterknife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etext;
    ImageView imageView, ip1, ip2, ip3, ip4;
    Button btnChoose, bp1, bp2, bp3, bp4, btnAdd, btnPart;

    RadioGroup radiodifficulty;
    RadioButton radio;

    final int REQUEST_CODE_GALLERY = 999;
    final int REQUEST_CODE_GALLERY1 = 111;
    final int REQUEST_CODE_GALLERY2 = 222;
    final int REQUEST_CODE_GALLERY3 = 333;
    final int REQUEST_CODE_GALLERY4 = 444;

    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        init();


        sqLiteHelper = new SQLiteHelper(this, "CaptchaDB.sqlite", null, 1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS CAPTCHA(alphabet VARCHAR, image BLOB, captcha_difficulty VARCHAR)");
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS SUBCAPTCHA(part_name VARCHAR, part_image BLOB, part_alpha VARCHAR, subcaptcha_difficulty VARCHAR)");

        btnChoose.setOnClickListener(this);

        bp1.setOnClickListener(this);
        bp2.setOnClickListener(this);
        bp3.setOnClickListener(this);
        bp4.setOnClickListener(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(radiodifficulty.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getApplicationContext(), "Empty field detected", Toast.LENGTH_LONG).show();
                        return;
                    }

                    radio = (RadioButton) findViewById(radiodifficulty.getCheckedRadioButtonId());
                    String selected_difficulty = radio.getText().toString().trim();

                    //Toast.makeText(getApplicationContext(), selected_difficulty, Toast.LENGTH_LONG).show();

                    sqLiteHelper.insertData(etext.getText().toString().trim(), imageViewToByte(imageView), selected_difficulty);
                    Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_LONG).show();
                    //etext.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (radiodifficulty.getCheckedRadioButtonId() == -1) return;

                    radio = (RadioButton) findViewById(radiodifficulty.getCheckedRadioButtonId());
                    String selected_difficulty = radio.getText().toString();

                    sqLiteHelper.insertData_subcaptcha(etext.getText().toString().trim() + "1", imageViewToByte(ip1), etext.getText().toString().trim(), selected_difficulty);
                    sqLiteHelper.insertData_subcaptcha(etext.getText().toString().trim() + "2", imageViewToByte(ip2), etext.getText().toString().trim(), selected_difficulty);
                    sqLiteHelper.insertData_subcaptcha(etext.getText().toString().trim() + "3", imageViewToByte(ip3), etext.getText().toString().trim(), selected_difficulty);
                    sqLiteHelper.insertData_subcaptcha(etext.getText().toString().trim() + "4", imageViewToByte(ip4), etext.getText().toString().trim(), selected_difficulty);

                    Toast.makeText(getApplicationContext(), "Subpart added successfully!", Toast.LENGTH_SHORT).show();

                    ip1.setImageResource(R.mipmap.ic_launcher);
                    ip2.setImageResource(R.mipmap.ic_launcher);
                    ip3.setImageResource(R.mipmap.ic_launcher);
                    ip4.setImageResource(R.mipmap.ic_launcher);
                    etext.setText("");
                    radiodifficulty.clearCheck();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (requestCode == REQUEST_CODE_GALLERY1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY1);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (requestCode == REQUEST_CODE_GALLERY2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY2);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (requestCode == REQUEST_CODE_GALLERY3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY3);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (requestCode == REQUEST_CODE_GALLERY4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY4);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CODE_GALLERY1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ip1.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CODE_GALLERY2 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ip2.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CODE_GALLERY3 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ip3.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CODE_GALLERY4 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ip4.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {

        etext = (EditText) findViewById(R.id.eid);

        imageView = (ImageView) findViewById(R.id.imageView);
        ip1 = (ImageView) findViewById(R.id.ip1);
        ip2 = (ImageView) findViewById(R.id.ip2);
        ip3 = (ImageView) findViewById(R.id.ip3);
        ip4 = (ImageView) findViewById(R.id.ip4);

        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        bp1 = (Button) findViewById(R.id.bp1);
        bp2 = (Button) findViewById(R.id.bp2);
        bp3 = (Button) findViewById(R.id.bp3);
        bp4 = (Button) findViewById(R.id.bp4);
        btnPart = (Button) findViewById(R.id.btnPart);

        radiodifficulty = (RadioGroup) findViewById(R.id.radioDifficultyImage);
    }

    @Override
    public void onClick(View view) {
        if (view == btnChoose) {
            ActivityCompat.requestPermissions(
                    ImageActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY
            );
        }
        if (view == bp1) {
            ActivityCompat.requestPermissions(
                    ImageActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY1
            );
        }
        if (view == bp2) {
            ActivityCompat.requestPermissions(
                    ImageActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY2
            );
        }
        if (view == bp3) {
            ActivityCompat.requestPermissions(
                    ImageActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY3
            );
        }
        if (view == bp4) {
            ActivityCompat.requestPermissions(
                    ImageActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY4
            );
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ImageActivity.this, Info.class);
        startActivity(intent);
        finish();
    }
}



