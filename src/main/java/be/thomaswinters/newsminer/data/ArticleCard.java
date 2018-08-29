package be.thomaswinters.newsminer.data;

import java.net.URL;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ArticleCard {
    private final String title;
    private final URL link;
    private final Supplier<NewsArticle> articleSupplier;
    private NewsArticle cachedFullArticle = null;

    public ArticleCard(String title, URL link, Supplier<NewsArticle> articleSupplier) {

        this.title = title;
        this.link = link;
        this.articleSupplier = articleSupplier;
    }


    public ArticleCard(String title, URL link, Function<URL, NewsArticle> urlToFullArticleMapper) {
        this(title, link, () -> urlToFullArticleMapper.apply(link));
    }

    public URL getLink() {
        return link;
    }

    public NewsArticle getFullArticle() {
        if (cachedFullArticle == null) {
            cachedFullArticle = articleSupplier.get();
        }
        return cachedFullArticle;
    }

    public String getTitle() {

        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleCard that = (ArticleCard) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(link, that.link) &&
                Objects.equals(articleSupplier, that.articleSupplier);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, link, articleSupplier);
    }

    @Override
    public String toString() {
        return "ArticleCard{" +
                "title='" + title + '\'' +
                ", link=" + link +
                ", articleSupplier=" + articleSupplier +
                '}';
    }
}
