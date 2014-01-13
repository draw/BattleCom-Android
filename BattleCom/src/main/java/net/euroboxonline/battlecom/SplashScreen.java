package net.euroboxonline.battlecom;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.euroboxonline.battlecom.network.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

    private Location location = null;

    private Zone gameZone = null;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

        new PrefetchData().execute();

//        new Handler().postDelayed(new Runnable() {
//
//			/*
//			 * Showing splash screen with a timer. This will be useful when you
//			 * want to show case your app logo / company
//			 */
//
//			@Override
//			public void run() {
//				// This method will be executed once the timer is over
//				// Start your app main activity
//				Intent i = new Intent( SplashScreen.this, MapActivity.class );
//				startActivity( i );
//
//				// close this activity
//				finish();
//			}
//		}, SPLASH_TIME_OUT);
	}


    /*
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls
            Log.e("JSON", "Pre execute");

        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            long id;
            double latitude;
            double longitude;

            String description = null;
            List<Location> coordinates = null;
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

			/*
			 * Will make http call here This call will download required data
			 * before launching the app
			 * example:
			 * 1. Downloading and storing SQLite
			 * 2. Downloading images
			 * 3. Parsing the xml / json
			 * 4. Sending device information to server
			 * 5. etc.,
			 */
            JsonParser jsonParser = new JsonParser();
            String json = jsonParser.getJSONFromUrl( "http://euroboxonline.net:9000/location" );

            Log.e("Response: ", "> " + json);

            location = gson.fromJson( json, Location.class );

            json = jsonParser.getJSONFromUrl( "http://euroboxonline.net:9000/zone" );

            Log.e("Response: ", "> " + json);

            gameZone = gson.fromJson( json, Zone.class );

            return null;
        }

        private List<Location> extractCoordinates( JSONArray array )
        {
            long id;
            double latitude;
            double longitude;

            List<Location> coordinates = new ArrayList<Location>();
            JSONObject rawCoord = null;

            try
            {
                for ( int count = 0; count < rawCoord.length(); count++)
                {
                    rawCoord = array.getJSONObject( count );

                    id = rawCoord.getLong("id");
                    latitude = rawCoord.getDouble( "latitude" );
                    longitude = rawCoord.getDouble( "longitude" );

                    coordinates.add( new Location( id, latitude, longitude ) );
                }
            }
            catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return coordinates;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            // After completing http call
            // will close this activity and lauch main activity

            BattleComApplication app = (BattleComApplication) getApplicationContext();

            app.save( "location", location );
            app.save( "gameZone", gameZone );

            Intent i = new Intent( SplashScreen.this, MapActivity.class );
            startActivity( i );

            // close this activity
            finish();
        }

    }

}
