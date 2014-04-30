package com.example.helloworld;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	final String KEY_USERNAME = "username_key";
	final String KEY_PASSWORD = "password_key";

	private final String TAG = MainActivity.class.getSimpleName();
	
	private Button viderButton;
	private Button envoyerButton;

	private EditText userEdit;
	private EditText mdpEdit;

	private TextView errorField;

	ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.i(TAG, "onCreate!");

		viderButton = (Button) findViewById(R.id.vider);
		envoyerButton = (Button) findViewById(R.id.envoyer);

		userEdit = (EditText) findViewById(R.id.userEdit);
		mdpEdit = (EditText) findViewById(R.id.mdpEdit);

		errorField = (TextView) findViewById(R.id.error_field);
		
		progressBar = (ProgressBar) findViewById(R.id.loader);

		// METHODE POUR VIDER CHAMPS
		viderButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userEdit.setText(null);
				mdpEdit.setText(null);
			}
		});

		// METHODE POUR ENVOYER CHAMPS
		envoyerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String username = userEdit.getText().toString();
				String password = mdpEdit.getText().toString();
				
				if (username.equals(""))
					userEdit.setError("!");

				if (password.equals(""))
					mdpEdit.setError("!");

				if (!password.equals("")
						&& !username.equals("")) {
					Toast.makeText(MainActivity.this, "Toast !",
							Toast.LENGTH_SHORT).show();
					
					
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString(KEY_USERNAME, username);
					editor.putString(KEY_PASSWORD, password);
					editor.commit();
					
					
					
					
					new ParlezVousTask().execute(userEdit.getText().toString(), mdpEdit.getText().toString());
					//new ParlezVousTask().execute("user", "password");
	
					errorField.setVisibility(View.INVISIBLE);
				} else
					errorField.setVisibility(View.VISIBLE);
			}
		});
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		userEdit.setText(preferences.getString(KEY_USERNAME, ""));
		mdpEdit.setText(preferences.getString(KEY_PASSWORD, ""));
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		
		
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy!");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(TAG, "onPause!");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "onResume!");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		if(errorField.isShown())
			outState.putBoolean("error", true);
		
		Log.i(TAG, "onSaveInstanceState!");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		
		if(savedInstanceState.getBoolean("error"))
			errorField.setVisibility(View.VISIBLE);
		
		Log.i(TAG, "onRestoreInstanceState!");
	}

	public class ParlezVousTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);

		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub

			
			String user = params[0];
			String password = params[1];

			try {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet(
						"http://dev.loicortola.com/parlez-vous-android/connect/"
								+ user + "/" + password);
				HttpResponse response = client.execute(request);

				InputStream content = response.getEntity().getContent();
				String resultat = InputStreamToString.convert(content);

				return resultat.equals("true");

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result)
			{
				Intent intent = new Intent(MainActivity.this, SecondActivity.class);
				intent.putExtra("nom", userEdit.getText().toString());
				startActivity(intent);
			}
			
			Log.i(TAG, "Resultat : "+result+" !!!!!!!!!!!");
			progressBar.setVisibility(View.INVISIBLE);
		}

	}
}
