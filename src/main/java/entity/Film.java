package entity;

import java.sql.Timestamp;
import java.util.Date;

public class Film {

    private int filmID;
    private String filmName;
    private String filmDescription;
    private Timestamp filmReleaseDate;
    private String filmImgLink;
    private String filmTrailerLink;
    private Long filmViewCount;

    public Film() {
    }

    public Film(int filmID, String filmName, String filmDescription, Timestamp filmReleaseDate, String filmImgLink, String filmTrailerLink, Long filmViewCount) {
        this.filmID = filmID;
        this.filmName = filmName;
        this.filmDescription = filmDescription;
        this.filmReleaseDate = filmReleaseDate;
        this.filmImgLink = filmImgLink;
        this.filmTrailerLink = filmTrailerLink;
        this.filmViewCount = filmViewCount;
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

    public String getFilmDescription() {
        return filmDescription;
    }

    public void setFilmDescription(String filmDescription) {
        this.filmDescription = filmDescription;
    }

    public Timestamp getFilmReleaseDate() {
        return filmReleaseDate;
    }

    public void setFilmReleaseDate(Timestamp filmReleaseDate) {
        this.filmReleaseDate = filmReleaseDate;
    }

    public String getFilmImgLink() {
        return filmImgLink;
    }

    public void setFilmImgLink(String filmImgLink) {
        this.filmImgLink = filmImgLink;
    }

    public String getFilmTrailerLink() {
        return filmTrailerLink;
    }

    public void setFilmTrailerLink(String filmTrailerLink) {
        this.filmTrailerLink = filmTrailerLink;
    }

    public Long getFilmViewCount() {
        return filmViewCount;
    }

    public void setFilmViewCount(Long filmViewCount) {
        this.filmViewCount = filmViewCount;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmID=" + filmID +
                ", filmName='" + filmName + '\'' +
                ", filmDescription='" + filmDescription + '\'' +
                ", filmReleaseDate=" + filmReleaseDate +
                ", filmImgLink='" + filmImgLink + '\'' +
                ", filmTrailerLink='" + filmTrailerLink + '\'' +
                ", filmViewCount='" + filmViewCount + '\'' +
                '}';
    }
}
