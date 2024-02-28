package entity;

import java.sql.Timestamp;

public class Comment {

    private int commentID;
    private int filmID;
    private int userID;
    private String commentText;
    private Timestamp commentDate;
    private Integer parentCommentID;
    private boolean isEdited;

    public Comment() {
    }

    public Comment(int commentID, int filmID, int userID, String commentText, Timestamp commentDate, Integer parentCommentID) {
        this.commentID = commentID;
        this.filmID = filmID;
        this.userID = userID;
        this.commentText = commentText;
        this.commentDate = commentDate;
        this.parentCommentID = parentCommentID;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }

    public Integer getParentCommentID() {
        return parentCommentID;
    }

    public void setParentCommentID(Integer parentCommentID) {
        this.parentCommentID = parentCommentID;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentID=" + commentID +
                ", filmID=" + filmID +
                ", userID=" + userID +
                ", commentText='" + commentText + '\'' +
                ", commentDate=" + commentDate +
                ", parentCommentID=" + parentCommentID +
                '}';
    }
}
