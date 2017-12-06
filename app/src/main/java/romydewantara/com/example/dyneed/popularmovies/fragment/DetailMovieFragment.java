package romydewantara.com.example.dyneed.popularmovies.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import romydewantara.com.example.dyneed.popularmovies.R;
import romydewantara.com.example.dyneed.popularmovies.ui.MainActivity;

public class DetailMovieFragment extends AppCompatActivity {
    //API KEY hidden according to guidline rules
    String API_URL_VIDEO = "https://api.themoviedb.org/3/movie/346364?&api_key=xxxxxxxxxxx&append_to_response=videos";

    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.img_movie) ImageView img_movie;
    @BindView(R.id.tv_daterelease) TextView tv_daterelease;
    @BindView(R.id.tv_rating) TextView tv_rating;
    @BindView(R.id.tv_overview) TextView tv_overview;
    @BindView(R.id.tv_durations) TextView tv_durations;
    @BindView(R.id.tv_trailer_list) TextView tv_trailer_list;
    @BindView(R.id.img_trailer) ImageView img_trailer;
    @BindView(R.id.linear_layout_trailer) LinearLayout linear_layout_trailer;

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_movie_detail);

        getSupportActionBar().setTitle("MovieDetail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        final String title = intent.getStringExtra("title");
        String posterPath = intent.getStringExtra("poster_path");
        String release_date = intent.getStringExtra("release_date");
        String overview = intent.getStringExtra("overview");
        Double vote_average = intent.getDoubleExtra("vote_average", 0);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL_VIDEO, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if (title.matches("It")){
                        String runtime = response.getString("runtime");
                        JSONObject results = response.getJSONObject("videos");
                        JSONArray resultLists = results.getJSONArray("results");
                        JSONObject resultIndex = resultLists.getJSONObject(0);
                        final String key = resultIndex.getString("key");

                        tv_durations.setText(runtime + "min");

                        tv_trailer_list.setText(Html.fromHtml("<a href=\"https://www.youtube.com/watch?v="+ key + "\">Play Trailer</a>"));
                        img_trailer.setImageResource(R.drawable.play);
                        linear_layout_trailer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + key));
                                startActivity(intent);
                            }
                        });

                    } else {
                        tv_trailer_list.setText("No Trailer Available");
                        img_trailer.setImageResource(R.drawable.notplay);
                        linear_layout_trailer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Toast toast  = Toast.makeText(DetailMovieFragment.this, "No Trailer Available", Toast.LENGTH_SHORT);
                                toast.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        toast.cancel();
                                    }
                                }, 800);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(DetailMovieFragment.this).add(jsonObjectRequest);

        button = findViewById(R.id.btn_favorite);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Toast toast  = Toast.makeText(DetailMovieFragment.this, "Added as a Favorite", Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 800);
            }
        });

        tv_title.setText(title);
        Picasso.with(getApplicationContext()).load(posterPath).into(img_movie);
        tv_daterelease.setText(release_date);
        tv_rating.setText(String.valueOf(vote_average) +"/10");
        tv_overview.setText(overview);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit_app:
                if( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
                    finish();
                } else {
                    finishAffinity();
                }
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
