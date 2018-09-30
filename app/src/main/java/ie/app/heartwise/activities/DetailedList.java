package ie.app.heartwise.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ie.app.heartwise.R;
import ie.app.heartwise.database.DatabaseHelper;
import ie.app.heartwise.model.Service;

/**
 * Created by Ian on 14/04/2017.
 */

public class DetailedList extends AppCompatActivity
{
    DatabaseHelper helper;
    List<Service> serviceList;
    int position;
    TextView idTextView, locationTextView, conditionTextView, dateTextView, timeTextView, latitudeTextView, longitudeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_list);

        displayDetailedList();
    }

    public void displayDetailedList()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        position = bundle.getInt("position");

        idTextView = (TextView) findViewById(R.id.id);
        locationTextView = (TextView) findViewById(R.id.location);
        conditionTextView = (TextView) findViewById(R.id.condition);
        dateTextView = (TextView) findViewById(R.id.date);
        timeTextView = (TextView) findViewById(R.id.time);
        latitudeTextView = (TextView) findViewById(R.id.latitude);
        longitudeTextView = (TextView) findViewById(R.id.longitude);

        helper = new DatabaseHelper(this);
        serviceList = new ArrayList<Service>();
        serviceList = helper.getDataFromServiceTable();

        if (serviceList.size() > 0)
        {
            String id = serviceList.get(position).getID();
            String location = serviceList.get(position).getLocation();
            String condition = serviceList.get(position).getCondition();
            String date = serviceList.get(position).getDate();
            String time = serviceList.get(position).getTime();
            String latitude = serviceList.get(position).getLatitude();
            String longitude = serviceList.get(position).getLongitude();

            idTextView.setText(id);
            locationTextView.setText(location);
            conditionTextView.setText(condition);
            dateTextView.setText(date);
            timeTextView.setText(time);
            latitudeTextView.setText(latitude);
            longitudeTextView.setText(longitude);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuHome : startActivity(new Intent(this, HomeScreen.class));
                break;
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
