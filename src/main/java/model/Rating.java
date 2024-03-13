package model;

public class Rating {
    private int ratingId;
    private int filmId;
    private int userId;
    private float ratingValue;

    public Rating() {
    }

    public Rating(int ratingId, int filmId, int userId, float ratingValue) {
        this.ratingId = ratingId;
        this.filmId = filmId;
        this.userId = userId;
        this.ratingValue = ratingValue;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + ratingId +
                ", filmId=" + filmId +
                ", userId=" + userId +
                ", ratingValue=" + ratingValue +
                '}';
    }
}
