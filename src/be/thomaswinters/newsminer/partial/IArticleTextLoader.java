package be.thomaswinters.newsminer.partial;

import java.io.IOException;
import java.net.URL;

public interface IArticleTextLoader {
	String loadArticleText(URL url) throws IOException;
}
