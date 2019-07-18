'use strict';
import React, {Component} from 'react';

import './LoginVideo.css'

class LoginVideo extends Component {
    constructor (props) {
        super(props);

        this.state = {
            videoPath : '_media/video/jump_lightspeed.mp4',
            videoType : 'video/mp4',

            videoPlay: false,
        }
    }
    
    render () {
        return (
            <video className={`login_video ${this.props.playVideo ? 'active' : ''}`} autoPlay={this.props.playVideo ? true : false}>
                <source src={this.state.videoPath} type={this.state.videoType}/>
            </video>
        )
    }
};

export default LoginVideo;
