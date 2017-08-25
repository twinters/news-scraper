package be.thomaswinters.newsminer.partial;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
	public Collection<IArticle> mineArticles() throws IOException {
		Document doc = Jsoup.connect(frontPageUrl).get();

		return retrieveHeadlineAnchorElements(doc).stream().map(this::toPartialArticle).collect(Collectors.toList());
	}

	protected IArticle toPartialArticle(Element anchor) {
		try {
			return new PartialArticle(new URL(baseUrl, anchor.attr("href")), anchor.text(), this);
		} catch (MalformedURLException e1) {
			throw new RuntimeException(e1);
		}
	}
	/*-********************************************-*/

	/*-********************************************-*
	 *  Text Loader
	*-********************************************-*/
	@Override
	public String loadArticleText(URL url) throws IOException {
		StringBuilder text = new StringBuilder();
		Document doc = Jsoup.connect(url.toExternalForm()).get();
		Elements articles = retrieveNewsParagraphElements(doc);

		articles.forEach(e -> text.append(e.text() + "\n"));
		return text.toString();
	}

	/*-********************************************-*/

	protected abstract Elements retrieveHeadlineAnchorElements(Document doc) throws IOException;

	protected abstract Elements retrieveNewsParagraphElements(Document doc) throws IOException;

}
