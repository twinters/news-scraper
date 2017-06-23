package be.thomaswinters.newsminer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import be.thomaswinters.newsminer.data.IArticle;
import be.thomaswinters.newsminer.data.PartialArticle;

public class DeRedactieMiner implements INewsMiner {
	private static final String FRONT_PAGE = "http://deredactie.be/cm/vrtnieuws";
	private static final URL BASE_URL;
	static {
		URL intermediate = null;
		try {
			intermediate = new URL("http://deredactie.be/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		BASE_URL = intermediate;
	}

	private static final IArticleTextLoader LOADER = new DeRedactieArticleLoader();

	@Override
	public Collection<IArticle> mineArticles() throws IOException {
		Document doc = Jsoup.connect(FRONT_PAGE).get();
		Elements articles = doc.select("article h1 a");

		Set<IArticle> result = new LinkedHashSet<>();
		articles.forEach(e -> {
			try {
				result.add(new PartialArticle(new URL(BASE_URL, e.attr("href")), e.text(), LOADER));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		});
		return result;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(new DeRedactieMiner().mineArticles());
	}
}
