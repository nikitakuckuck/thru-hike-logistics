import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import SectionAlert from "./SectionAlert";
import TrailImage from "./../images/forest.png";


const DEFAULT_SECTION = {trailSectionId: 0, trailId: 0, sectionStart: "", sectionEnd: "", startLatitude: 0, startLongitude: 0, endLatitude: 0, endLongitude: 0, sectionLength: 0,sectionDays: 0, upcoming: true, trail: {trailName: ""}};



function Home (){
    const [activeSection, setActiveSection] = useState(DEFAULT_SECTION);
    const history = useHistory();
    const [weatherStart, setWeatherStart] = useState([{}]);
    const [weatherEnd, setWeatherEnd] = useState([{}]);
    const [incomplete, setIncomplete]= useState([]);
    const [alerts, setAlerts] = useState([]);
 
    //arrival day 
    let arrivalDate = new Date(Date.now());
    arrivalDate.setDate(arrivalDate.getDate() + activeSection.sectionDays-1); //arrival date counts the current day

    function arrivalDateWarning (dayofWeek) {
        switch(dayofWeek){
            case 6:
                return "today or tomorrow";
            case 0:
                return "today";
            case 5:
                return "tomorrow"
            default:
                return "error";
        }
    }


    useEffect(()=>{
        let endLat, endLong, sectionId;

        //gets active section
        fetch(`http://localhost:8080/api/sections/active`)
        .then(resp=>{
            switch(resp.status){
                case 200:
                    return resp.json();
                case 404:
                    //TODO: decide what happens when there is no active section - reject promise?
                    break;
                default:
                    return Promise.reject("Something has gone wrong.");
            }
        })
        .then(body =>{
            if(!body){
                return;
            } 
            setActiveSection(body);
            endLat = body.endLatitude;
            endLong = body.endLongitude;
            sectionId = body.trailSectionId;

            //gets starting location's weather grid endpoint from lat/long data
            return fetch(`https://api.weather.gov/points/${body.startLatitude},${body.startLongitude}`);
            
        })
        .then(resp=>{
            switch (resp.status){
                case 200:
                    return resp.json();
                case 404:
                    return;
                default: Promise.reject("Something has gone wrong.");
            }    
        })
        .then(({properties: {forecast}}) =>{

            return fetch(`${forecast}`); //gets forecast data
        })
        .then(resp=>{
            switch (resp.status){
                case 200:
                    return resp.json();
                case 404:
                    return;
                default: Promise.reject("Something has gone wrong.");
            }    
        })
        .then(({properties: {periods}})=>{
  
            setWeatherStart(periods);

            // gets ending location's weather grid endpoint from lat/long data
            return fetch(`https://api.weather.gov/points/${endLat},${endLong}`); 
        })
        .then(resp=>{
            switch (resp.status){
                case 200:
                    return resp.json();
                default: Promise.reject("Something has gone wrong.");
            }    
        })
        .then(({properties: {forecast}}) =>{
            
            return fetch(`${forecast}`); //gets forecast data
        })
        .then(resp=>{
            switch (resp.status){
                case 200:
                    return resp.json();
                default: Promise.reject("Something has gone wrong.");
            }    
        })
        .then(({properties: {periods}})=>{
            setWeatherEnd(periods);  

            //gets alerts for active section
            return fetch(`http://localhost:8080/api/alerts/section/${sectionId}`);
        })
        .then(resp =>{
            switch (resp.status){
                case 200:
                    return resp.json();
                default: Promise.reject("Something has gone wrong.");
            }
        })
        .then(data =>{setAlerts(data);})
        
        .catch(err=>console.log("Error: ", err));
    },[]);

 
//fetching the incomplete town exit checklist items, so the color/message on the checklist button can be customized
    useEffect(()=>{
        fetch(`http://localhost:8080/api/exit-items/incomplete`)
        .then(resp =>{
            switch(resp.status){
                case 200:
                    return resp.json();
                default: Promise.reject("Something has gone wrong.");
            }
        })
        .then(data=>{
            setIncomplete(data);
        })
        .catch(err=>console.log("Error: ", err));
    },[])


    const handleExitChecklistClick = ()=> history.push("exit-checklist");
    

    return(<>
    {activeSection.trailSectionId===0 ? //if there is no active section:
    <div>
        <h2>Section Summary</h2>
        <p className="mt-2">Welcome! Once you have created trails and sections, a summary for your active section will show up on this page.</p>
        <p>To get started, check out the <a href = "/about">About</a> page for more information on available features, and then <a href ="/trails/add">add a trail</a>. Once you have added at least one trail, you'll be able to start adding sections.</p>
        <p>You can choose which section is displayed here by setting a section as active on the <a href ="/sections">Sections</a> page.</p>
        <div className="picture-with-credits mt-3">
            <img src={TrailImage} className="img-fluid " alt="a stylized design of stairsteps going up a trail in the forest"/>
            <p className="small">Image by <a href="https://pixabay.com/users/freefunart-8472313/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=7161510">Amy</a> from Pixabay</p>
        </div>
        
    </div>
    :
    <div>
    <h2>Section Summary: {activeSection.sectionStart} - {activeSection.sectionEnd}</h2>
    <p className="mb-4">Section can be switched by setting a different section as active on <a href ="/sections">Sections</a>.</p>
    <div className="alert alert-red" role="alert">
        <p>Alerts:</p>
        <table>
            <tbody>
            {alerts.map(alert =><SectionAlert key ={alert.alertId} alert = {alert}/>)}
              {arrivalDate.getDay() === 6 || arrivalDate.getDay()===0 || arrivalDate.getDay()===5 ?<tr><td>- WEEKEND: if you leave {arrivalDateWarning(arrivalDate.getDay())}, you are scheduled to arrive in the next town on a weekend. <button className="btn btn-link btn-small" data-toggle="modal" data-target="#arrivalInfo"><small>How is arrival day calculated?</small></button></td></tr>: null}
            </tbody>
              
        </table>
    </div>
    <div className="mt-4">
        {/* TODO: possibly add logic so the button is only visible when the checklist has at least one item? */}
    <button className={incomplete.length===0 ? "btn btn-green mb-3" : "btn btn-red mb-3"} onClick={handleExitChecklistClick}><strong>{incomplete.length===0 ? "Completed" : "Not Completed"}</strong>: Town Exit Checklist </button>
    </div>
      

    {/* weather display */}
    <h4 className="mt-3">7 Day Weather Forecast:</h4>
    <div className="row mr-1 ml-1" >
        <div className="text-wrap border border-dark col-sm">
            <h5><strong>{activeSection.sectionStart}</strong></h5>
            {weatherStart.map(weather =><p key={weather.number}><strong>{weather.name}</strong>: {weather.detailedForecast}</p>)}
                  
        </div>
        <div className="text-wrap border border-dark col-sm">
            <h5><strong>{activeSection.sectionEnd}</strong></h5>
            {weatherEnd.map(weather =><p key={weather.number}><strong>{weather.name}</strong>: {weather.detailedForecast}</p>)}       
        </div>
    </div>
    </div>

    }
 

    {/* user info for how weekend alerts are calculated */}
    <div className="modal fade" id="arrivalInfo" role="dialog" aria-labelledby="addTitle" aria-hidden="true">
        <div className="modal-dialog modal-dialog-centered" role="document">
            <div className="modal-content">
                <div className="modal-header">
                     <button type="button" className="close" data-dismiss="modal" aria-label = "Close">
                        <span aria-hidden = "true">&times;</span>
                    </button>
                </div>
                <div className="modal-body">
                    <p>Scheduled arrival day is calculated based on the total number of days for a section. The day you leave counts as a whole day, as well as the day you arrive.</p>
                    <p>Example: if you have a section that you've estimated will take 4 days of hiking and you leave a town on Monday, your scheduled arrival will be Thursday.</p>
                </div>
            </div>
        </div>
    </div>
    </>)
}
export default Home;