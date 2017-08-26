package be.thomaswinters.newsminer.partial;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import be.thomaswinters.newsminer.INewsRetriever;
import be.thomaswinters.newsminer.data.IArticle;

public abstract class APartialNewsRetriever implements INewsRetriever, IArticleTextLoader {

	/*-********************************************-*
	 *  Variables
	*-********************************************-*/
	private final String frontPageUrl;
	private final URL baseUrl;

	/*-********************************************-*/

	/*-********************************************-*
	 *  Constructor
	*-********************************************-*/
	public APartialNewsRetriever(String frontPageUrl, URL baseUrl) {
		this.frontPageUrl = frontPageUrl;
		this.baseUrl = baseUrl;
	}

	/*-********************************************-*/

	/*-********************************************-*
	 *  Mining articles
	*-********************************************-*/
	@Override
	public Collection<IArticle> retrieveArticles() throws IOException {
		Document doc = Jsoup.connect(frontPageUrl).get();

		return retrieveHeadlineAnchorElements(doc);
	}

	protected PartialArticle toPartialArticle(String relativeUrl, String headline) {
		try {
			return new PartialArticle(new URL(baseUrl, relativeUrl), headline, this);
		} catch (MalformedURLException e1) {
			throw new RuntimeException(e1);
		}
	}
	/*-********************************************-*/

	/*-********************************************-*
	 *  Text Loader
	*-********************************************-*/
	@Override
	public String retrieveArticleText(URL url) throws IOException {
		StringBuilder text = new StringBuilder();
		Document doc = Jsoup.connect(url.toExternalForm()).get();
		Elements articles = retrieveNewsParagraphElements(doc);

		articles.forEach(e -> text.append(e.text() + "\n"));
		return text.toString();
	}

	/*-********************************************-*/

	protected abstract Collection<IArticle> retrieveHeadlineAnchorElements(Document doc) throws IOException;

	protected abstract Elements retrieveNewsParagraphElements(Document doc) throws IOException;

}
