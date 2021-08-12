import { useState, useEffect } from "react"
import API from "./API"
import Nav from "../../Components/Nav/Nav"
import './LandingPage.scss';
import { Table } from "react-bootstrap"
import { RiAddCircleFill } from "react-icons/ri"
import PopUp from "../../Components/PopUp/PopUp"
import Input from "../../Components/Input/Input"


export default function LandingPage() {

    const [shifts, setShifts] = useState([])
    const [shiftName, setShiftName] = useState(null)



    useEffect(() => {
        API.getData()

            .then((res) => {
                setShifts(res)
                console.log(res)

            })
    }, [])


    return (
        <>
            <Nav />
            <div className="main">
                <div className="container">
                    <div className="header">
                        <h1>Shifts</h1>

                    </div>
                    <Table hover bordered responsive >
                        <caption>List of All shifts</caption>
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Start</th>
                                <th>End</th>
                                <th>Bus Id</th>
                                <th>Driver Id</th>
                            </tr>
                        </thead>

                        <tbody>
                            {shifts.map((shift, i) => (
                                <tr key={i}>
                                    <td>{shift.date}</td>
                                    <td>{shift.start_at}</td>
                                    <td>{shift.end_at}</td>
                                    <td>{shift.bus}</td>
                                    <td>{shift.driver}</td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                </div>
            </div>

        </>

    );
}

