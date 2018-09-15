package be.thomaswinters.newsminer;

import be.thomaswinters.newsminer.data.ArticleCard;
import be.thomaswinters.newsminer.data.NewsArticle;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CombinedNewsRetriever implements INewsRetriever {
    private final List<INewsRetriever> retrievers;

    public CombinedNewsRetriever(List<INewsRetriever> retrievers) {
        this.retrievers = retrievers;
    }

    public CombinedNewsRetriever(INewsRetriever... retrievers) {
        this(Arrays.asList(retrievers));
    }

    @Override
    public Collection<ArticleCard> retrieveArticleCards() throws IOException {
        return retrievers.stream().flatMap(e -> {
            try {
                return e.retrieveArticleCards().stream();
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public NewsArticle retrieveArticle(URL url) throws IOException {
        Optional<NewsArticle> newsArticleOptional = retrievers.stream()
                .filter(e -> e.canRetrieveArticle(url))
                .map(e -> {
                    try {
                        return e.retrieveArticle(url);
                    } catch (IOException e1) {
                        throw new RuntimeException("Can't retrieve " + url + " using " + e);
                    }
                }).findAny();
        if (newsArticleOptional.isPresent()) {
            return newsArticleOptional.get();
        }
        throw new RuntimeException("Can't retrieve " + url);
    }

    @Override
    public boolean canRetrieveArticle(URL url) {
        return retrievers.stream().anyMatch(r -> r.canRetrieveArticle(url));
    }
}
