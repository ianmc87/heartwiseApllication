package ie.app.heartwise.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ie.app.heartwise.R;
import ie.app.heartwise.database.DatabaseHelper;

/**
 * Created by Ian on 02/04/2017.
 */

public class LoginScreen extends AppCompatActivity
{
    DatabaseHelper helper = new DatabaseHelper(this);
    EditText loginUsername, loginPassword;
    Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        loginUsername = (EditText)findViewById(R.id.LoginUsername);
        loginPassword = (EditText)findViewById(R.id.LoginPassword);
        LoginButton = (Button)this.findViewById(R.id.LoginButton);

        loginToApplication();

    }

    public void loginToApplication()
    {

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();

                String passwordChecker = helper.passwordCheck(login);

                if(password.equals(passwordChecker))
                {
                    Intent intent = new Intent(LoginScreen.this,HomeScreen.class);
                    intent.putExtra("Username", login);
                    startActivity(intent);
                    Toast.makeText(LoginScreen.this, "You have successfully logged in!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(LoginScreen.this, "Username and password do not exist!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void registerButtonClicked(View view)
    {
        Intent intent = new Intent(LoginScreen.this,RegisterScreen.class);
        startActivity(intent);
    }
}
