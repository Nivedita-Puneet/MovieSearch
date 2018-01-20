package moviesearch.example.com.moviesearch.data;

/**
 * Created by PUNEETU on 20-01-2018.
 */

public class Tvshow {

    private String poster;
    private String title;
    private String releaseDate;
    private String plot;

    public Tvshow(String title, String poster, String releaseDate, String plot) {

        this.title = title;
        this.poster = poster;
        this.releaseDate = releaseDate;
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }


}
