import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";

const DEFAULT_TRAIL = {trailId: 0, trailName: '', trailAbbreviation: ''};

function TrailDeleteConfirmation (){
    const [trail, setTrail] = useState(DEFAULT_TRAIL);
    const history = useHistory();
    const {deleteTrailId} = useParams();

    const handleCancelDelete = ()=>history.push("/trails");

    useEffect(()=>{
        if(deleteTrailId){
            fetch(`http://localhost:8080/api/trails/${deleteTrailId}`)
            .then(resp =>{
                switch(resp.status){
                    case 200:
                        return resp.json();
                    case 404:
                            //add not found routing
                        break;
                    default:
                        return Promise.reject("Something has gone wrong.");
                }
            })
            .then(body =>{setTrail(body);})
            .catch(err=>console.log("Error: ", err));
        }
    }, [deleteTrailId]);

    const handleConfirmDelete = () => {
        const init = {
            method: 'DELETE'
        };
        fetch(`http://localhost:8080/api/trails/${deleteTrailId}`, init)
        .then(resp =>{
            switch(resp.status){
                case 204:
                    history.push('/trails');
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
        <p>{trail.trailAbbreviation === ""? `${trail.trailName}`: `${trail.trailName} (${trail.trailAbbreviation})`}</p>
    </div>
    <p>Are you sure you want to delete this trail? This action will <strong> also delete all sections, notes, etc. that are associated with this trail.</strong></p>
    <div>
        <button type="button" className="btn btn-red mr-2" onClick={handleConfirmDelete}>Delete this trail and all its associated data</button>
        <button type="button" className="btn btn-blue" onClick={handleCancelDelete}>Do Not Delete</button>
    </div>
    
    </>)
}
export default TrailDeleteConfirmation;