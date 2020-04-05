package com.inmagine.shape;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.inmagine.shape.views.PixelGridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonShapeOne, buttonShapeTwo, buttonFilterOne, buttonFilterTwo, buttonFilterThree;
    PixelGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonShapeOne = (Button)findViewById(R.id.button_one);
        buttonShapeTwo = (Button)findViewById(R.id.button_two);
        buttonFilterOne = (Button) findViewById(R.id.button_filter_one);
        buttonFilterTwo = (Button) findViewById(R.id.button_filter_two);
        buttonFilterThree = (Button) findViewById(R.id.button_filter_three);

        gridView = (PixelGridView) findViewById(R.id.grid);

        buttonShapeOne.setOnClickListener(this);
        buttonShapeTwo.setOnClickListener(this);
        buttonFilterOne.setOnClickListener(this);
        buttonFilterTwo.setOnClickListener(this);
        buttonFilterThree.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_one:
                gridView.setNumRows(2);
                gridView.setNumColumns(2);
                break;
            case R.id.button_two:
                gridView.setNumRows(3);
                gridView.setNumColumns(3);

                break;
            case R.id.button_filter_one:
                gridView.setAlphaBlendFilter();
                gridView.invalidate();
                break;
            case R.id.button_filter_two:
                gridView.setHueFilter();
                gridView.invalidate();
                break;
            case R.id.button_filter_three:
                gridView.setBuldgeDistortionFilter();
                gridView.invalidate();
                break;

        }

    }
}
