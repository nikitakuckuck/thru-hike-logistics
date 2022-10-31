import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";

const DEFAULT_SECTION = {trailSectionId: 0, trailId: 0, sectionStart: "", sectionEnd: "", latitude: 0, longitude: 0, sectionLength: 0,sectionDays: 0, upcoming: true, trail: {trailName: ""}};

function TrailSectionDeleteConfirmation(){
    const history = useHistory();
    const {deleteSectionId} = useParams();
    const [section, setSection] = useState(DEFAULT_SECTION);

    const handleCancelDelete = ()=> history.push("/sections");

    useEffect(()=>{
        if(deleteSectionId){
            fetch(`http://localhost:8080/api/sections/${deleteSectionId}`)
            .then(resp =>{
                switch(resp.status){
                    case 200:
                        return resp.json();
                    case 404:
                        //add not found routing
                        break;
                    default:
                        return Promise.reject("Something has gone wrong");
                }
            })
            .then(body =>{
                setSection(body);
            })
            .catch(err=>console.log("Error: ", err));
        }
    })

    const handleConfirmDelete=()=>{
        const init = {
            method: 'DELETE'
        };
        fetch(`http://localhost:8080/api/sections/${deleteSectionId}`, init)
        .then(resp =>{
            switch(resp.status){
                case 204:
                    history.push('/sections');
                    break;
                case 404:
                    //add routing to not found pg
                    break;
                default:
                    return Promise.reject("Something has gone wrong.");

            }
        })
        .catch(err=>console.log("Error: ", err));
    }
    return(<>
        <h2>Delete Confirmation</h2>
    <div className="alert alert-danger" role="alert">
        <p>{section.sectionStart} - {section.sectionEnd}, {section.trail.trailName}</p>
        <p>{section.sectionLength} miles, {section.sectionDays} days</p>
    </div>
    <p>Are you sure you want to delete this section? This action will <strong> also delete all notes, alerts, resupply lists, etc. that are associated with this section.</strong></p>
    <div>
        <button type="button" className="btn btn-red mr-2" onClick={handleConfirmDelete}>Delete this section and all its associated data</button>
        <button type="button" className="btn btn-blue" onClick={handleCancelDelete}>Do Not Delete</button>
    </div>
    </>)
}
export default TrailSectionDeleteConfirmation;