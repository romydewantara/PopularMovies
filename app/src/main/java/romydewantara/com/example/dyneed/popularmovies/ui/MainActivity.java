package romydewantara.com.example.dyneed.popularmovies.ui;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import romydewantara.com.example.dyneed.popularmovies.R;
import romydewantara.com.example.dyneed.popularmovies.adapter.MovieAdapter;
import romydewantara.com.example.dyneed.popularmovies.model.PopularMovies;

public class MainActivity extends AppCompatActivity {

    //API KEY hidden according to guidline rules
    String API_URL = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=xxxxxxxxx";

    public ArrayList<PopularMovies> list_movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView gridView = findViewById(R.id.layout_gridview);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                JSONArray results = null;
                try {
                    results = response.getJSONArray("results");
                    for (int x=0; x<results.length(); x++) {
                        JSONObject jsonObject = results.getJSONObject(x);

                        String title = jsonObject.getString("title");
                        String posterPath = "http://image.tmdb.org/t/p/w640" + jsonObject.getString("poster_path");
                        String releaseDate = jsonObject.getString("release_date");
                        Double voteAverage = jsonObject.getDouble("vote_average");
                        String overview = jsonObject.getString("overview");

                        PopularMovies popularMovies = new PopularMovies(title, posterPath, releaseDate, voteAverage, overview);
                        list_movies.add(popularMovies);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this, list_movies);

                gridView.setAdapter(movieAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Error!");
            }
        });
        Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_exit, null);
        Button btn_yes = view.findViewById(R.id.btn_yes);
        Button btn_no = view.findViewById(R.id.btn_no);
        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
