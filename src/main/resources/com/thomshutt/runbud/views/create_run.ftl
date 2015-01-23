<#include "header.ftl">
    <form action="/runs/create" method="post">
        <input type="number" name="distance_km" value="1" />
        <input type="text" name="initiating_user_id" value="1" />
        <input type="text" name="start_location" value="London Fields" />
        <input type="text" name="description" value="Some description..." />
        <input type="submit" />
    </form>

    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src='http://maps.google.com/maps/api/js?sensor=false&libraries=places'></script>
    <script src="/assets/js/locationpicker.jquery.js"></script>

    <div id="somecomponent" style="width: 500px; height: 400px;"></div>
    <script>
        var mapPosChanged = function(currentLocation, radius, isMarkerDropped) {
           console.log("!!!");
           console.log(currentLocation);
           var addressComponents = $('#somecomponent').locationpicker('map').location.addressComponents;
           console.log(addressComponents);
        }

        $('#somecomponent').locationpicker({
            onchanged: mapPosChanged
        });

        navigator.geolocation.getCurrentPosition(function(position){
             var lat = position.coords.latitude;
             var long = position.coords.longitude;
             $('#somecomponent').locationpicker('map').map.setCenter({lat: lat, lng: long})
             $('#somecomponent').locationpicker('map').marker.setPosition({lat: lat, lng: long})
             setTimeout(function(){
                mapPosChanged(position.coords);
                }, 10000);
        });
    </script>
<#include "footer.ftl">
