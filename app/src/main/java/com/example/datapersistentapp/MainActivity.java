package com.example.datapersistentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static String FILE_NAME = "";
    //    public static String[] items=new String[]{"aman","arindam","test1","test2","anshul","mam"};
    public static String[] items = new String[]{""};

    public static List<String> fileList = new ArrayList<String>();
    EditText mEditText, Filename;
    Button createButton;
    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.edit_text);
        createButton = findViewById(R.id.createButton);
//        createButton.setEnabled(false);
        Filename = findViewById(R.id.filename);
        dropdown = findViewById(R.id.spinner1);
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
//set the spinners adapter to the previously created one.
        items = fileList.toArray(new String[fileList.size()]);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
//        if(Filename.getText().toString().length()>0){
//
//            createButton.setEnabled(true);
//        }

        Toast.makeText(this, "Made with ❤️ By Aman & Anshul", Toast.LENGTH_LONG).show();
    }

    public void create(View v) {

        mEditText.setText("");
        FILE_NAME = Filename.getText().toString();
        if (Filename.getText().toString().length() <= 0) {

            Toast.makeText(this, "Please Enter some File Name", Toast.LENGTH_SHORT).show();
        } else if (fileList.contains(FILE_NAME)) {
            Toast.makeText(this, "File Already Exist", Toast.LENGTH_SHORT).show();
        } else {
            fileList.add(FILE_NAME);
            items = fileList.toArray(new String[fileList.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropdown.setAdapter(adapter);
            dropdown.setOnItemSelectedListener(this);
            mEditText.setText("");

//            for(int i=0;i<fileList.size();i++){items[i]=fileList.get(i); }
            List t = Arrays.asList(items);
            Collections.reverse(t);
            items = (String[]) t.toArray(new String[fileList.size()]);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dropdown.setAdapter(adapter);
            Toast.makeText(this, "File Successfully Created", Toast.LENGTH_SHORT).show();

        }
    }

    public void open(View v) {
        FILE_NAME = Filename.getText().toString();
        if (Filename.getText().toString().length() <= 0) {
            Toast.makeText(this, "Please Enter some File Name", Toast.LENGTH_SHORT).show();
        } else if (fileList.contains(FILE_NAME)) {

            Toast.makeText(this, "File Successfully Opened", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "File Does Not Exist", Toast.LENGTH_SHORT).show();
        }

    }

    public void save(View v) {
        String text = mEditText.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            mEditText.getText().clear();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(View v) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            mEditText.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        FILE_NAME = items[position];
        Filename.setText(FILE_NAME);
        load(view);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}