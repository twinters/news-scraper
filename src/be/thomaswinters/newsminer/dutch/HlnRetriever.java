package be.thomaswinters.newsminer.dutch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import be.thomaswinters.newsminer.data.IArticle;
import be.thomaswinters.newsminer.partial.APartialNewsRetriever;

public class HlnRetriever extends APartialNewsRetriever {
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
	protected Collection<IArticle> retrieveHeadlineAnchorElements(Document doc) throws IOException {
		return doc.select("article h3 a").stream().map(e -> toPartialArticle(e.attr("href"), e.text()))
				.collect(Collectors.toList());
	}

	@Override
	protected Elements retrieveNewsParagraphElements(Document doc) throws IOException {
		return doc.select("article p");
	}
}
