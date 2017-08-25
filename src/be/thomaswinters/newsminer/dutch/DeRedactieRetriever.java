package be.thomaswinters.newsminer.dutch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import be.thomaswinters.newsminer.partial.APartialNewsRetriever;

@Deprecated
public class DeRedactieRetriever extends APartialNewsRetriever {
	public DeRedactieRetriever() throws MalformedURLException {
		super("http://deredactie.be/cm/vrtnieuws", new URL("http://deredactie.be/"));
	}

	@Override
	public Elements retrieveHeadlineAnchorElements(Document doc) throws IOException {
		return doc.select("article h1 a");
	}

	@Override
	protected Elements retrieveNewsParagraphElements(Document doc) throws IOException {
		return doc.select(".articlecontent");
	}

	public static void main(String[] args) throws IOException {
		System.out.println(new DeRedactieRetriever().mineArticles());
	}
}
