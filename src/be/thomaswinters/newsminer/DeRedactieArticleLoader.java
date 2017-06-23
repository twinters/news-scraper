package be.thomaswinters.newsminer;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DeRedactieArticleLoader implements IArticleTextLoader {

	@Override
	public String loadArticleText(URL url) throws IOException {
		StringBuilder text = new StringBuilder();
		Document doc = Jsoup.connect(url.toExternalForm()).get();
		Elements articles = doc.select(".articlecontent");
		
		articles.forEach(e->text.append("$$ " + e.text() + " $$\n\n"));
		return text.toString();
	}

}
