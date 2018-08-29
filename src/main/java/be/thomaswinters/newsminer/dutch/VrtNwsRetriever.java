package be.thomaswinters.newsminer.dutch;

import be.thomaswinters.newsminer.INewsRetriever;
import be.thomaswinters.newsminer.JsoupNewsRetriever;
import be.thomaswinters.newsminer.data.ArticleCard;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.stream.Collectors;

public class VrtNwsRetriever extends JsoupNewsRetriever {
    private final static URL BASE_URL;

    static {
        try {
            BASE_URL = new URL("https://www.vrt.be/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public VrtNwsRetriever() {
        super("https://www.vrt.be/vrtnws/nl/", BASE_URL);
    }


    @Override
    protected Collection<ArticleCard> retrieverArticleCards(Document doc) throws IOException {
        return doc.select(".page-article a").stream()
                // Create headliner object
                .map(e -> createArticleCard(e.attr("href"), e.select("h2").first().text()))
                // Filter out non-articles by getting one whose url starts with a digit (for the date)
                .filter(e -> e.getLink().getPath().matches("/vrtnws/nl/\\d.*"))
                // Collect to list
                .collect(Collectors.toSet());
    }

    @Override
    protected String retrieveArticleTitle(Document doc) {
        return doc.select("h1.vrt-title").text();
    }

    @Override
    protected Elements retrieveNewsParagraphElements(Document doc) throws IOException {
        return doc.select("article p");
    }
}
