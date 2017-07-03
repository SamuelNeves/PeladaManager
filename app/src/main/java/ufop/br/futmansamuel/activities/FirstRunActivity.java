package ufop.br.futmansamuel.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ufop.br.futmansamuel.R;

public class FirstRunActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefEditor;
    EditText txtName,txtEmail;


    private void initComponents(){
        txtName= (EditText) findViewById(R.id.txtFirstRunName);
        txtEmail= (EditText) findViewById(R.id.txtFirstRunEmail);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        initComponents();
        setupSharedPreferences();
    }

    public void firstRunLogin(View view) {
        if(validadeInputs()){
            sharedPrefEditor.putBoolean("firstRun",false);

            Log.d("firstRun",sharedPreferences.getBoolean("firstRun", true)+"");
            sharedPrefEditor.putString("name",txtName.getText().toString());
            sharedPrefEditor.putString("email",txtEmail.getText().toString());
            sharedPrefEditor.commit();
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);
        }


    }

    public void firstRunExit(View view) {
        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
    }


    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences("settings",
                Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();
    }

    private  boolean   validadeInputs(){
        return true;
    }
}
