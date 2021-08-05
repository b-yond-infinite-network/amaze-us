import './PopUp.scss';


export default function PopUp(props) {
    return (
        <div className="popup-dropback">
            <div className="popup">
                <div className="popup-title text-center">
                    {props.title}
                </div>
                <div className="popupContent">
                    <div className="divContent">
                        {props.children}
                    </div>
                    <button
                        className={
                            props.disableBtn
                                ? "btn btn-primary btnSubmitDisabled oneButton"
                                : "btn btn-primary btnSubmit oneButton"
                        }
                        style={props.btnOneStyle}
                        onClick={props.submit}
                        disabled={props.disableBtn}
                    >
                        {props.btnMsg}
                    </button>

                </div>
            </div>
        </div>
    );
}

