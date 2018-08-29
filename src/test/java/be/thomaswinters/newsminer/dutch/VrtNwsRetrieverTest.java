package be.thomaswinters.newsminer.dutch;

import be.thomaswinters.newsminer.INewsRetriever;
import be.thomaswinters.newsminer.data.ArticleCard;
import be.thomaswinters.newsminer.data.NewsArticle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class VrtNwsRetrieverTest {

    private VrtNwsRetriever newsRetriever;

    @BeforeEach
    void setUp() {
        newsRetriever = new VrtNwsRetriever();
    }

    @Test
    void test_vrt_article_cards_present() throws IOException {
        INewsRetriever newsRetriever = new VrtNwsRetriever();
        assertTrue(0 < newsRetriever.retrieveArticleCards().size());
        System.out.println(
                newsRetriever
                        .retrieveArticleCards()
                        .stream()
                        .limit(1)
                        .map(e -> {
                            try {
                                return newsRetriever.retrieveArticle(e);
                            } catch (IOException e1) {
                                throw new RuntimeException(e1);
                            }
                        })
                        .map(Object::toString)
                        .collect(Collectors.joining("\n\n\n\n\n\n")));
    }

    @Test
    void test_article_scraper() throws IOException {
        Collection<ArticleCard> articleCards = newsRetriever.retrieveArticleCards();
        assertFalse(articleCards.isEmpty());
        ArticleCard articleCard = articleCards.stream().findFirst().get();
        assertNotNull(articleCard.getTitle());
        assertNotNull(articleCard.getLink());
        NewsArticle newsArticle = articleCard.getFullArticle();

        assertNotNull(newsArticle.getTitle());
        assertTrue(newsArticle.getTitle().length() > 0);
        assertNotNull(newsArticle.getLink());
        assertEquals(articleCard.getLink(), newsArticle.getLink());
        assertNotNull(newsArticle.getText());
        assertTrue(newsArticle.getText().length() > 0);


    }
}