package be.thomaswinters.newsminer.data;

import java.net.URL;

public class Headline implements IHeadline {
	private final URL url;
	private final String headline;

	public Headline(URL url, String headline) {
		this.url = url;
		this.headline = headline;
	}

	public URL getUrl() {
		return url;
	}

	public String getHeadline() {
		return headline;
	}


	public String toString() {
		return headline + "\n" + url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((headline == null) ? 0 : headline.hashCode());
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
		Headline other = (Headline) obj;
		if (headline == null) {
			if (other.headline != null)
				return false;
		} else if (!headline.equals(other.headline))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
