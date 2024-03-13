package dtos;

import entity.Episode;

import java.util.Date;

public class episodeDtos extends Episode {
    private int filmId;

    public episodeDtos() {
    }

    public episodeDtos(int epId, String epTittle, String epLink, Date epDate, int filmId) {
        super(epId, epTittle, epLink, epDate);
        this.filmId = filmId;
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
                "filmId=" + filmId +
                '}';
    }
}
