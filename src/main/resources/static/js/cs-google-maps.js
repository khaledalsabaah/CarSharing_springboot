// Hide Booking Section
$('.cs-booking').hide();


function initMap() {
    // Map options
    var options = {
        zoom: 14,
        center: {lat: 52.41185195658542, lng: 12.536941898216064}
    }

    // New map
    var map = new google.maps.Map(document.getElementById('csMap'), options);


    // Markers
    let markers = jQuery.parseJSON($('#csMap').attr('data-cs-merkers'));

    

    $.each(markers, function (key) {
        // Add marker
        const car = markers[key];
        if (car.available) {
            const lat = car.lat;
            const lng = car.lng;
            const coords = {lat, lng};
            const cardAddress = 'Potsdam';
            const content = car.brand;
            const price = car.price;
            const carId = key;
            const carColor = car.color;
            const carImg = car.img;
            const props = {coords, cardAddress, content, price, carId, carColor, carImg };
            addMarker(props);
        }
    });


    // Add Parking Areas
    
    const flightPlanCoordinates = [
        {lat: 52.415713934215844, lng: 12.500018251595172},
        {lat: 52.421277203536036, lng: 12.523738744413565},
        {lat:52.422662049111416, lng: 12.536977924175934},
        {lat: 52.421625998376705, lng: 12.556383051770478},
        {lat: 52.41609998322356, lng: 12.557515646847502},
        {lat: 52.41216227489358, lng: 12.551588399755072},
        {lat: 52.40707834755867, lng: 12.539513982384587},
        {lat: 52.41365440196928, lng: 12.523933240900323},
        {lat: 52.415713934215844, lng: 12.500018251595172},
    ];
    const flightPath = new google.maps.Polyline({
        path: flightPlanCoordinates,
        geodesic: true,
        strokeColor: "#FF0000",
        strokeOpacity: 1.0,
        strokeWeight: 4,
    });

    flightPath.setMap(map);
    


    // Add Marker Function
    function addMarker(props) {
        var marker = new google.maps.Marker({
            position: props.coords,
            map: map,
            icon: '../static/images/icons8-car-sharing-64.png'
        });


        marker.addListener('click', function () {
            displayBookingSection(props);
        });

        // Check for customicon

        /** 
        if (props.iconImage) {
            // Set icon image
            const image = "images/icons8-car-sharing-64.png";
            marker.setIcon(image);
            
        }
        */

        // Check content
        /*
        if (props.content) {
            var infoWindow = new google.maps.InfoWindow({
                content: props.content
            });

            marker.addListener('click', function () {
                infoWindow.open(map, marker);
            });
        }

         */
    }


}


function displayBookingSection(props) {
    if ($('.cs-booking').css('display') != 'none') {
        $('.cs-booking').hide(200);
    }
    $('.cs-car-brand').html(props.content);
    $('.cs-color').html(props.carColor);    
    const address = getAddressFromLaLng(props.coords);
    $('.cs-address').html(address);
    console.log(props)
    $('.cs-car-image').attr("src", props.carImg);

    $('.cs-price-per-hour').find('span').html(props.price + ' Euro')
    $('#csBookNow').attr('data-car-id', props.carId)
    $('.cs-booking').show(500);
    
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
            address = address_components[1].long_name + ", " + address_components[0].long_name + ", "+ address_components[2].long_name ;
        },
        error: function (request, error) {
            alert("Request: " + JSON.stringify(request));
        }
    });
    return address;
}






