package dtos;

import entity.*;

import java.sql.Timestamp;
import java.util.List;

public class filmDtos extends Film {

    private float ratingValue;
    private List<Category> categories;
    private List<Tag> tags;
    private List<Season> seasons;
    private List<Episode> episodes;

    public filmDtos() {
    }

    public filmDtos(int filmID, String filmName, String filmDescription, Timestamp filmReleaseDate, String filmImgLink, String filmTrailerLink, Long filmViewCount, float rating, List<Category> categories, List<Tag> tags, List<Season> seasons, List<Episode> episodes) {
        super(filmID, filmName, filmDescription, filmReleaseDate, filmImgLink, filmTrailerLink, filmViewCount);
        this.ratingValue = rating;
        this.categories = categories;
        this.tags = tags;
        this.seasons = seasons;
        this.episodes = episodes;
    }

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return super.toString() +
                "rating=" + ratingValue +
                ", categories=" + categories +
                ", tags=" + tags +
                ", seasons=" + seasons +
                ", episodes=" + episodes +
                '}';
    }
}
