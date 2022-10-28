import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import SectionAlert from "./SectionAlert";

const DEFAULT_SECTION = {trailSectionId: 0, trailId: 0, sectionStart: "", sectionEnd: "", latitude: 0, longitude: 0, sectionLength: 0,sectionDays: 0, upcoming: true, trail: {trailName: ""}};

function Home (){
    const [activeSection, setActiveSection] = useState(DEFAULT_SECTION);
    const history = useHistory();

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
                        history.push("/sections")
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
            .catch(err=>console.log("Error: ", err));
        }
    );



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
    })



    const handleExitChecklistClick = ()=> history.push("exit-checklist");
    
    
    return(<>
    <h2>Section Summary: {activeSection.sectionStart} - {activeSection.sectionEnd}</h2>
    <p className="mt-10">Section can be switched by setting a different section as active on <a href ="/sections">Sections</a>.</p>
    <div className="alert alert-red" role="alert">
        <p>Alerts:</p>
        <ul>
              {alerts.map(alert =><SectionAlert key={alert.sectionAlertId} alert = {alert}/>)}
        </ul>
      </div>
    {/* TODO: possibly add logic so the button is only visible when the checklist has at least one item? */}
    <button className={incomplete.length===0 ? "btn btn-green mb-3" : "btn btn-red mb-3"} onClick={handleExitChecklistClick}><strong>{incomplete.length===0 ? "Completed" : "Not Completed"}</strong>: Town Exit Checklist </button>

    </>)
}
export default Home;