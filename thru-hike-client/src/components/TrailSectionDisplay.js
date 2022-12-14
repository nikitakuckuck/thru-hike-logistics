import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import TrailSection from "./TrailSection";
const DEFAULT_SECTIONS = [];

function TrailSectionDisplay (){
    const [sections, setSections]= useState(DEFAULT_SECTIONS);
    const [sectionEditMode, setSectionEditMode] = useState(false);
    const history = useHistory();

    useEffect(()=>{
        fetch('http://localhost:8080/api/sections')
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

    const handleAddClick = ()=> history.push('/sections/add');

    return(<>
    <h2>My Trail Sections</h2>
    {sections.length===0 ? null :<p className="mb-4">Select the 'Details' button to view section details, add section alerts, or add town contact information. </p>}
    <button onClick = {handleAddClick} className="btn btn-sm btn-green mb-3 mr-2">Add a Section</button>
    {sections.length===0 ? null :<button onClick={editModeClick} className="btn btn-sm btn-blue mb-3" >{sectionEditMode === false ? "Edit or Delete a Section" : "Exit Edit Mode"}</button>}

    {sections.length===0 ? <p>After you've <a href="/trails/add">added a trail</a>, just add a section here to get started. Happy hiking!</p>: 
    <table className="table table-hover">
        <caption>List of Sections</caption>
        <thead className="thead-dark">
            <tr>
                <th></th>
                <th>Status</th>
                <th>Trail</th>
                <th>Start</th>
                <th>End</th>
                <th>Miles</th>
                <th>Days</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            {sections.map(section =><TrailSection key={section.trailSectionId} section = {section} sectionEditMode = {sectionEditMode}/>)}
        </tbody>
    </table>
    }
    </>)
}
export default TrailSectionDisplay;