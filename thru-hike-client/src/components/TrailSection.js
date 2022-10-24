import { useHistory } from "react-router-dom";

function TrailSection({section, sectionEditMode}){
    const history = useHistory();

    const handleEditClick = ()=> history.push(`/sections/edit/${section.trailSectionId}`)

    const handleDeleteClick = ()=>history.push(`/sections/delete/${section.trailSectionId}`)

    return(<>
    <tr>
        <td>{section.upcoming? "Upcoming" : "Past"}</td>
        <td>{section.trail.trailName}</td>
        <td>{section.sectionStart}</td>
        <td>{section.sectionEnd}</td>
        <td>{section.sectionLength}</td>
        <td>{section.sectionDays}</td>
        <td>{sectionEditMode === true ? <button className="btn btn-blue btn btn-sm mr-2" onClick={handleEditClick}>Edit</button>
            : null}
            {sectionEditMode === true ? <button className="btn btn-red btn btn-sm" onClick={handleDeleteClick}>Delete</button>
             : null}
        </td>
    </tr>
    </>)
}
export default TrailSection;