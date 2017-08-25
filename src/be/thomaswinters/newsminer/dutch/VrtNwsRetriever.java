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

public class VrtNwsRetriever extends APartialNewsRetriever {
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
	protected Collection<IArticle> retrieveHeadlineAnchorElements(Document doc) throws IOException {
		return doc.select(".page-article a").stream()
				// Create headliner object
				.map(e -> toPartialArticle(e.attr("href"), e.select("h2").text()))
				// Collect to list
				.collect(Collectors.toList());
	}

	@Override
	protected Elements retrieveNewsParagraphElements(Document doc) throws IOException {
		return doc.select("article p");
	}

	public static void main(String[] args) throws IOException {
		System.out.println(new VrtNwsRetriever().mineArticles().stream().map(e -> e.toString())
				.collect(Collectors.joining("\n\n\n\n\n\n")));
	}
}
