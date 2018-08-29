package be.thomaswinters.newsminer;

import java.io.IOException;
import java.util.Collection;

import be.thomaswinters.newsminer.data.Article;
import be.thomaswinters.newsminer.data.ArticleCard;
import be.thomaswinters.newsminer.data.IArticle;

public interface INewsRetriever {
	Collection<ArticleCard> retrieveArticleCards() throws IOException;
	Collection<Article> retrieveFullArticles() throws IOException;
	Collection<IArticle> retrieveArticles() throws IOException;
}
