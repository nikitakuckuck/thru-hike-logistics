import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import Error from "./Error";

const DEFAULT_SECTION = {trailSectionId: 0, trailId: 0, sectionStart: "", sectionEnd: "", latitude: 0, longitude: 0, sectionLength: 0,sectionDays: 0, trail: {trailName: ""}};
const DEFAULT_ALERT = {alertId: 0, alertContent: "", trailSectionId:0, alertCategoryId: 0};

function SectionAlertForm (){

        const {sectionId}= useParams();
        const [section, setSection] = useState(DEFAULT_SECTION);
        const [alert, setAlert] = useState(DEFAULT_ALERT);
        const history = useHistory();
        const [errors, setErrors] = useState([]);

        useEffect(()=>{
        if(sectionId){

        fetch(`http://localhost:8080/api/sections/${sectionId}`)
        .then(resp=>{
            switch(resp.status){
                case 200:
                    return resp.json();
                case 404:
                    //add not found routing
                    return null;
                default:
                    return Promise.reject("Something has gone wrong.");
            }
        })
        .then(body =>{
            if(body){
                setSection(body);
            }
        })
        .catch(err=>console.log("Error: ", err)); 
     }
    },[sectionId])


    const saveAlert = ()=>{
        alert.trailSectionId=sectionId;
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({...alert})
        };
        fetch('http://localhost:8080/api/alerts', init)
        .then(resp=>{
            switch(resp.status){
                case 201:
                case 400:
                    return resp.json();
                default:
                    return Promise.reject("Something has gone wrong.")
            }
        })
        .then(body=>{
            if(body.alertId){
                //TODO routing
            } else if(body){
                setErrors(body);
            }
        })
        .catch(err=>console.log("Error: ", err));
    }

    const onSubmit = evt =>{
        evt.preventDefault();
        saveAlert();
    }

    const handleChange = evt =>{

        const property = evt.target.name;
        const valueType = evt.target.type === "checkbox" ? "checked" : "value";
        const value = evt.target[valueType];

        const newAlert = {...alert};
        newAlert[property] = value;

        setAlert(newAlert);
    }

    const handleCancel = ()=>history.push("/sections/details/"+sectionId);

    return(<>
    <h2>Add an Alert for: {section.sectionStart}-{section.sectionEnd}</h2>
    <p>Tip: you can paste links into the alert content field if you want links to outside resources - for example, the incident page for a specific fire - to show up in your alerts section.</p>
    {errors.length >0 ? <Error errors={errors}/> : null}
    <form onSubmit={onSubmit}>
        <div className="form-group">
            <label htmlFor="alertCategoryId">Category</label>

            <select name="alertCategoryId" className="form-control" id="alertCategoryId" value={alert.alertCategoryId} onChange={handleChange}>
                <option>Please select a category:</option>
                {/* calendar option is only located here until a separate calendar reminder page is built */}
                <option value={7}>CALENDAR </option>
                <option value={2}>CLOSURE</option>
                <option value={5}>FIRE</option>
                <option value={8}>GEAR</option>
                <option value={3}>WATER</option>
                <option value={4}>WEATHER</option>
                <option value={1}>OTHER</option>
            </select>
        </div>
        <div className="form-group">
            <label htmlFor="alertContent">Alert Content</label>
            <input name="alertContent" type="text" className="form-control" id="alertContent" value={alert.alertContent} onChange={handleChange}/>
        </div>
        {/* todo: refactor this to give the option of assigning the alert to specific sections
        <div className="form-group ml-4">
        <input type="checkbox" id="futureSections" name="futureSections" className="form-check-input" checked={alert.futureSections} onChange={handleChange}/>
            <label htmlFor="futureSections">Do you want this alert to also show up for additional sections?</label>
        </div> */}
            <div className="form group">
        <button type="submit" className="btn btn-green mr-2">Submit</button>
        <button type="button" className="btn btn-blue" onClick={handleCancel}>Cancel</button>
    </div>
    </form>

    </>)
}

export default SectionAlertForm;