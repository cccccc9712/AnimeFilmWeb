package dtos;

import entity.Episode;
import entity.Season;

import java.util.ArrayList;
import java.util.List;

public class seasonDtos extends Season {

    private int filmId;
    private List<Episode> episodes = new ArrayList<>();

    public seasonDtos() {
        super();
        this.episodes = new ArrayList<>();
    }

    public seasonDtos(int seasonID, String seasonName, int filmId, List<Episode> episodes) {
        super(seasonName);
        setSeasonID(seasonID);
        this.filmId = filmId;
        this.episodes = episodes;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return "seasonDtos{" +
                "filmId=" + filmId +
                ", episodes=" + episodes +
                '}';
    }
}
