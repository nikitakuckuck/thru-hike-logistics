function TrailSection({section, sectionEditMode}){
    return(<>
    <tr>
        <td>{section.upcoming? "Upcoming" : "Past"}</td>
        <td>{section.sectionStart}</td>
        <td>{section.sectionEnd}</td>
        <td>{section.sectionLength}</td>
        <td>{section.sectionDays}</td>
        <td>{sectionEditMode === true ? <button className="btn btn-blue btn btn-sm mr-2" >Edit</button>
            : null}
            {sectionEditMode === true ? <button className="btn btn-red btn btn-sm">Delete</button>
             : null}
        </td>
    </tr>
    </>)
}
export default TrailSection;