const lat = bookingJs.car.XCoordinates;
const lng = bookingJs.car.YCoordinates;
const coords = {lat, lng};
const carAdress = getAddressFromLaLng(coords);
document.getElementById('address').innerHTML=carAdress;

let date1 = new Date();
date1.setHours(bookingBookTimeHHJs, bookingBookTimeMMJs);
let endTimer = add_minutes(date1, 14);

let currentDate = new Date();
let diffMs = (endTimer - currentDate);
let diffMins = Math.round(((diffMs % 86400000) % 3600000) / 60000); // minutes


let s = 60;
let m = diffMins;

function add_minutes(dt, minutes) {
    return new Date(dt.getTime() + minutes * 60000);
}
let time = setInterval(function () {
    timer()
}, 1000);

function timer() {
    if (m < 0) {
        alert("die Zeit ist um, Sie haben es nicht geschafft das Auto zu öffnen!");
    } else {

        s--;
        if (s == -1) {
            m--;
            s = 59;
            if (m == -1) {
                m = 0;
                s = 0;
                clearInterval(time);
                alert("die Zeit ist um, Sie haben es nicht geschafft das Auto zu öffnen!");
            }
        }
        document.getElementById("m").innerHTML = m;
        document.getElementById("s").innerHTML = s;
        document.getElementById("d").innerHTML = ":";
        // }
    }
}



    document.getElementById("opencar").onclick = function () {
        document.getElementById("opencar").style.display = "none";
        clearInterval(time);
        document.getElementById("m").style.display = "none";
        document.getElementById("s").style.display = "none";
        document.getElementById("d").style.display = "none";
        document.getElementById("info").style.display = "none";
        document.getElementById("stornoButton").style.display = "none";
        document.getElementById("closcar").style.display = "block";

    }
    var timerVar = setInterval(countTimer, 1000);
    var totalSeconds = 0;

    function countTimer() {
        ++totalSeconds;
        var hour = Math.floor(totalSeconds / 3600);
        var minute = Math.floor((totalSeconds - hour * 3600) / 60);
        var seconds = totalSeconds - (hour * 3600 + minute * 60);
        if (hour < 10)
            hour = "0" + hour;
        if (minute < 10)
            minute = "0" + minute;
        if (seconds < 10)
            seconds = "0" + seconds;
        document.getElementById("timer").innerHTML = hour + ":" + minute + ":" + seconds;

        document.getElementById("closcar").addEventListener("click", function () {
            clearInterval(timerVar);
        });
    }

    document.getElementById("stornoButton").onclick = function () {
        document.getElementById("opencar").style.display = "none";
        clearInterval(time);
        document.getElementById("stornoButton").style.display = "none";
        document.getElementById("closcar").style.display = "none";
        clearInterval(timerVar);
        document.getElementById("timer").style.display = "none";
        document.getElementById("m").style.display = "none";
        document.getElementById("s").style.display = "none";
        document.getElementById("d").style.display = "none";
        document.getElementById("info").style.display = "none";
        document.getElementById("dasbuchen").innerHTML = "Die Buchung wurde Storniert"
        setTimeout(function () {
            if (typeof bookingidJs !== 'undefined' && bookingidJs.length !== 0) {
                let url = "/cancelbooking?id=" + bookingidJs;
                window.location = url;
            }
        }, 1000);
    }

function getAddressFromLaLng(coords) {
    let address = '';
    $.ajax({
        url: "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + coords.lat + "," + coords.lng + "&key=AIzaSyCIc0TEu2s9XA77NASIjZxlh1xEtzUXY8w\n",
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            console.log(data)
            const address_components = data.results[0].address_components;
            address = address_components[1].long_name + ", " + address_components[0].long_name + ", " + address_components[2].long_name;
        },
        error: function (request, error) {
            alert("Request: " + JSON.stringify(request));
        }
    });
    return address;
}
