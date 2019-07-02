import ArticleActions from '../ArticleActions/ArticleActions';
import { Link } from 'react-router-dom';
import React from 'react';

const ArticleMeta = props => {
  const article = props.article;
  return (
    <div id="article-meta" className="article-meta">
      <Link id="article-meta-author-image" to={`/@${article.author.username}`}>
        <img src={article.author.image} alt={article.author.username} />
      </Link>

      <div className="info">
        <Link id="article-meta-author-info" to={`/@${article.author.username}`} className="author">
          {article.author.username}
        </Link>
        <span id="article-meta-created" className="date">
          {new Date(article.createdAt).toDateString()}
        </span>
      </div>

      <ArticleActions canModify={props.canModify} article={article} />
    </div>
  );
};

export default ArticleMeta;
