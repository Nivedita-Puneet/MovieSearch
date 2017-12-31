package moviesearch.example.com.moviesearch.data;

/**
 * Created by PUNEETU on 25-12-2017.
 */

public class MovieDetail {

    private String title;
    private String poster;
    private String description;
    private String year;
    private String trailer;


    public MovieDetail(String title, String poster, String description, String year) {
        this.title = title;
        this.poster = poster;
        this.description = description;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }


}
