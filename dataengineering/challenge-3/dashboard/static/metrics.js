var timeFormat = 'YYYY-MM-DD hh:mm:ss';

var chartTotalTweets = new Chart(document.getElementById("chartTotalTweets"), {
    type:    'line',
    data:    {
        datasets: [
        ]
    },
    options: {
        responsive: true,
        title:      {
            display: true,
            text:    "Chart.js Time Scale"
        },
        scales:     {
            xAxes: [{
                type:       "time",
                time:       {
                    format: timeFormat,
                    tooltipFormat: 'll'
                },
                scaleLabel: {
                    display:     true,
                    labelString: 'Date'
                }
            }],
            yAxes: [{
                scaleLabel: {
                    display:     true,
                    labelString: 'value'
                }
            }]
        }
    }
});
var chartTotalUsers = new Chart(document.getElementById("chartTotalUsers"), {
    type:    'line',
    data:    {
        datasets: [
        ]
    },
    options: {
        responsive: true,
        title:      {
            display: true,
            text:    "Chart.js Time Scale"
        },
        scales:     {
            xAxes: [{
                type:       "time",
                time:       {
                    format: timeFormat,
                    tooltipFormat: 'll'
                },
                scaleLabel: {
                    display:     true,
                    labelString: 'Date'
                }
            }],
            yAxes: [{
                scaleLabel: {
                    display:     true,
                    labelString: 'value'
                }
            }]
        }
    }
});
var chartTotalRetweets = new Chart(document.getElementById("chartTotalRetweets"), {
    type:    'line',
    data:    {
        datasets: [
        ]
    },
    options: {
        responsive: true,
        title:      {
            display: true,
            text:    "Chart.js Time Scale"
        },
        scales:     {
            xAxes: [{
                type:       "time",
                time:       {
                    format: timeFormat,
                    tooltipFormat: 'll'
                },
                scaleLabel: {
                    display:     true,
                    labelString: 'Date'
                }
            }],
            yAxes: [{
                scaleLabel: {
                    display:     true,
                    labelString: 'value'
                }
            }]
        }
    }
});

var chartActiveCities = new Chart(document.getElementById("chartActiveCities"), {
    type: 'bar',
    data: {
        labels: [],
        datasets: [{
            label: 'count',
            data: [],
            backgroundColor: [
            ],
            borderColor: [
            ],
            borderWidth: 1
        }]
    },
    options: {
        indexAxis: 'y',
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    }
});
var chartWorstCities = new Chart(document.getElementById("chartWorstCities"), {
    type: 'bar',
    data: {
        labels: [],
        datasets: [{
            label: '# of Mentions',
            data: [],
            backgroundColor: [
            ],
            borderColor: [
            ],
            borderWidth: 1
        }]
    },
    options: {
        indexAxis: 'y',
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    }
});

var graphs = ['chartTotalTweets', 'chartTotalUsers', 'chartTotalRetweets', 'chartActiveCities', 'chartWorstCities'];

function updateData() {
    $.each(graphs, function(i, g){ 
        $.getJSON('/data?graph=' + g, function(data) {
            var chart = eval(g);
            chart.data = data;
            chart.update();    
        });
    });
}

updateData();

setInterval(function(){
    updateData();

}, 1000*60);