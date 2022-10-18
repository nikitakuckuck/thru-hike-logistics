function Trail({trail}){

    return (<>
    <tr>
        <td>{trail.trailName } ({trail.trailAbbreviation})</td>
        <td>
            <button className="btn btn-primary btn btn-sm">Edit/Delete</button>
        </td>
    </tr>
    </>)
}
export default Trail;