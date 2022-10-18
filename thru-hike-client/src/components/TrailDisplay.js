import { useEffect, useState } from "react";
import Trail from "./Trail";

const DEFAULT_TRAILS = []

function TrailDisplay(){
    const [trails, setTrails]= useState(DEFAULT_TRAILS);

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

    return(<>
    <h2 className="mt-3">Trails</h2>
    <button className="btn btn-sm btn-primary mb-3 mr-2">Add aTrail</button>
    {/* on click, change edit button to be exit edit mode */}
    <button className="btn btn-sm btn-primary mb-3">Edit or Delete a Trail</button>
    <table>
        <thead></thead>
        <tbody>
            {trails.map(trail => <Trail key={trail.trailId} trail = {trail} />)}
        </tbody>
    </table>

    </>)
}
export default TrailDisplay;