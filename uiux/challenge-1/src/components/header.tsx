import * as React from 'react';

const logo = 'https://static.cms.yp.ca/ecms/media/2/19773060_xxl-1449435516-600x360.jpg';
// <!-- <img src={logo} /> -->
export default class Header extends React.Component {
    render() {
        return (
            <div className="row">
                <div className="col-xs-12">
                    <h1 className="center-xs">That Karaoke app kick arse</h1>
                </div>
            </div>
        );
    }
}
