package be.thomaswinters.newsminer.dutch;

import be.thomaswinters.newsminer.JsoupNewsRetriever;
import be.thomaswinters.newsminer.data.ArticleCard;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.stream.Collectors;

public class HlnRetriever extends JsoupNewsRetriever {
    private final static URL BASE_URL;

    static {
        try {
            BASE_URL = new URL("http://www.hln.be/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public HlnRetriever() {
        super("http://www.hln.be/", BASE_URL);
    }

    @Override
    protected Collection<ArticleCard> retrieverArticleCards(Document doc) throws IOException {
        return doc.select("article h3 a")
                .stream()
                .map(e -> createArticleCard(e.text(), e.attr("href")))
                .collect(Collectors.toList());
    }

    @Override
    protected String retrieveArticleTitle(Document doc) {
        return doc.select("h1.article__title").text();
    }

    @Override
    protected Elements retrieveNewsParagraphElements(Document doc) throws IOException {
        return doc.select("article p");
    }
}
