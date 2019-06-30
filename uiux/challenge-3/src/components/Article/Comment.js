import DeleteButton from './DeleteButton';
import { Link } from 'react-router-dom';
import React from 'react';

const Comment = props => {
  const comment = props.comment;
  const show = props.currentUser &&
    props.currentUser.username === comment.author.username;
  return (
    <div id="comment-card" className="card">
      <div className="card-block">
        <p className="card-text">{comment.body}</p>
      </div>
      <div className="card-footer">
        <Link
        id="comment-author-image"
          to={`/@${comment.author.username}`}
          className="comment-author">
          <img src={comment.author.image} className="comment-author-img" alt={comment.author.username} />
        </Link>
        &nbsp;
        <Link
        id="comment-author"
          to={`/@${comment.author.username}`}
          className="comment-author">
          {comment.author.username}
        </Link>
        <span id="comment-created" className="date-posted">
          {new Date(comment.createdAt).toDateString()}
        </span>
        <DeleteButton id="comment-delete-button" show={show} slug={props.slug} commentId={comment.id} />
      </div>
    </div>
  );
};

export default Comment;
