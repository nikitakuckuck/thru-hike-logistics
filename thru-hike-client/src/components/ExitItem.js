import { useHistory } from "react-router-dom";

function ExitItem({item}){
    const history = useHistory();

    const handleUpdateClick = ()=>{

        const itemToUpdate = {...item};
        if(item.goodToGo){
            itemToUpdate.goodToGo = false;
        } else {itemToUpdate.goodToGo = true;}
        
        const init = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({...itemToUpdate})
        };
        fetch(`http://localhost:8080/api/exit-items/${item.exitItemId}`, init)
        .then(resp =>{
            switch (resp.status){
                case 204:
                    window.location.reload();
                    break;
                case 400:
                    return resp.json();
                case 404:
                    return null;
                    //add routing to not found
                default:
                    return Promise.reject("Something has gone wrong");
            }
        })
        // .then(body =>{
        //     if(!body){
        //         history.push('/exit-checklist');
        //     }
        // })
        .catch(err =>console.log("Error: ", err));
    }

    const handleDeleteClick = ()=> {
        const init = {
            method: 'DELETE'
        };
        fetch (`http://localhost:8080/api/exit-items/${item.exitItemId}`, init)
        .then(resp =>{
            switch (resp.status){
                case 204:
                    window.location.reload();
                    break;
                case 404:
                    //handle somehow?
                    break;
                default:
                    return Promise.reject("Something has gone wrong");
            }
        })
        .catch(err =>console.log("Error: ", err));
    }

    return(<>
    <tr>
        <td><button type="button" className="btn btn-block btn-checklist" onClick={handleUpdateClick}>{item.goodToGo ? <s>{item.exitItemName}</s> : item.exitItemName }</button></td>
        <td><button type="button" className="btn btn-sm btn-red" onClick={handleDeleteClick}>X</button></td>
    
    </tr>
    </>)
}
export default ExitItem;