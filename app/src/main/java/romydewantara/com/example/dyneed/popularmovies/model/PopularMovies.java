package romydewantara.com.example.dyneed.popularmovies.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PopularMovies {

    private String title;
    private String posterPath;
    private String releaseDate;
    private Double voteAverage;
    private String overview;

    public PopularMovies(String title, String posterPath, String releaseDate, Double voteAverage, String overview) {
        this.title = title;
        this.posterPath = posterPath;
        setReleaseDate(releaseDate);
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        SimpleDateFormat formatDefault = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");

        try {
            Date YearFormat = formatDefault.parse(releaseDate);
            releaseDate = formatYear.format(YearFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.releaseDate = releaseDate;
    }
    //ddasdasdas

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }
}
