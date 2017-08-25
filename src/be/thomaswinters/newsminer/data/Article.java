package be.thomaswinters.newsminer.data;

import java.net.URL;

public class Article implements IArticle {
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
		return getHeadline() + "\n" + getUrl() + "\n" + text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
