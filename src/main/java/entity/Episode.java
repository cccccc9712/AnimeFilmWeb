package entity;

import java.util.Date;

public class Episode {
    private int epId;
    private String epTittle;
    private String epLink;
    private Date epDate;
    private Boolean isPremium;

    public Episode() {
    }

    public Episode(int epId, String epTittle, String epLink, Date epDate) {
        this.epId = epId;
        this.epTittle = epTittle;
        this.epLink = epLink;
        this.epDate = epDate;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public Date getEpDate() {
        return epDate;
    }

    public void setEpDate(Date epDate) {
        this.epDate = epDate;
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

    @Override
    public String toString() {
        return "Episode{" +
                "epId='" + epId + '\'' +
                ", epTittle='" + epTittle + '\'' +
                ", epLink='" + epLink + '\'' +
                ", epDate=" + epDate +
                '}';
    }
}
