package models;

/**
 * Created by sunil on 26/3/16.
 */
public class NewsDataList {
    public String newsimage;
    public String newdesc;
    public String newsdate;
    public String newshead;

    public String getNewshead() {
        return newshead;
    }

    public void setNewshead(String newshead) {
        this.newshead = newshead;
    }

    public String getNewsimage() {
        return newsimage;
    }

    public void setNewsimage(String newsimage) {
        this.newsimage = newsimage;
    }

    public String getNewdesc() {
        return newdesc;
    }

    public void setNewdesc(String newdesc) {
        this.newdesc = newdesc;
    }

    public String getNewsdate() {
        return newsdate;
    }

    public void setNewsdate(String newsdate) {
        this.newsdate = newsdate;
    }
}
