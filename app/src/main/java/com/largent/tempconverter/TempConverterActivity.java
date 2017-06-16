package com.largent.tempconverter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class TempConverterActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    private static final String TAG = "TempConverterActivity";

    //define our widget variables
    private EditText fahInputET;
    private TextView celResultTV;


    //define instance variable
    private String fahInputString = "";
    private float celResult = 0;
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        //get reference to the widgets
        fahInputET = (EditText) findViewById(R.id.fahInputET);
        celResultTV = (TextView) findViewById(R.id.celResultTV);

        //set the listener for the event
        fahInputET.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

    }



    private void calculateAndDisplay() {

        //get Fahrenheit from user
        fahInputString = fahInputET.getText().toString();
        float fahInput;
        if(fahInputString.equals("")){
            fahInput = 0;
        } else {
            fahInput = Float.parseFloat(fahInputString);
        }

        Log.d(TAG, "Fahrenheit: " + fahInput);

        //do the math
        celResult = ((fahInput-32) * 5/9);
        Toast.makeText(getApplicationContext(), "Celsius" + celResult, Toast.LENGTH_LONG).show();

        // format and display
        celResultTV.setText(String.valueOf(celResult));

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay();
        }

        //hide soft keyboard
        return false;
    }

    protected void onPause(){

        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fahInputString", fahInputString);
        editor.commit();

        super.onPause();
    }

    protected void onResume() {
        super.onResume();

        fahInputString = savedValues.getString("fahInputString", "");
        fahInputET.setText(fahInputString);
        calculateAndDisplay();
    }
}
