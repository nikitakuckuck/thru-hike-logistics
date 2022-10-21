import { useHistory } from "react-router-dom";

function Trail({trail, editMode}){
    const history = useHistory();

    const handleEditClick = ()=> history.push(`/trails/edit/${trail.trailId}`);

    const handleDeleteClick = () => history.push(`/trails/delete/${trail.trailId}`);
 

    return (<>
    <tr>
        <td>{trail.trailName } ({trail.trailAbbreviation})</td>
        <td>{editMode === true ? <button className="btn btn-primary btn btn-sm mr-2" onClick={handleEditClick}>Edit</button>
         : null}
         {editMode === true ? <button className="btn btn-primary btn btn-sm" onClick={handleDeleteClick}>Delete</button>
         : null}
            
        </td>
    </tr>
    </>)
}
export default Trail;