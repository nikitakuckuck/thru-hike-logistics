import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import Trail from "./Trail";

const DEFAULT_TRAILS = [];

function TrailDisplay(){
    const [trails, setTrails]= useState(DEFAULT_TRAILS);
    let[editMode, setEditMode] = useState(false);
    const history = useHistory();

    useEffect(()=>{
        fetch('http://localhost:8080/api/trail')
        .then(resp =>{
            switch(resp.status){
                case 200:
                    return resp.json();
                default: Promise.reject("Something has gone wrong.");
            }
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

    const handleAddClick= ()=> history.push('/trails/add');

    return(<>
    <h2>Trails</h2>
    <button className="btn btn-sm btn-green mb-3 mr-2" onClick={handleAddClick}>Add</button>
    <button onClick={editModeClick} className="btn btn-sm btn-blue mb-3" >{editMode === false ? "Edit or Delete a Trail" : "Exit Edit Mode"}</button>
    <table>
        <caption>List of Trails</caption>
        <thead></thead>
        <tbody>
            {trails.map(trail => <Trail key={trail.trailId} trail = {trail} editMode = {editMode}/>)}
        </tbody>
    </table>

    </>)
}
export default TrailDisplay;