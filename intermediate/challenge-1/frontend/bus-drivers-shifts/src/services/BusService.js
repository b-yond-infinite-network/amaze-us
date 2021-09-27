import axios from 'axios'

const REST_API_URL = 'http://localhost:8080/bus-drivers-shifts/api/bus/'

class BusService {
    
    // get Bus by ID
    getBusByID = async (id) => {
        return await axios.get(REST_API_URL + id)
        .then((response) => console.log(response))
        .catch((err) => console.log(err))
    } 

    // get all Buss
    getAllBusses = async () => {
        return await axios.get(REST_API_URL + 'all')
        .then((response) => console.log(response))
        .catch((err) => console.log(err))
    }

    // create/update Bus
    saveOrUpdateBus = async (bus) => {
        await axios.post(REST_API_URL, {
            bus: bus
        })
        .then((response) => console.log(response))
        .catch((err) => console.log(err))
    }
    
    // delete Bus by ID
    deleteBusByID = async (id) => {
        return await axios.delete(REST_API_URL + id)
        .then((response) => console.log(response))
        .catch((err) => console.log(err))
    }
}

export default new BusService();