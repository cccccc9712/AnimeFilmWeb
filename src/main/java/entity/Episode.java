package entity;

public class Episode {
    private String epTittle;
    private String epLink;

    public Episode() {
    }

    public Episode(String epTittle, String epLink) {
        this.epTittle = epTittle;
        this.epLink = epLink;
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
                "epTittle='" + epTittle + '\'' +
                ", epLink='" + epLink + '\'' +
                '}';
    }
}
