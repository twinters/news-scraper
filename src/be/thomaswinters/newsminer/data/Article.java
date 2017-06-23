package be.thomaswinters.newsminer.data;

import java.net.URL;

public class Article {
	private final URL url;
	private final String headline;
	private final String text;

	public Article(URL url, String headline, String text) {
		this.url = url;
		this.headline = headline;
		this.text = text;
	}

	public URL getUrl() {
		return url;
	}

	public String getHeadline() {
		return headline;
	}

	public String getText() {
		return text;
	}

	public String toString() {
		return headline + "\n" + url + "\n" + text;
	}

}
