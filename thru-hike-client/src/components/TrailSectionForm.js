import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import Error from "./Error";
import { MapContainer, TileLayer } from 'react-leaflet';
import MapMarker from "./MapMarker";



const DEFAULT_SECTION = {trailSectionId: 0, trailId: 0, sectionStart: "", sectionEnd: "", startLatitude: 0, startLongitude: 0, endLatitude: 0, endLongitude: 0, sectionLength: 0,sectionDays: 0, upcoming: true};

function TrailSectionForm (){
const [trails, setTrails]=useState([]);
const [section, setSection]=useState(DEFAULT_SECTION);
const history = useHistory();
const [errors, setErrors] = useState([]);
const {editSectionId} = useParams();

let location = [37.0902, -95.7129];
const [startPosition, setStartPosition] = useState(null);
const [endPosition, setEndPosition] = useState(null);





    useEffect(()=>{
        if(editSectionId){
            fetch(`http://localhost:8080/api/sections/${editSectionId}`)
            .then(resp=>{
                switch(resp.status){
                    case 200:
                        return resp.json();
                    case 404:
                        return null;
                        //add routing to not found
                    default:
                        return Promise.reject("Something has gone wrong.");
                }
            })
            .then(body =>{
                if(body.trailSectionId){
                    setSection(body);
                    setStartPosition([body.startLatitude, body.startLongitude]);
                    setEndPosition([body.endLatitude, body.endLongitude]);
                }
            })
            .catch(err=>console.log("Error: ", err));
        }
    },[editSectionId]);

    const saveSection = ()=>{
        const sectionWithCoordinates = {...section};
        sectionWithCoordinates.startLatitude = startPosition.lat;
        sectionWithCoordinates.startLongitude = startPosition.lng;
        sectionWithCoordinates.endLatitude = endPosition.lat;
        sectionWithCoordinates.endLongitude = endPosition.lng;

        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(sectionWithCoordinates)
        };
        fetch('http://localhost:8080/api/sections', init)
        .then(resp =>{
            switch(resp.status){
                case 201:
                case 400:
                    return resp.json();
                default:
                    return Promise.reject("Something has gone wrong.");
            }
        })
        .then(body =>{
            if(body.trailSectionId){
                history.push('/sections');
            } else if(body){
                setErrors(body);
            }
        })
        .catch(err => console.log("Error: ", err));
    }


    useEffect(()=>{
        fetch('http://localhost:8080/api/trails')
        .then(resp=>{
            switch(resp.status){
                case 200:
                    return resp.json();
                    default:
                        return Promise.reject("Something has gone wrong.");
            }
        })
        .then(body =>{
            if(body){
                setTrails(body);
            }
        })
        .catch(err=>console.log("Error: ", err));
    },[])

    const updateSection = ()=>{
        //TODO: update coordinates??
        const init = {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(section)
        };
        fetch(`http://localhost:8080/api/sections/${editSectionId}`,init)
        .then(resp =>{
            switch(resp.status){
                case 204:
                    return null;
                case 400:
                    return resp.json();
                case 404:
                    return null;
                    //add routing to not found pg
                default:
                    return Promise.reject("Something has gone wrong");
            }
        })
        .then(body =>{
            if(!body){
                history.push('/sections');
            } else {
                setErrors(body);
            }
        })
        .catch(err => console.log("Error: ", err));
    }


    const onSubmit= evt =>{
        evt.preventDefault();
        const functionChoice = editSectionId > 0 ? updateSection : saveSection;
        functionChoice();
    }

    const handleChange = evt =>{
        const property = evt.target.name;
        const valueType = evt.target.type === "checkbox" ? "checked" : "value";
        const value = evt.target[valueType];

        const newSection = {...section};
        newSection[property] = value;

        setSection(newSection);
    }

    const handleCancel = ()=>history.push('/sections');



    return(<>
    <h2>{editSectionId ? "Edit" : "Add"} a Section:</h2>
    <p className="mb-4">If you do not see the trail you want in the trail dropdown, <a href="/trails/add">add the trail</a> first.</p>
    {errors.length >0 ? <Error errors = {errors}/> : null}
    <form onSubmit={onSubmit}>
        <div className="form-group">
            <label htmlFor="trailId">Trail</label>
            <select name="trailId" className ="form-control" id="trailId" value={section.trailId} onChange={handleChange}>
                <option>Please select a trail:</option>
                {trails.map(trail =><option key={trail.trailId} value={trail.trailId}>{trail.trailAbbreviation === ""? `${trail.trailName}`: `${trail.trailName} (${trail.trailAbbreviation})`}</option>)}
            </select>
        </div>
        <div className="form-group">
            <label htmlFor="sectionStart">Starting Town Name</label>
            <input name="sectionStart" type="text" className="form-control" id="sectionStart" value={section.sectionStart} onChange={handleChange}/>
        </div>

        <div className="pb-3">
            <label htmlFor="sectionStartMap">Click on the starting town to assign a location which will be used on the section summary to display weather forecasts, air quality alerts, etc.</label>
            <MapContainer center={location} zoom={4} scrollWheelZoom={true}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <MapMarker position={startPosition} setPosition={setStartPosition}/>
               
            </MapContainer>
        </div>

        <div className="form-group">
            <label htmlFor="sectionEnd">Ending Town Name</label>
            <input name="sectionEnd" type="text" className="form-control" id="sectionEnd" value={section.sectionEnd} onChange={handleChange}/>
        </div>
        <div className="pb-3">
            <label htmlFor="sectionEndMap">Click on the ending town to assign a location which will be used on the section summary to display weather forecasts, air quality alerts, etc.</label>
            <MapContainer center={location} zoom={4} scrollWheelZoom={true}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
               <MapMarker position={endPosition} setPosition={setEndPosition}/>
            </MapContainer>
        </div>

        <div className="form-group">
            <label htmlFor="sectionDays">Total Days</label>
            <input name="sectionDays" type="number" className="form-control" id="sectionDays" value={section.sectionDays} onChange={handleChange}/>
        </div>
        <div className="form-group">
            <label htmlFor="sectionLength">Section Length (miles)</label>
            <input name="sectionLength" type="number" className="form-control" id="sectionLength" value={section.sectionLength} onChange={handleChange}/>
        </div>
        <div className="form-group ml-4">
            <input type="checkbox" id="upcoming" name="upcoming" className="form-check-input" checked={section.upcoming} onChange={handleChange}/>
            <label htmlFor="upcoming">Upcoming? (this box should only be unchecked if you have already passed through this section)</label>
        </div>
        <div className="form group">
            <button type="submit" className="btn btn-green mr-2">Submit</button>
            <button type="button" className="btn btn-blue" onClick={handleCancel}>Cancel</button>
        </div>
    </form>

    </>)
}
export default TrailSectionForm;