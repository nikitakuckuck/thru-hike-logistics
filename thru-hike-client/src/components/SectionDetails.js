import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import SectionAlert from "./SectionAlert";

const DEFAULT_SECTION = {trailSectionId: 0, trailId: 0, sectionStart: "", sectionEnd: "", latitude: 0, longitude: 0, sectionLength: 0,sectionDays: 0, upcoming: true, trail: {trailName: ""}};


function SectionDetails (){
    const {sectionDetailsId}= useParams();
    const [section, setSection] = useState(DEFAULT_SECTION);
    const [alerts, setAlerts] = useState([]);
    const history = useHistory();
    const [alertDeleteMode, setAlertDeleteMode] = useState(false);

    useEffect(()=>{
        if(sectionDetailsId){

        fetch(`http://localhost:8080/api/sections/${sectionDetailsId}`)
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
    },[sectionDetailsId])

    useEffect(()=>{
        if(sectionDetailsId){
        fetch(`http://localhost:8080/api/alerts/section/${sectionDetailsId}`)
        .then(resp =>{
            switch (resp.status){
                case 200:
                    return resp.json();
                default: Promise.reject("Something has gone wrong.");
            }
        })
        .then(data =>{
            setAlerts(data);
        })
        .catch(err=>console.log("Error: ", err));
    }
    },[sectionDetailsId])

    const handleAddAlert = ()=>{history.push(`/sections/add-alert/${sectionDetailsId}`)}
    const handleBackToSections = ()=>{history.push("/sections")}

    const handleDeleteModeClick = ()=>{
        if(alertDeleteMode === true){
            setAlertDeleteMode(false);
        } else {
            setAlertDeleteMode(true);
        }
    }
    
    return(<>
    <h2>Section Details: </h2>
    <h3>{section.sectionStart} - {section.sectionEnd}</h3>
    <div>
        <p>Length: {section.sectionLength} miles, {section.sectionDays} days</p>
        

        
    </div>
    <div >
        <p>Section alerts: {alerts.length===0? "No alerts" : null}</p>
        {alerts.length===0? null :   
        <table>
            <thead>
                <tr></tr>
                <tr></tr>
            </thead>
            <tbody>
                {alerts.map(alert =><SectionAlert key={alert.alertId} alert = {alert} alertDeleteMode= {alertDeleteMode}/>)}
            </tbody>
              
        </table>
        
        }
        <button className="btn btn-green mt-3" onClick={handleAddAlert}>Add an alert for this section</button>
        <button className="btn btn-red ml-2 mt-3" onClick={handleDeleteModeClick}>{alertDeleteMode === false ? "Delete an Alert" : "Exit Alert Delete Mode"}</button>
      </div>
      <button className="btn btn-blue mt-3" onClick={handleBackToSections}>Back to all sections</button>

    </>)
}
export default SectionDetails;