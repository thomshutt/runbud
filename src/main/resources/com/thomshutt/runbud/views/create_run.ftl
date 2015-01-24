<#include "header.ftl">
    <div id="googleMap" style="width: 500px; height: 400px; display: inline-block; margin-right: 20px;"></div>

    <div style="display: inline-block; vertical-align: top; text-align: left;">
        <form action="/runs/create" method="post">
            <label>Run Name</label>
            <input type="text" name="description" value="Give your run a name..." />

            <label>Distance (km)</label>
            <input type="number" name="distance_km" value="1" />

            <label>Description</label>
            <input type="text" name="description" value="Some description..." />

            <input id="inputLatitude" type="hidden" name="start_latitude" value="51.510730378916186" />
            <input id="inputLongitude" type="hidden" name="start_longitude" value="-0.13398630345454876" />
            <input id="inputAddress" type="hidden" name="start_address" value="Piccadilly Circus, London W1J, UK" />
            <input type="submit" value="Create Run" />
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src='http://maps.google.com/maps/api/js?sensor=false&libraries=places'></script>
    <script src="/assets/js/locationpicker.jquery.js"></script>

    <script>
        var mapPosChanged = function(currentLocation, radius, isMarkerDropped) {
           var address = $('#googleMap').locationpicker('map').location.formattedAddress;
            $('#inputLatitude').val(currentLocation.latitude);
            $('#inputLongitude').val(currentLocation.longitude);
            $('#inputAddress').val(address);
        }

        $('#googleMap').locationpicker({
            onchanged: mapPosChanged
        });

        navigator.geolocation.getCurrentPosition(function(position){
             var lat = position.coords.latitude;
             var long = position.coords.longitude;
             $('#googleMap').locationpicker('map').map.setCenter({lat: lat, lng: long})
             $('#googleMap').locationpicker('map').marker.setPosition({lat: lat, lng: long})
             amazingFunction();
        });
    </script>
<#include "footer.ftl">
