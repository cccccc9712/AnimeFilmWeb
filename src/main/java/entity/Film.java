package entity;

import java.sql.Timestamp;

public class Film {

    private int filmID;
    private String filmName;
    private String description;
    private Timestamp filmReleaseDate;
    private String imageLink;
    private String trailerLink;
    private Long viewCount;

    public Film() {
    }

    public Film(int filmID, String filmName, String description, Timestamp filmReleaseDate, String imageLink, String trailerLink, Long viewCount) {
        this.filmID = filmID;
        this.filmName = filmName;
        this.description = description;
        this.filmReleaseDate = filmReleaseDate;
        this.imageLink = imageLink;
        this.trailerLink = trailerLink;
        this.viewCount = viewCount;
    }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }


    public Timestamp getFilmReleaseDate() {
        return filmReleaseDate;
    }

    public void setFilmReleaseDate(Timestamp filmReleaseDate) {
        this.filmReleaseDate = filmReleaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmID=" + filmID +
                ", filmName='" + filmName + '\'' +
                ", description='" + description + '\'' +
                ", filmReleaseDate=" + filmReleaseDate +
                ", imageLink='" + imageLink + '\'' +
                ", trailerLink='" + trailerLink + '\'' +
                ", viewCount=" + viewCount +
                '}';
    }
}
