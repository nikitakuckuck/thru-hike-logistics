import { useHistory } from "react-router-dom";

function TrailSection({section, sectionEditMode}){
    const history = useHistory();

    const handleDetailsClick = ()=> history.push(`/sections/details/${section.trailSectionId}`)

    const handleEditClick = ()=> history.push(`/sections/edit/${section.trailSectionId}`)

    const handleDeleteClick = ()=>history.push(`/sections/delete/${section.trailSectionId}`)

    const handleSetActiveClick = ()=>{

      

        const activeSection = {...section};
            activeSection.active = true;

          console.log(activeSection);
        const init = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({...activeSection})
        };
        fetch(`http://localhost:8080/api/sections/${section.trailSectionId}`, init)
        .then(resp =>{
            switch (resp.status){
                case 204:
                    window.location.reload();
                    break;
                case 400:
                    return resp.json();
                case 404:
                    return null;
                    //add error message?
                default:
                    return Promise.reject("Something has gone wrong");
            }
        })
        // .then(body =>{
        //     if(!body){
        //     }
        // })
        .catch(err =>console.log("Error: ", err));
    }

    return(<>
    <tr className = {section.active? "active" : null}>
        <td>{section.active ? <p><strong>ACTIVE SECTION</strong></p>: <button className="btn btn-green btn-sm mr-2" onClick={handleSetActiveClick}>Set as Active</button>}</td>
        <td>{section.upcoming? "Upcoming" : "Past"}</td>
        <td>{section.trail.trailName}</td>
        <td>{section.sectionStart}</td>
        <td>{section.sectionEnd}</td>
        <td>{section.sectionLength}</td>
        <td>{section.sectionDays}</td>
        <td><button className="btn btn-blue" onClick={handleDetailsClick}>Details</button></td>
        <td>{sectionEditMode === true ? <button className="btn btn-blue btn-sm mr-2" onClick={handleEditClick}>Edit</button>
            : null}
            {sectionEditMode === true ? <button className="btn btn-red btn-sm" onClick={handleDeleteClick}>Delete</button>
             : null}
        </td>
    </tr>
    </>)
}
export default TrailSection;