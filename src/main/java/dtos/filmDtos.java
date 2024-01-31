package dtos;

import entity.Category;
import entity.Episode;
import entity.Season;
import entity.Tag;

import java.util.List;

public class filmDtos {
    private int filmID;
    private String filmName;
    private String description;
    private String imageLink;
    private String trailerLink;
    private long viewCount;
    private List<Category> categories;
    private List<Tag> tags;
    private List<Season> seasons;
    private List<Episode> episodes;

    public filmDtos() {
    }

    public filmDtos(int filmID, String filmName, String description, String imageLink, String trailerLink, long viewCount, List<Category> categories, List<Tag> tags, List<Season> seasons, List<Episode> episodes) {
        this.filmID = filmID;
        this.filmName = filmName;
        this.description = description;
        this.imageLink = imageLink;
        this.trailerLink = trailerLink;
        this.viewCount = viewCount;
        this.categories = categories;
        this.tags = tags;
        this.seasons = seasons;
        this.episodes = episodes;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
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

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
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
        return "filmDtos{" +
                "filmID=" + filmID +
                ", filmName='" + filmName + '\'' +
                ", description='" + description + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", trailerLink='" + trailerLink + '\'' +
                ", viewCount=" + viewCount +
                ", categories=" + categories +
                ", tags=" + tags +
                ", seasons=" + seasons +
                ", episodes=" + episodes +
                '}';
    }
}
