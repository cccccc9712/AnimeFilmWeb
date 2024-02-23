package dtos;

import entity.Episode;

import java.util.ArrayList;
import java.util.List;

public class seasonDtos {

    private int filmId;

    private int seasonId;
    private String seasonName;
    private List<Episode> episodes = new ArrayList<>();

    public seasonDtos() {
    }

    public seasonDtos(int filmId, int seasonId, String seasonName, List<Episode> episodes) {
        this.filmId = filmId;
        this.seasonId = seasonId;
        this.seasonName = seasonName;
        this.episodes = episodes;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
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
                ", seasonId=" + seasonId +
                ", seasonName='" + seasonName + '\'' +
                ", episodes=" + episodes +
                '}';
    }
}
