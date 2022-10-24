import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import SectionAlerts from "./SectionAlerts";
import TownExitDisplay from "./TownExitDisplay";

const DEFAULT_SECTION = {trailSectionId: 0, trailId: 0, sectionStart: "", sectionEnd: "", latitude: 0, longitude: 0, sectionLength: 0,sectionDays: 0, upcoming: true, trail: {trailName: ""}};

function Home (){
    const [section, setSection] = useState(DEFAULT_SECTION);
    const sectionId = 6;
    let allComplete = false;
    const history = useHistory();

    useEffect(()=>{
        if(sectionId){
            fetch(`http://localhost:8080/api/section/${sectionId}`)
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
                }
            })
            .catch(err=>console.log("Error: ", err));
        }
    }, [sectionId]);

    const handleExitChecklistClick = ()=> history.push("exit_checklist");
    
    
    return(<>
    <h2>Section Summary: {section.sectionStart} - {section.sectionEnd}</h2>
    <button className={allComplete ? "btn btn-green mb-3" : "btn btn-red mb-3"} onClick={handleExitChecklistClick}><strong>{allComplete ? "Completed" : "Not Completed"}</strong>: Town Exit Checklist</button>
    </>)
}
export default Home;