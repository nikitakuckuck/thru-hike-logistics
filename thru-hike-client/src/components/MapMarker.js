
import { useMapEvents, Marker } from "react-leaflet";

function MapMarker({position, setPosition}){

    useMapEvents({
        click: (e)=> {
           
            setPosition(e.latlng);

        },
    })
    return position === null? null :(
        <Marker position = {position}></Marker>
    );
}
export default MapMarker;