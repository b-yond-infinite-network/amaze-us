import axios from "axios";
import backend_url from "../../environment"


export default class API {
    static getData = () => {
        return axios({
            method: "GET",
            url: backend_url + "api/v1/shift/",
            headers: {
                "Content-Type": "application/json",
            },

        }).then(res => res.data)
    }
    static addCompany = body => {
        return axios({
            method: "POST",
            url: backend_url + "api/v1/companies/",
            data: body

        }).then(res => res.data)
    }
}