function SectionAlert ({alert, alertDeleteMode}){

    const handleDeleteClick = ()=>{
        const init = {
            method: 'DELETE'
        };
        fetch (`http://localhost:8080/api/alerts/${alert.alertId}`, init)
        .then(resp=>{
            switch (resp.status){
                case 204:
                    //reload page?
                    break;
                case 404:
                    //todo: decide what to do here.
                    break;
                default:
                    return Promise.reject("Something has gone wrong.");
            }
        })
        .catch(err=>console.log("Error: ", err));
    }


    //considering making a field variable for alerts so user can choose whether something is a tdnk or not
    return(<>
    <tr>
         {alert.alertContent.startsWith("www.") || alert.alertContent.startsWith("http") ? <td>- {alert.alertCategory.alertCategoryName}: <a href={`${alert.alertContent}`}>{alert.alertContent}</a></td> : 
        <td>- {alert.alertCategory.alertCategoryName}: {alert.alertContent}</td>}
         <td>{alertDeleteMode === true ? <button type="button" className="btn btn-sm btn-red" onClick={handleDeleteClick}>X</button> : null }</td>
    </tr>
           
        
    </>)
}
export default SectionAlert;