package com.myapplicationdev.android.p10_ndpsongs_clv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etSingers, etYear;
    Button btnInsert, btnShowList;
//    RadioGroup rg;
    RatingBar ratingbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_main));

        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        btnInsert = findViewById(R.id.btnInsertSong);
        btnShowList = findViewById(R.id.btnShowList);
//        rg = findViewById(R.id.rgStars);
        ratingbar1 = (RatingBar) findViewById(R.id.ratingBar);

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString().trim();
                String singers = etSingers.getText().toString().trim();
                if (title.length() == 0 || singers.length() == 0) {
                    Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                    return;
                }

                String year_str = etYear.getText().toString().trim();
                int year = Integer.valueOf(year_str);
                int stars = getStars();


                DBHelper dbh = new DBHelper(MainActivity.this);
                long result = dbh.insertSong(title, singers, year, stars);
                dbh.close();


                if (result != -1) {
                    Toast.makeText(MainActivity.this, "Song inserted", Toast.LENGTH_LONG).show();
                    etTitle.setText("");
                    etSingers.setText("");
                    etYear.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private int getStars() {
        int stars = (int)ratingbar1.getRating();
        return stars;
    }

}