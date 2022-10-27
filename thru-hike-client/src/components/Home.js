import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import SectionAlerts from "./SectionAlerts";
import ExitItemDisplay from "./ExitItemDisplay";

const DEFAULT_SECTION = {trailSectionId: 0, trailId: 0, sectionStart: "", sectionEnd: "", latitude: 0, longitude: 0, sectionLength: 0,sectionDays: 0, upcoming: true, trail: {trailName: ""}};

function Home (){
    const [section, setSection] = useState(DEFAULT_SECTION);
    const sectionId = 1;
    const history = useHistory();

    const [incomplete, setIncomplete]= useState([]);

    useEffect(()=>{
        if(sectionId){
            fetch(`http://localhost:8080/api/sections/${sectionId}`)
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
    <h2>Section Summary: {section.sectionStart} - {section.sectionEnd}</h2>
    <button className={incomplete.length===0 ? "btn btn-green mb-3" : "btn btn-red mb-3"} onClick={handleExitChecklistClick}><strong>{incomplete.length===0 ? "Completed" : "Not Completed"}</strong>: Town Exit Checklist </button>
    </>)
}
export default Home;