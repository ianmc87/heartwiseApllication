package ie.app.heartwise.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import ie.app.heartwise.R;

/**
 * Created by Ian on 20/04/2017.
 */

public class HomeScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        String username = getIntent().getStringExtra("Username");

        TextView tv =(TextView)findViewById(R.id.TVUsername);
        tv.setText(username);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onBackPressed()
    {
        finishAffinity();
    }

    public void addButtonClicked(View view){

        startActivity(new Intent(this, AddScreen.class));
    }

    public void searchButtonClicked(View view){

        startActivity(new Intent(this, SearchScreen.class));
    }

    public void listButtonClicked(View view){
        startActivity(new Intent(this, ListScreen.class));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuLogout : startActivity(new Intent(this, LoginScreen.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void home(MenuItem item)
    {
        startActivity(new Intent(this, HomeScreen.class));
    }

    public void logout(MenuItem item)
    {
        startActivity(new Intent(this, LoginScreen.class));
    }
}


