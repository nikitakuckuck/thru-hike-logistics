import { useHistory } from "react-router-dom";
import SectionAlerts from "./SectionAlerts";

function TownExitDisplay ({allComplete}){
    const history = useHistory();
    
    const handleChange = () =>{
        let checklistBoxes = document.getElementById("exitChecklist").getElementsByTagName("input");
        let boxCount = 0;
    for(let i = 0; i<checklistBoxes.length; i++){
        if(!checklistBoxes[i].checked){
            allComplete=false;
        } else{
            boxCount++;
            // change color of label to grey out?
        }
    }
    if(boxCount===checklistBoxes.length){
        allComplete=true;
    }
    }

    const handleBackToSummary = ()=> history.push("");
    return(<>
        <h2>Town Exit Checklist</h2>
        <form id="exitChecklist">
            <div className="form-group ml-4">
                <input type="checkbox" id="checkAlerts" name="checkAlerts" className="form-check-input" onChange={handleChange}/>
                <label htmlFor="checkAlerts">Check Alerts and Reports</label>
                <SectionAlerts/>
            </div>
            <div className="form-group ml-4">
                <input type="checkbox" id="test" name="test" className="form-check-input" onChange={handleChange}/>
                <label  htmlFor="Test">Test</label>
            </div>
        </form>
        <button onClick={handleBackToSummary} className = "btn btn-blue">Back To Section Summary</button>
    </>)
}
export default TownExitDisplay;