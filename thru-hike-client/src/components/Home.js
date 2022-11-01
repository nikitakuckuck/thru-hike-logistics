import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import SectionAlert from "./SectionAlert";

const DEFAULT_SECTION = {trailSectionId: 0, trailId: 0, sectionStart: "", sectionEnd: "", latitude: 0, longitude: 0, sectionLength: 0,sectionDays: 0, upcoming: true, trail: {trailName: ""}};

function Home (){
    const [activeSection, setActiveSection] = useState(DEFAULT_SECTION);
    const history = useHistory();
    let arrivalDate = new Date(Date.now());
    //arrival date counts the current day
    arrivalDate.setDate(arrivalDate.getDate() + activeSection.sectionDays-1);

    //arrival day 
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


    const [incomplete, setIncomplete]= useState([]);
    const [alerts, setAlerts] = useState([]);



    
    useEffect(()=>{
        fetch(`http://localhost:8080/api/alerts/section/${activeSection.trailSectionId}`)
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
    },[activeSection.trailSectionId])

    useEffect(()=>{
            fetch(`http://localhost:8080/api/sections/active`)
            .then(resp=>{
                switch(resp.status){
                    case 200:
                        return resp.json();
                    case 404:
                        //what should happen when there's no active section???
                        // history.push("/sections")
                        break;
                    default:
                        return Promise.reject("Something has gone wrong.");
                }
            })
            .then(body =>{
                if(body){
                    setActiveSection(body);
                }
            })
            .catch(err=>console.log("Error: ", err))
        },[history]

    )



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
    <h2>Section Summary: {activeSection.sectionStart} - {activeSection.sectionEnd}</h2>
    <p className="mt-10">Section can be switched by setting a different section as active on <a href ="/sections">Sections</a>.</p>
    <div className="alert alert-red" role="alert">
        <p>Alerts:</p>
        <ul>
              {alerts.map(alert =><SectionAlert key ={alert.alertId} alert = {alert}/>)}
              {arrivalDate.getDay() === 6 || arrivalDate.getDay()===0 || arrivalDate.getDay()===5 ?<li>WEEKEND: if you leave {arrivalDateWarning(arrivalDate.getDay())}, you are scheduled to arrive in the next town on a weekend. <button className="btn btn-link btn-small" data-toggle="modal" data-target="#arrivalInfo"><small>How is arrival day calculated?</small></button></li>: null}
        </ul>


      </div>
    {/* TODO: possibly add logic so the button is only visible when the checklist has at least one item? */}
    <button className={incomplete.length===0 ? "btn btn-green mb-3" : "btn btn-red mb-3"} onClick={handleExitChecklistClick}><strong>{incomplete.length===0 ? "Completed" : "Not Completed"}</strong>: Town Exit Checklist </button>
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
                    <p>For example, if you have a section that you've estimated will take 4 days of hiking and you leave a town on Monday, your estimated arrival will be for Thursday.</p>
                </div>
            </div>
        </div>
    </div>
    </>)
}
export default Home;