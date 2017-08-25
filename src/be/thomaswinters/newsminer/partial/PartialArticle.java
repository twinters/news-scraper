package be.thomaswinters.newsminer.partial;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import be.thomaswinters.newsminer.data.IArticle;

public class PartialArticle implements IArticle {

	private final URL articleURL;
	private final String headline;
	private Optional<String> text;
	private IArticleTextLoader loader;

	public PartialArticle(URL articleUrl, String headline, APartialNewsRetriever aPartialNewsRetriever) {
		this.articleURL = articleUrl;
		this.headline = headline;
		this.loader = aPartialNewsRetriever;
		this.text = Optional.empty();
	}

	@Override
	public URL getUrl() {
		return articleURL;
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
		return "PartialArticle:\n" + articleURL + ",\n" + headline + "\n" + text + "\n\n";
	}

	/*-********************************************-*
	 *  Hashcode and equals
	*-********************************************-*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleURL == null) ? 0 : articleURL.hashCode());
		result = prime * result + ((headline == null) ? 0 : headline.hashCode());
		result = prime * result + ((loader == null) ? 0 : loader.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		if (articleURL == null) {
			if (other.articleURL != null)
				return false;
		} else if (!articleURL.equals(other.articleURL))
			return false;
		if (headline == null) {
			if (other.headline != null)
				return false;
		} else if (!headline.equals(other.headline))
			return false;
		if (loader == null) {
			if (other.loader != null)
				return false;
		} else if (!loader.equals(other.loader))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	/*-********************************************-*/

}
