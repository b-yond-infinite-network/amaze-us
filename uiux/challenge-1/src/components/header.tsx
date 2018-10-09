import * as React from 'react';

const logo = 'https://static.cms.yp.ca/ecms/media/2/19773060_xxl-1449435516-600x360.jpg';

export default class Header extends React.Component<{}> {
    render() {
        return (
            <div>
                <h1>That Karaoke app kick arse</h1>
                <img src={logo} />
            </div>
        );
    }
}