package pl.edu.pwr.lab1mlynarczyk.lab1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;
import butterknife.ButterKnife;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.massEditText) EditText userMass;
    @BindView(R.id.heightEditText) EditText userHeight;
    @BindView(R.id.button) Button calcButton;
    @BindView(R.id.kgSwitch) SwitchCompat switchCompat;
    @BindView(R.id.messageTextView) TextView BMI;
    @BindView(R.id.opinionTextView) TextView opinion;
    @BindColor(R.color.red) ColorStateList red;
    @BindColor(R.color.yellow) ColorStateList yellow;
    @BindColor(R.color.orange) ColorStateList orange;
    @BindColor(R.color.green) ColorStateList green;
    @BindColor(R.color.colorPrimary) ColorStateList colorPrimary;
    @BindColor(R.color.colorAccent) ColorStateList colorAccent;
    @BindString(R.string.input_err) String input_err;
    @BindString(R.string.imperial_mass) String imperial_mass;
    @BindString(R.string.imperial_height) String imperial_height;
    @BindString(R.string.your_height) String your_height;
    @BindString(R.string.your_mass) String your_mass;
    @BindString(R.string.severe_underweight) String severe_underweight;
    @BindString(R.string.underweight) String underweight;
    @BindString(R.string.normal) String normal;
    @BindString(R.string.overweight) String overweight;
    @BindString(R.string.moderately_obese) String moderately_obese;
    @BindString(R.string.severely_obese) String severely_obese;
    @BindString(R.string.very_severely_obese) String very_severely_obese;
    @BindString(R.string.shared_preferences) String preferencesName;
    @BindString(R.string.preferences_mass) String prefMass;
    @BindString(R.string.preferences_height) String prefHeight;
    @BindString(R.string.preferences_check) String prefCheck;
    @BindString(R.string.bmi_charsequence) String BMICharSeq;
    @BindString(R.string.bmi_myshare) String shareBMI;
    @BindString(R.string.share_options) String shareOpt;
    @BindString(R.string.data_save) String dataSave;
    @BindString(R.string.retype) String retype;


    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pref = getSharedPreferences(preferencesName, Activity.MODE_PRIVATE);
        restoreData();

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String resultBMI;
                    float BMIMass = Float.valueOf(userMass.getText().toString());
                    float BMIHeight = Float.valueOf(userHeight.getText().toString());
                    float BMIFloat;
                    ICountBMI BMIResult;

                    if(switchCompat.isChecked()) {
                        BMIResult = new CountBMIForImperial();
                        BMIFloat = BMIResult.countBMI(BMIMass, BMIHeight);
                        resultBMI = String.format((Locale.US),"%1.2f", BMIFloat);
                    }
                    else {
                        BMIResult = new CountBMIForKGM();
                        BMIFloat = BMIResult.countBMI(BMIMass, BMIHeight);
                        resultBMI = String.format((Locale.US),"%1.2f", BMIFloat);
                    }

                    setOpinionAndColor(BMIFloat);
                    BMI.setText(resultBMI);
                    BMI.setVisibility(View.VISIBLE);

                }catch (IllegalArgumentException e) {
                    opinion.setText("");
                    BMI.setTextColor(colorPrimary);
                    BMI.setText(input_err);
                    BMI.setVisibility(View.VISIBLE);
                }

                finally {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchCompat.isChecked()){
                    userHeight.setHint(imperial_height);
                    userMass.setHint(imperial_mass);
                }
                else{
                    userHeight.setHint(your_height);
                    userMass.setHint(your_mass);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted
        if(BMI.getText().length()>0)
            savedInstanceState.putCharSequence(BMICharSeq,BMI.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        if(savedInstanceState.getCharSequence(BMICharSeq)==null) {
        }
        else if(savedInstanceState.getCharSequence(BMICharSeq).equals(input_err)){
            BMI.setText(input_err);
        }
        else {
            setOpinionAndColor(Float.valueOf(savedInstanceState.getCharSequence(BMICharSeq).toString()));
            BMI.setText(savedInstanceState.getCharSequence(BMICharSeq));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                if(!BMI.getText().toString().equals(input_err) && !BMI.getText().toString().equals("")) {
                    String shareBodyText = shareBMI + " " + BMI.getText().toString();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                    startActivity(Intent.createChooser(sharingIntent, shareOpt));
                    return true;
                }
                else  {
                    Toast.makeText(this, retype, Toast.LENGTH_SHORT).show();
                    return true;
                }

            case R.id.aboutToolbar:
                Intent itAU = new Intent(this, AuthorActivity.class);
                startActivity(itAU);
                return true;

            case R.id.save:
                saveData();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void restoreData(){
        userMass.setText(pref.getString(prefMass, ""));
        userHeight.setText(pref.getString(prefHeight, ""));
        switchCompat.setChecked(pref.getBoolean(prefCheck,false));
    }
    private void saveData(){
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString(prefMass,(userMass.getText().toString()));
        editor.putString(prefHeight,(userHeight.getText().toString()));
        editor.putBoolean(prefCheck,switchCompat.isChecked());
        editor.commit();
        Toast.makeText(this, dataSave, Toast.LENGTH_SHORT).show();
    }

    private void setOpinionAndColor(float bmi){
        if(bmi<16) {
            BMI.setTextColor(red);
            opinion.setTextColor(red);
            opinion.setText(severe_underweight);
        }
        else if(bmi<18.5){
            BMI.setTextColor(yellow);
            opinion.setTextColor(yellow);
            opinion.setText(underweight);
        }
        else if(bmi<25){
            BMI.setTextColor(green);
            opinion.setTextColor(green);
            opinion.setText(normal);
        }
        else if(bmi<30){
            BMI.setTextColor(yellow);
            opinion.setTextColor(yellow);
            opinion.setText(overweight);
        }
        else if(bmi<35){
            BMI.setTextColor(orange);
            opinion.setTextColor(orange);
            opinion.setText(moderately_obese);
        }
        else if(bmi<40){
            BMI.setTextColor(red);
            opinion.setTextColor(red);
            opinion.setText(severely_obese);
        }
        else{
            BMI.setTextColor(red);
            opinion.setTextColor(red);
            opinion.setText(very_severely_obese);
        }
    }
}



