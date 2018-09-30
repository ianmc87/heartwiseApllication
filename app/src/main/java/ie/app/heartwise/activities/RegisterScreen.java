package ie.app.heartwise.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ie.app.heartwise.R;
import ie.app.heartwise.database.DatabaseHelper;
import ie.app.heartwise.model.User;

/**
 * Created by Ian on 20/04/2017.
 */

public class RegisterScreen extends Activity
{
    DatabaseHelper helper;
    List<User> userList;
    Button RegisterButton;
    EditText tvName, tvUsername, tvPassword, tvPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        userList = new ArrayList<User>();

        tvName = (EditText)findViewById(R.id.RegName);
        tvUsername = (EditText)findViewById(R.id.RegUsername);
        tvPassword =(EditText)findViewById(R.id.RegPassword1);
        tvPassword2 = (EditText)findViewById(R.id.RegPassword2);
        RegisterButton =(Button)findViewById(R.id.RegisterButton);

        registerUser();
    }

    public void registerUser()
    {
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = tvName.getText().toString();
                String usernameString = tvUsername.getText().toString();
                String passwordString = tvPassword.getText().toString();
                String confirmPasswordString = tvPassword2.getText().toString();

                if(nameString.length()==0)
                {
                    tvName.requestFocus();
                    tvName.setError("Please enter your name");
                }
                else if(!nameString.matches("[a-zA-Z ]+"))
                {
                    tvName.requestFocus();
                    tvName.setError("No illegal characters valid");
                }
                else if(usernameString.length()==0)
                {
                    tvUsername.requestFocus();
                    tvUsername.setError("Please enter your username");
                }
                else if(usernameString.length() < 5)
                {
                    tvUsername.requestFocus();
                    tvUsername.setError("Your username must be between 5-10 characters");
                }
                else if(usernameString.length() > 10)
                {
                    tvUsername.requestFocus();
                    tvUsername.setError("Your username must be between 5-10 characters");
                }
                else if(passwordString.length()==0)
                {
                    tvPassword.requestFocus();
                    tvPassword.setError("Please enter your password");
                }
                else if(passwordString.length() < 6)
                {
                    tvPassword.requestFocus();
                    tvPassword.setError("Your password must be at least 6 characters in length");
                }
                else if(confirmPasswordString.length()==0)
                {
                    tvPassword2.requestFocus();
                    tvPassword2.setError("Please enter your password");
                }
                else if(confirmPasswordString.length() < 6)
                {
                    tvPassword2.requestFocus();
                    tvPassword2.setError("Your password must be at least 6 characters in length");
                }
                else if(!passwordString.equals(confirmPasswordString))
                {
                    tvPassword2.requestFocus();
                    tvPassword2.setError("Your passwords do not match");
                }
                else
                {
                    User user = new User();
                    user.setName(nameString);
                    user.setUsername(usernameString);
                    user.setPassword(passwordString);
                    user.setCpassword(confirmPasswordString);

                    helper = new DatabaseHelper(RegisterScreen.this);
                    helper.insertIntoUserTable(user);

                    Intent intent = new Intent(RegisterScreen.this,HomeScreen.class);
                    intent.putExtra("Username", usernameString);
                    startActivity(intent);
                    Toast.makeText(RegisterScreen.this, "User successfully created!", Toast.LENGTH_LONG).show();
                }
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