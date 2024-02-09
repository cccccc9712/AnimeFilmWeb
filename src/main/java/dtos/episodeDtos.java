package dtos;

import java.sql.Date;

public class episodeDtos {
    private int epId;
    private String epTittle;
    private String epLink;
    private Date epDate;
    private int filmId;

    public episodeDtos() {
    }

    public episodeDtos(int epId, String epTittle, String epLink, Date epDate, int filmId) {
        this.epId = epId;
        this.epTittle = epTittle;
        this.epLink = epLink;
        this.epDate = epDate;
        this.filmId = filmId;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public String getEpTittle() {
        return epTittle;
    }

    public void setEpTittle(String epTittle) {
        this.epTittle = epTittle;
    }

    public String getEpLink() {
        return epLink;
    }

    public void setEpLink(String epLink) {
        this.epLink = epLink;
    }

    public Date getEpDate() {
        return epDate;
    }

    public void setEpDate(Date epDate) {
        this.epDate = epDate;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    @Override
    public String toString() {
        return "episodeDtos{" +
                "epId=" + epId +
                ", epTittle='" + epTittle + '\'' +
                ", epLink='" + epLink + '\'' +
                ", epDate=" + epDate +
                ", filmId=" + filmId +
                '}';
    }
}
