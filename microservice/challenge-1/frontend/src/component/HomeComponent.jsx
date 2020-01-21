import { ErrorMessage, Field, Form, Formik } from 'formik';
import React, { Component } from 'react'

import PostService from '../service/PostService';

const USER = 'thiago'

class HomeComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            id: '-1',
            posts: [],
            message: null,
            content: ''
        }

        this.refreshPosts = this.refreshPosts.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    componentDidMount() {
        this.refreshPosts();
    }

    validate(values) {
        let errors = {}
        if (!values.content) {
            errors.content = 'Enter a Content'
        } else if (values.content.length < 5) {
            errors.content = 'Enter at least 5 Characters in Content'
        }

        return errors
    }

    onSubmit(values) {
        let username = USER

        let post = {
            content: values.content
        }
        PostService.createPost(username, post)
            .then(
                () => {
                    this.setState({ message: 'Post added successful', content: '' })
                    this.refreshPosts()
                }
            )
    }

    refreshPosts() {
        PostService.retrievePosts(USER)//HARDCODED
            .then(
                response => {
                    this.setState({ posts: response.data })
                }
            )
    }

    render() {
        let { content } = this.state
        return (
            <div>
                <br />
                {this.state.message && <div className="alert alert-success">{this.state.message}</div>}
                <Formik
                    initialValues={{ content }}
                    onSubmit={this.onSubmit}
                    validateOnChange={false}
                    validateOnBlur={false}
                    validate={this.validate}
                    enableReinitialize={true}
                >
                    {
                        (props) => (
                            <Form>
                                <ErrorMessage name="content" component="div" className="alert alert-warning" />
                                <fieldset className="form-group">
                                    <Field className="form-control" type="text" name="content" placeholder="What's happening?" />
                                </fieldset>
                                <button className="btn btn-success" type="submit">Add post</button>
                            </Form>
                        )
                    }
                </Formik>
                <br />
                <h3>Posts</h3>
                <div className="container">
                    <table className="table">
                        <tbody>
                            {
                                this.state.posts.map(
                                    post =>
                                        <tr key={post.pid}>
                                            <td>{post.content}</td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default HomeComponent