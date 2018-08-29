package be.thomaswinters.newsminer;

import be.thomaswinters.newsminer.INewsRetriever;
import be.thomaswinters.newsminer.data.ArticleCard;
import be.thomaswinters.newsminer.data.NewsArticle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

public abstract class JsoupNewsRetriever implements INewsRetriever {

    /*-********************************************-*
     *  Variables
     *-********************************************-*/
    private final String frontPageUrl;
    private final URL baseUrl;

    /*-********************************************-*/

    /*-********************************************-*
     *  Constructor
     *-********************************************-*/
    public JsoupNewsRetriever(String frontPageUrl, URL baseUrl) {
        this.frontPageUrl = frontPageUrl;
        this.baseUrl = baseUrl;
    }

    /*-********************************************-*/

    /*-********************************************-*
     *  Mining articles
     *-********************************************-*/


    @Override
    public Collection<ArticleCard> retrieveArticleCards() throws IOException {
        Document doc = Jsoup.connect(frontPageUrl).get();

        return retrieverArticleCards(doc);
    }

    @Override
    public NewsArticle retrieveArticle(URL url) throws IOException {
        StringBuilder text = new StringBuilder();
        Document doc = Jsoup
                .connect(url.toExternalForm())
                .followRedirects(true)
                .timeout(60 * 1000)
                .get();

        String articleTitle = retrieveArticleTitle(doc);
        Elements articles = retrieveNewsParagraphElements(doc);

        articles.forEach(e -> text.append(e.text()).append("\n"));
        return new NewsArticle(articleTitle, url, text.toString());
    }
    /*-********************************************-*/

    protected URL toFullUrl(String link) throws MalformedURLException {
        System.out.println("BASEURL:" + baseUrl  + " / LINK: " + link);

        return new URL(baseUrl, link);
    }

    protected ArticleCard createArticleCard(String link, String title) {
        try {
            return new ArticleCard(title, toFullUrl(link), e -> {
                try {
                    return retrieveArticle(e);
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
            });
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    protected abstract Collection<ArticleCard> retrieverArticleCards(Document doc) throws IOException;

    protected abstract String retrieveArticleTitle(Document doc);

    protected abstract Elements retrieveNewsParagraphElements(Document doc) throws IOException;

}
