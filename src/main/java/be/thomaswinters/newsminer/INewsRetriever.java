package be.thomaswinters.newsminer;

import be.thomaswinters.newsminer.data.ArticleCard;
import be.thomaswinters.newsminer.data.NewsArticle;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.stream.Collectors;

public interface INewsRetriever {
    Collection<ArticleCard> retrieveArticleCards() throws IOException;

    NewsArticle retrieveArticle(URL url) throws IOException;

    default NewsArticle retrieveArticle(ArticleCard card) throws IOException {
        return retrieveArticle(card.getLink());
    }

    default Collection<NewsArticle> retrieveFullArticles() throws IOException {
        return retrieveArticleCards()
                .stream()
                .map(card -> {
                    try {
                        return retrieveArticle(card.getLink());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
