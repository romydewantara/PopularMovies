package romydewantara.com.example.dyneed.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import romydewantara.com.example.dyneed.popularmovies.R;
import romydewantara.com.example.dyneed.popularmovies.fragment.DetailMovieFragment;
import romydewantara.com.example.dyneed.popularmovies.model.PopularMovies;

/**
 * Created by Romy Dewantara on 29/11/2017.
 */

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PopularMovies> list_image;

    public MovieAdapter(Context context, ArrayList<PopularMovies> list_image) {
        this.context = context;
        this.list_image = list_image;
    }

    @Override
    public int getCount() {
        return list_image.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.card_movie_list, null);

        ImageView imageView = v.findViewById(R.id.poster_card);
        Picasso.with(v.getContext()).load(list_image.get(i).getPosterPath()).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovieFragment.class);
                intent.putExtra("poster_path", list_image.get(i).getPosterPath());
                intent.putExtra("title", list_image.get(i).getTitle());
                intent.putExtra("vote_average", list_image.get(i).getVoteAverage());
                intent.putExtra("release_date", list_image.get(i).getReleaseDate());
                intent.putExtra("overview", list_image.get(i).getOverview());

                context.startActivity(intent);

            }
        });
        return v;

    }
}
