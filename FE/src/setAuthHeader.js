import axios from 'axios';


const setAuthHeader = (token) => {
    if (token) {
        console.log("SAH: "+token);
        axios.defaults.headers = {
            Authorization: "Bearer " + token
        };
    } else {
        delete axios.defaults.headers.Authorization;
    }
}

export default setAuthHeader;



