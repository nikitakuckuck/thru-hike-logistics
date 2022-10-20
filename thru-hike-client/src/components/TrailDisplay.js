import { useEffect, useState } from "react";
import Trail from "./Trail";

const DEFAULT_TRAILS = []
const DEFAULT_EDIT_MODE = false;

function TrailDisplay(){
    const [trails, setTrails]= useState(DEFAULT_TRAILS);
    let[editMode, setEditMode] = useState(false);

    useEffect(()=>{
        fetch('http://localhost:8080/api/trail')
        .then(resp =>{
            if(resp.status===200){
                return resp.json();
            }
            return Promise.reject("Something has gone wrong.");
        })
        .then(data=>{
            setTrails(data);
        })
        .catch(err=>console.log("Error: ",err));
    }, [])

    function editModeClick(){
        if(editMode === true){
            setEditMode(false);
        } else {
            setEditMode(true);
        }
    }

    return(<>
    <h2 className="mt-3">Trails</h2>
    <button className="btn btn-sm btn-primary mb-3 mr-2">Add a Trail</button>
    <button onClick={editModeClick} className="btn btn-sm btn-primary mb-3" >{editMode === false ? "Edit or Delete a Trail" : "Exit Edit Mode"}</button>
    <table>
        <thead></thead>
        <tbody>
            {trails.map(trail => <Trail key={trail.trailId} trail = {trail} editMode = {editMode}/>)}
        </tbody>
    </table>

    </>)
}
export default TrailDisplay;