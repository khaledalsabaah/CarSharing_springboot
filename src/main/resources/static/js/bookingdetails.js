//get car Adress
const carAdress = getAddressFromLaLng();
$('.cs-address').html(carAdress);

let PreEndTime = getPreEndTime();
let diff= getDiffInMinAndSec(PreEndTime);
let s = diff.get('s');
let m = diff.get('m');
let time = setInterval(function () {timer()}, 1000);
function getPreEndTime(){
    let date1 = new Date();
    date1.setHours(bookingBookTimeHHJs, bookingBookTimeMMJs, bookingBookTimeSSJs);
    return  add_minutes(date1, 15);
}
function timer() {
    if (document.getElementById("m")!=null) {
    if (m < 0) {
        clearInterval(time);
        cancelBooking().then(alert("die Zeit ist um, Sie haben es nicht geschafft das Auto zu öffnen!"));
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
                cancelBooking();
            }
        }

            if (m < 10 && m.toString().length <2) m = "0" + m;
            if (s < 10 && s.toString().length <2) s = "0" + s;
            document.getElementById("m").innerHTML = m;
            document.getElementById("s").innerHTML = s;
            document.getElementById("d").innerHTML = ":";
        }
    }
}
function getSecDiff(date_future){
    let date_now = new Date();
    let delta = Math.abs(date_future - date_now) / 1000;
    return delta % 60;//seconds
}
function getDiffInMinAndSec(date_future) {

    let currentDate = new Date();
    var delta = Math.abs( date_future - currentDate) / 1000;
    var days = Math.floor(delta / 86400);
    delta -= days * 86400;
    var hours = Math.floor(delta / 3600) % 24;
    delta -= hours * 3600;
    var minutes = Math.floor(delta / 60) % 60;
    delta -= minutes * 60;
    var seconds = delta % 60;
    let map = new Map();
    if(date_future - currentDate < 0){
        minutes = minutes*-1;
    }
    map.set('m', minutes);
    map.set('s', seconds);
    return map;
}
function add_minutes(dt, minutes) {
    return new Date(dt.getTime() + minutes * 60000);
}

let startTime = (bookingStartTimeJs!= null) ? Date.parse(bookingStartTimeJs) : new Date();
var timerVar = setInterval(countTimer, 1000);
var totalSeconds = getTotalSecDiff(startTime);
function getTotalSecDiff(startDate){
    let endDate   = new Date();
    return (endDate.getTime() - startDate) / 1000;
}
function countTimer() {
    ++totalSeconds;
    let hour = Math.floor(totalSeconds / 3600);
    let minute = Math.floor((totalSeconds - hour * 3600) / 60);
    let seconds = totalSeconds - (hour * 3600 + minute * 60);
    seconds= Math.floor(seconds)
    if (hour < 10) hour = "0" + hour;
    if (minute < 10) minute = "0" + minute;
    if (seconds < 10) seconds = "0" + seconds;
    let timerHtml = document.getElementById("timer");
    if (timerHtml != null) timerHtml.innerHTML = hour + ":" + minute + ":" + seconds;
    // let closecarHtml = document.getElementById("closecar");
    // if (closecarHtml != null) {
    //     closecarHtml.addEventListener("click", function () {
    //         clearInterval(timerVar);
    //     });
    // }
}

let stornoButtonHtml = document.getElementById("stornoButton");
if (stornoButtonHtml != null) stornoButtonHtml.onclick = cancelBooking;


function cancelBooking() {
    let opencarHtml = document.getElementById("opencar");
    if (opencarHtml != null) opencarHtml.style.display = "none";
    if (time != null) clearInterval(time);
    if (timerVar != null) clearInterval(timerVar);
    if (stornoButtonHtml != null) stornoButtonHtml.style.display = "none";
    // document.getElementById("timer").style.display = "none";
    if (document.getElementById("m")!=null) {
        document.getElementById("m").style.display = "none";
        document.getElementById("s").style.display = "none";
        document.getElementById("d").style.display = "none";
    }
    let infoHtml = document.getElementById("info");
    if(infoHtml!=null)
        infoHtml.style.display = "none";
    let closecarHtml = document.getElementById("closecar");
    if(closecarHtml!=null)
        closecarHtml.style.display = "none";
    //document.getElementById("cs-booking").style.display = "none";
    if(document.getElementById("dasbuchen")!=null)
        document.getElementById("dasbuchen").innerHTML = "Die Buchung wurde Storniert"
    setTimeout(function () {
        if (typeof bookingidJs !== 'undefined' && bookingidJs.length !== 0) {
            let url = "/cancelbooking?id=" + bookingidJs;
            window.location = url;
        }
    }, 1000);
    return 0;
}
function finishBooking() {
    clearInterval(timerVar);
    document.getElementById("finishText").hidden=false;
    let url = "/completebooking?id=" + bookingidJs;
    window.location = url;
}

function getAddressFromLaLng() {
    let address = '';
    $.ajax({
        url: "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + bookingXcJs + "," + bookingYcJs + "&key=AIzaSyCIc0TEu2s9XA77NASIjZxlh1xEtzUXY8w\n",
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

// document.getElementById("opencar").onclick = function () {
//     clearInterval(time);
//     document.getElementById("opencar").style.display = "none";
//     document.getElementById("m").style.display = "none";
//     document.getElementById("s").style.display = "none";
//     document.getElementById("d").style.display = "none";
//     document.getElementById("info").style.display = "none";
//     document.getElementById("stornoButton").style.display = "none";
//     document.getElementById("closecar").style.display = "block";
//
// }
