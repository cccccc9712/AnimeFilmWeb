package dtos;

import entity.Comment;

import java.sql.Timestamp;
import java.util.List;

public class commentDto extends Comment {
    private String userName;
    private List<commentDto> replies;

    public commentDto() {
    }

    public commentDto(int commentID, int filmID, int userID, String commentText, Timestamp commentDate, Integer parentCommentID, String userName) {
        super(commentID, filmID, userID, commentText, commentDate, parentCommentID);
        this.userName = userName;
    }

    public List<commentDto> getReplies() {
        return replies;
    }

    public void setReplies(List<commentDto> replies) {
        this.replies = replies;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "commentDto{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
