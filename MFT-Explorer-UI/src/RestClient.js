import axios from "axios";

const RestClient = {
  Execute: function(method, endpoint, input, successFunc, errorFunc, headers) {
    axios({
      method: method,
      url: endpoint,
      timeout: 180000,
      params: input,
      headers: headers
    })
      .then(response => {
        if (successFunc) successFunc(response.data);
      })
      .catch(error => {
        if (errorFunc) errorFunc(error);
      });
  },
    Get: function (endpoint, input, successFunc, errorFunc) {
        RestClient.Execute('GET',endpoint,input, successFunc,errorFunc)
    },
    Post: function (endpoint, input, successFunc, errorFunc) {
        axios.post( endpoint, input, {headers: {"Content-Type": "application/json"}} )
        .then(response => {
            if (successFunc) successFunc(response.data)             
        })
        .catch(error => {
            if (errorFunc) errorFunc(error)
        })
    },
    Put: function (endpoint, input, successFunc, errorFunc, headers) {
        axios.put( endpoint, input, {headers: {"Content-Type": "application/json"}} )
        .then(response => {
            if (successFunc) successFunc(response.data)             
        })
        .catch(error => {
            if (errorFunc) errorFunc(error)
        })
    },
    Delete: function (endpoint, input, successFunc, errorFunc) {
        RestClient.Execute('DELETE',endpoint,input, successFunc,errorFunc)
    }
    
}

module.exports = RestClient;
