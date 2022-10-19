function Trail({trail, editMode}){
 

    return (<>
    <tr>
        <td>{trail.trailName } ({trail.trailAbbreviation})</td>
        <td>{editMode === true ? <button className="btn btn-primary btn btn-sm mr-2" >Edit</button>
         : null}
         {editMode === true ? <button className="btn btn-primary btn btn-sm">Delete</button>
         : null}
            
        </td>
    </tr>
    </>)
}
export default Trail;