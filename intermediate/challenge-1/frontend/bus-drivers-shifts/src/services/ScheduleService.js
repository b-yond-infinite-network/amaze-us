import axios from 'axios'

const REST_API_URL = 'http://localhost:8080/bus-drivers-shifts/api/schedule/'

class ScheduleService {

    // get Schedule by ID
    getScheduleByID = async (id) => {
        return await axios.get(REST_API_URL + id)
        .then((response) => console.log(response))
        .catch((err) => console.log(err))
    }

    // get Schedule by SSN
    getScheduleBySSN = async (ssn) => {
        return await axios.get(REST_API_URL + ssn)
        .then((response) => console.log(response))
        .catch((err) => console.log(err))
    }

    // get Schedule by PK
    getScheduleByPK = async (id, ssn) => {
        return await axios.get(REST_API_URL + id + ssn)
        .then((response) => console.log(response))
        .catch((err) => console.log(err))
    }

    // get all Schedules
    getAllScheduleses = async () => {
        return await axios.get(REST_API_URL + 'all')
        .then((response) => console.log(response))
        .catch((err) => console.log(err))
    }

    getSchedulesByDate(from, to){
        return await axios.get(REST_API_URL + from + to)
        .then((response) => console.log(response))
        .catch((err) => console.log(err))
    }

    // create/update Schedule
    saveOrUpdateSchedule = async (schedule) => {
        await axios.post(REST_API_URL)
        .then((response) => console.log(response))
        .catch((err) => console.log(err))
    }
    
    // delete Schedule by PK
    deleteScheduleByPK = async (id, ssn) => {
        return await axios.delete(REST_API_URL + id + ssn)
        .then((response) => console.log(response))
        .catch((err) => console.log(err))
    }
}

export default new ScheduleService();