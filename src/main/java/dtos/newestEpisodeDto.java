package dtos;

import model.Category;
import model.Episode;
import model.Season;
import model.Tag;

import java.util.List;

public class newestEpisodeDto extends Episode {
    private int filmId;

    private String seasonName;
    private String filmName;
    private String description;
    private String imageLink;
    private String trailerLink;
    private long viewCount;
    private List<Category> categories;
    private List<Tag> tags;
    private List<Season> seasons;

    public newestEpisodeDto() {
    }

    public newestEpisodeDto(int epId, String epTittle, String epLink, java.util.Date epDate, int filmId, String seasonName, String filmName, String description, String imageLink, String trailerLink, long viewCount, List<Category> categories, List<Tag> tags, List<Season> seasons) {
        super(epId, epTittle, epLink, epDate);
        this.filmId = filmId;
        this.seasonName = seasonName;
        this.filmName = filmName;
        this.description = description;
        this.imageLink = imageLink;
        this.trailerLink = trailerLink;
        this.viewCount = viewCount;
        this.categories = categories;
        this.tags = tags;
        this.seasons = seasons;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
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

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
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

    @Override
    public String toString() {
        return "newestEpisodeDto{" +
                "filmName='" + filmName + '\'' +
                ", description='" + description + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", trailerLink='" + trailerLink + '\'' +
                ", viewCount=" + viewCount +
                ", categories=" + categories +
                ", tags=" + tags +
                ", seasons=" + seasons +
                '}';
    }
}
