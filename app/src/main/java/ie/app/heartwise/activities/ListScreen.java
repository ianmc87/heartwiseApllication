package ie.app.heartwise.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import ie.app.heartwise.R;
import ie.app.heartwise.adapter.RecyclerAdapter;
import ie.app.heartwise.database.DatabaseHelper;
import ie.app.heartwise.model.Service;

/**
 * Created by Ian on 14/04/2017.
 */
public class ListScreen extends AppCompatActivity
{
    DatabaseHelper helper;
    List<Service> serviceList;
    RecyclerView myRecyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager myLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_screen);

        helper = new DatabaseHelper(this);
        serviceList = new ArrayList<Service>();
        serviceList = helper.getDataFromServiceTable();

        myRecyclerView = (RecyclerView)findViewById(R.id.recycleList);
        myRecyclerView.setHasFixedSize(true);

        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);

        myAdapter = new RecyclerAdapter(this, serviceList);
        myRecyclerView.setAdapter(myAdapter);
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
