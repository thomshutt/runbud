<#include "header.ftl">
    <h1>Create A Run</h1>

    <div id="googleMap" style="width: 500px; height: 400px; display: inline-block; margin-right: 20px;"></div>

    <div style="display: inline-block; vertical-align: top; text-align: left;">
        <form action="/runs/create" method="post">
            <label>Run Name</label>
            <input type="text" name="run_name" placeholder="Give your run a name..." value="${runName}" />

            <label>Distance (km)</label>
            <input type="number" name="distance_km" value="${distanceKm}" />

            <label>Start Time</label>
            <select name="start_time_hours">
                <#assign x=24>
                <#list 0..x as i>
                  <#if i = startTimeHours>
                    <option value="${i}" selected="true">${i}</option>
                  <#else>
                    <option value="${i}">${i}</option>
                  </#if>
                </#list>
            </select>
            :
            <select name="start_time_mins">
                <option value="00">00</option>
                <option value="15">15</option>
                <option value="30">30</option>
                <option value="45">45</option>
            </select>

            <label>Description</label>
            <textarea rows="4" name="description" placeholder="How people can find the start point, what sort of pace you'll be running at etc.">${description}</textarea>

            <input id="inputLatitude" type="hidden" name="start_latitude" value="${startLatitude}" />
            <input id="inputLongitude" type="hidden" name="start_longitude" value="${startLongitude}" />
            <input id="inputAddress" type="hidden" name="start_address" value="${startAddress}}" />
            <input type="submit" value="Create Run" />
        </form>
    </div>

    <script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src='//maps.google.com/maps/api/js?sensor=false&libraries=places'></script>
    <script type="text/javascript" src="/assets/js/locationpicker.jquery.js"></script>

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
