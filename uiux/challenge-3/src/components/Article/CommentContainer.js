import CommentInput from './CommentInput';
import CommentList from './CommentList';
import { Link } from 'react-router-dom';
import React from 'react';

const CommentContainer = props => {
  if (props.currentUser) {
    return (
      <div id="comment-container" className="col-xs-12 col-md-8 offset-md-2">
        <div>
          <list-errors errors={props.errors}></list-errors>
          <CommentInput id="comment-input" slug={props.slug} currentUser={props.currentUser} />
        </div>

        <CommentList
          id="comment-list"
          comments={props.comments}
          slug={props.slug}
          currentUser={props.currentUser} />
      </div>
    );
  } else {
    return (
      <div id="comment-container" className="col-xs-12 col-md-8 offset-md-2">
        <p>
          <Link id="comment-login" to="/login">Sign in</Link>
          &nbsp;or&nbsp;
          <Link id="comment-register" to="/register">sign up</Link>
          &nbsp;to add comments on this article.
        </p>

        <CommentList
          id="comment-list"
          comments={props.comments}
          slug={props.slug}
          currentUser={props.currentUser} />
      </div>
    );
  }
};

export default CommentContainer;
