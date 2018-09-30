package ie.app.heartwise.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ie.app.heartwise.R;
import ie.app.heartwise.database.DatabaseHelper;
import ie.app.heartwise.model.Service;

/**
 * Created by Ian on 22/03/2017.
 */

public class AddScreen extends AppCompatActivity
{
    EditText editTextID, editTextLocation, editTextDate, editTextTime, editTextLatitude, editTextLongitude;
    RadioGroup radioGroup;
    Button buttonSubmit;
    DatabaseHelper helper;
    List<Service> serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_screen);

        serviceList = new ArrayList<Service>();

        editTextID = (EditText) findViewById(R.id.idET);
        editTextLocation = (EditText) findViewById(R.id.locationET);
        radioGroup =(RadioGroup)findViewById(R.id.radio_group);
        editTextDate = (EditText) findViewById(R.id.dateET);
        editTextTime = (EditText) findViewById(R.id.timeET);
        editTextLatitude = (EditText) findViewById(R.id.latitudeET);
        editTextLongitude = (EditText) findViewById(R.id.longitudeET);
        buttonSubmit = (Button) findViewById(R.id.submitButton);

        addServiceToDatabase();
    }

    public void addServiceToDatabase()
    {
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String id = editTextID.getText().toString();
                String location = editTextLocation.getText().toString();
                String radioValue = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                String date = editTextDate.getText().toString();
                String time = editTextTime.getText().toString();
                String latitude = editTextLatitude.getText().toString();
                String longitude = editTextLongitude.getText().toString();

                if(id.length() == 0)
                {
                    editTextID.requestFocus();
                    editTextID.setError("Please enter the AED ID");
                }
                else if(id.length() > 5)
                {
                    editTextID.requestFocus();
                    editTextID.setError("The ID must be less than 6 characters");
                }
                else if(location.length() == 0)
                {
                    editTextLocation.requestFocus();
                    editTextLocation.setError("Please enter the AED location");
                }
                else if(date.length() == 0)
                {
                    editTextDate.requestFocus();
                    editTextDate.setError("Please enter the date");
                }
                else if(time.length() == 0)
                {
                    editTextTime.requestFocus();
                    editTextTime.setError("Please enter the time");
                }
                else if(latitude.length() == 0)
                {
                    editTextLatitude.requestFocus();
                    editTextLatitude.setError("Please enter location latitude");
                }
                else if(longitude.length() == 0)
                {
                    editTextLongitude.requestFocus();
                    editTextLongitude.setError("Please enter location longitude");
                }
                else
                {
                    Service service = new Service();  //creating new Service object
                    service.setID(id);
                    service.setLocation(location);
                    service.setCondition(radioValue);
                    service.setDate(date);
                    service.setTime(time);
                    service.setLatitude(latitude);
                    service.setLongitude(longitude);

                    helper = new DatabaseHelper(AddScreen.this);
                    helper.insertIntoServiceTable(service);
                }
                editTextID.setText("");
                editTextLocation.setText("");
                editTextDate.setText("");
                editTextTime.setText("");
                editTextLatitude.setText("");
                editTextLongitude.setText("");
                Toast.makeText(AddScreen.this, "You have added service details successfully!", Toast.LENGTH_LONG);
            }
        });
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

