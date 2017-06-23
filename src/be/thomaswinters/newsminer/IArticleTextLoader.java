package be.thomaswinters.newsminer;

import java.io.IOException;
import java.net.URL;

public interface IArticleTextLoader {
	String loadArticleText(URL url) throws IOException;
}
