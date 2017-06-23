package be.thomaswinters.newsminer;

import java.io.IOException;
import java.util.Collection;

import be.thomaswinters.newsminer.data.IArticle;

public interface INewsMiner {
	Collection<IArticle> mineArticles() throws IOException;
}
