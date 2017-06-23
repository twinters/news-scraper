package be.thomaswinters.newsminer.data;

import java.net.URL;

public interface IArticle {

	URL getUrl();

	String getHeadline();

	String getText();
}
