import { useEffect, useState } from "react";
import TrailSection from "./TrailSection";
const DEFAULT_SECTIONS = [];

function TrailSectionDisplay (){
    const [sections, setSections]= useState(DEFAULT_SECTIONS);
    const [sectionEditMode, setSectionEditMode] = useState(false);

    useEffect(()=>{
        fetch('http://localhost:8080/api/section')
        .then(resp =>{
            if(resp.status===200){
                return resp.json();
            }
            return Promise.reject("Something has gone wrong.");
        })
        .then(data=>{
            setSections(data);
        })
        .catch(err=>console.log("Error: ", err));
    }, [])





    function editModeClick(){
        if(sectionEditMode === true){
            setSectionEditMode(false);
        } else {
            setSectionEditMode(true);
        }
    }

    return(<>
    <h2>Trail Sections</h2>
    <button className="btn btn-sm btn-primary mb-3 mr-2">Add a Section</button>
    <button onClick={editModeClick} className="btn btn-sm btn-primary mb-3" >{sectionEditMode === false ? "Edit or Delete a Section" : "Exit Edit Mode"}</button>
    <table className="table">
        <thead className="thead-dark">
            <tr>
                <th>Status</th>
                <th>Start</th>
                <th>End</th>
                <th>Miles</th>
                <th>Days</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            {sections.map(section =><TrailSection key={section.trailSectionId} section = {section} sectionEditMode = {sectionEditMode}/>)}
        </tbody>
    </table>
    </>)
}
export default TrailSectionDisplay;