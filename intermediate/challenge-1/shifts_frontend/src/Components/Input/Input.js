import './Input.scss';
import { InputGroup, FormControl, Form } from 'react-bootstrap'


export default function Input(props) {
    return (


        <>
            <Form.Label>{props.label}</Form.Label>
            <InputGroup size="sm" className="mb-3">
                <FormControl placeholder={props.placeholder}
                    aria-label="Username"
                    maxLength={props.maxLength}
                    onChange={props.onChange}
                    type={props.type}

                />
            </InputGroup>

        </>


    );
}

