function goToMetricForCity(endpoint) {
    document.location = "/metrics/" + endpoint + "/" + document.getElementById("city").value;
}