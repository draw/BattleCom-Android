package net.euroboxonline.battlecom;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView( R.layout.activity_map );

		try {
			// Loading map
			initilizeMap();

			// Changing map type
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

			// Showing / hiding your current location
			// googleMap.setMyLocationEnabled( false );

			// Enable / Disable zooming controls
            googleMap.getUiSettings().setZoomControlsEnabled( false );

			// Enable / Disable my location button
            googleMap.getUiSettings().setMyLocationButtonEnabled( false );

			// Enable / Disable Compass icon
            googleMap.getUiSettings().setCompassEnabled( true );

			// Enable / Disable Rotate gesture
            googleMap.getUiSettings().setRotateGesturesEnabled( true );

			// Enable / Disable zooming functionality
            googleMap.getUiSettings().setZoomGesturesEnabled( true );

            googleMap.setOnMapClickListener( new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick( LatLng latLng )
                {
                    MarkerOptions marker = new MarkerOptions().position( latLng )
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
					        .title("Objective Marker");

                    googleMap.addMarker( marker );
                }
            });

            googleMap.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick( Marker marker )
                {
                    marker.remove();

                    return false;
                }
            });

//            googleMap.setOnMyLocationChangeListener( new GoogleMap.OnMyLocationChangeListener() {
//                @Override
//                public void onMyLocationChange(Location location)
//                {
//                    CameraPosition cameraPosition = new CameraPosition.Builder()
//                            .target(new LatLng(location.getLatitude(), location.getLongitude())).build();
//
//                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                }
//            });

//            googleMap.setLocationSource( new LocationSource() {
//                @Override
//                public void activate(OnLocationChangedListener onLocationChangedListener)
//                {
//
//                }
//
//                @Override
//                public void deactivate()
//                {
//
//                }
//            });

			double latitude = 51.069175;    //51.07224    51.06611
			double longitude = -0.864745;    //-0.87317    -0.85632

            BattleComApplication app = (BattleComApplication) getApplicationContext();

            Zone zone = (Zone) app.retrieve( "gameZone" );

            if ( zone == null )
            {
                Toast.makeText( getApplicationContext(), "No Zone Info loaded", Toast.LENGTH_LONG ).show();
            }
            else
            {
                addZone( zone, Color.RED );
            }

            zone = (Zone) app.retrieve( "safeZone" );

            if ( zone != null )
            {
                addZone( zone, Color.BLUE );
            }

            zone = (Zone) app.retrieve( "respawn" );

            if ( zone != null )
            {
                addZone( zone, Color.GREEN );
            }

            Location location = (Location) app.retrieve( "location" );

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            MarkerOptions marker = new MarkerOptions().position( new LatLng(latitude, longitude) )
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE ) )
                    .title("Trakker");

            googleMap.addMarker( marker );

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latitude, longitude)).zoom((float) 17.8).build();

            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

        }
        catch (Exception e)
        {
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/*
	 * creating random postion around a location for testing purpose only
	 */
	private double[] createRandLocation(double latitude, double longitude) {

		return new double[] { latitude + ((Math.random() - 0.5) / 500),
				longitude + ((Math.random() - 0.5) / 500),
				150 + ((Math.random() - 0.5) * 10) };
	}


    private void addZone( Zone zone, int color )
    {
        List<LatLng> points = new ArrayList<LatLng>();

        for( Location location : zone.getCoordinates() )
        {
            points.add(  new LatLng( location.getLatitude(), location.getLongitude() ) );
        }

        googleMap.addPolyline( new PolylineOptions().color( color ).width( 2 ).addAll(points) );
    }

}
