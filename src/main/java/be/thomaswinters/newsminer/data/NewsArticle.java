package be.thomaswinters.newsminer.data;

import java.net.URL;
import java.util.Objects;

public class NewsArticle {
    private final String title;
    private final URL link;
    private final String fullText;

    public NewsArticle(String title, URL link, String fullText) {
        this.title = title;
        this.link = link;
        this.fullText = fullText;
    }

    public URL getLink() {

        return link;
    }

    public String getText() {
        return fullText;
    }

    public String getTitle() {

        return title;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "title='" + title + '\'' +
                ", link=" + link +
                ", fullText='" + fullText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsArticle that = (NewsArticle) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(link, that.link) &&
                Objects.equals(fullText, that.fullText);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, link, fullText);
    }

}
