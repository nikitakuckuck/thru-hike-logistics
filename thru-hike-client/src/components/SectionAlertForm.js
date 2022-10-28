import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import Error from "./Error";

const DEFAULT_SECTION = {trailSectionId: 0, trailId: 0, sectionStart: "", sectionEnd: "", latitude: 0, longitude: 0, sectionLength: 0,sectionDays: 0, upcoming: true, trail: {trailName: ""}};
const DEFAULT_ALERT = {alertId: 0, alertContent: "", trailSectionId:0, futureSections:false, alertCategory: {alertCategoryId: 0,alertCategoryName: "" }};

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
        const value = evt.target.value;

        const newAlert = {...alert};
        newAlert[property] = value;

        setAlert(newAlert);
    }

    //TODO: FIX
    const handleCancel = ()=>history.push(`sections/details/${sectionId}`)

    return(<>
    <h2>Add an Alert for: {section.sectionStart}-{section.sectionEnd}</h2>
    {errors.length >0 ? <Error errors={errors}/> : null}
    <form onSubmit={onSubmit}>
        <div className="form-group">
            <label htmlFor="alertCategory">Category</label>

            {/* not going to work: refactor this */}
            <select name="alertCategory" className="form-control" id="alertCategory" value={alert.alertCategory} onChange={handleChange}>
                <option>Please select a category:</option>
                <option data-value={{"alertCategoryId": 1, "alertCategoryName": "OTHER"}}>Other</option>
                <option data-value={{alertCategoryId: 2, alertCategoryName: "CLOSURE"}}>Closure</option>
            </select>
        </div>
        <div className="form-group">
            <label htmlFor="alertContent">Alert Content</label>
            <input name="alertContent" type="text" className="form-control" id="alertContent" value={alert.alertContent} onChange={handleChange}/>
        </div>
            <div className="form group">
        <button type="submit" className="btn btn-green mr-2">Submit</button>
        <button type="button" className="btn btn-blue" onClick={handleCancel}>Cancel</button>
    </div>
    </form>

    </>)
}

export default SectionAlertForm;