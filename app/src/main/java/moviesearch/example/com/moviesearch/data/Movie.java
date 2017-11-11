package moviesearch.example.com.moviesearch.data;

/**
 * Created by PUNEETU on 02-03-2017.
 */

public class Movie {

    /*Define all the attributes to add it in a JSON*/

    private String title;
    private String year;
    private String poster;
    private String imdbID;


    public Movie(){
        //Default Constructor.
    }

    public Movie(String title, String year, String imdbID, String poster){
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.poster = poster;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

}
