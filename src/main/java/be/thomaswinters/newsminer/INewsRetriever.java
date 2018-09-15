package be.thomaswinters.newsminer;

import be.thomaswinters.newsminer.data.ArticleCard;
import be.thomaswinters.newsminer.data.NewsArticle;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface INewsRetriever {
    Collection<ArticleCard> retrieveArticleCards() throws IOException;

    NewsArticle retrieveArticle(URL url) throws IOException;

    boolean canRetrieveArticle(URL url);

    default NewsArticle retrieveArticle(ArticleCard card) throws IOException {
        return retrieveArticle(card.getLink());
    }

    default Stream<NewsArticle> retrieveFullArticlesStream() throws IOException {
        return retrieveArticleCards()
                .stream()
                .map(card -> {
                    try {
                        return retrieveArticle(card.getLink());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
    default Collection<NewsArticle> retrieveFullArticles() throws IOException {
        return retrieveFullArticlesStream()
                .collect(Collectors.toList());
    }
}
