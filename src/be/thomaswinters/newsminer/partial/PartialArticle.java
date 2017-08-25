package be.thomaswinters.newsminer.partial;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import be.thomaswinters.newsminer.data.IArticle;

public class PartialArticle implements IArticle {

	private final URL url;
	private final String headline;
	private Optional<String> text;
	private IArticleTextLoader loader;

	public PartialArticle(URL url, String headline, IArticleTextLoader loader) {
		this.url = url;
		this.headline = headline;
		this.loader = loader;
		this.text = Optional.empty();
	}

	public PartialArticle(String url, String headline, IArticleTextLoader loader) throws MalformedURLException {
		this(new URL(url), headline, loader);
	}

	@Override
	public URL getUrl() {
		return url;
	}

	@Override
	public String getHeadline() {
		return headline;
	}

	@Override
	public String getText() {
		if (!text.isPresent()) {
			try {
				text = Optional.of(loader.loadArticleText(getUrl()));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return text.get();
	}

	@Override
	public String toString() {
		return "PartialArticle:\n" + url + ",\n" + headline + "\n" + text + "\n\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((headline == null) ? 0 : headline.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartialArticle other = (PartialArticle) obj;
		if (headline == null) {
			if (other.headline != null)
				return false;
		} else if (!headline.equals(other.headline))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
