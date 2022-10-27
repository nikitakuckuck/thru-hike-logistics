import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import ExitItem from "./ExitItem";

const DEFAULT_ITEM = {exitItemId: 0, exitItemName: "", isGoodToGo: false};

function ExitItemDisplay (){
    const history = useHistory();
    const [newItem, setNewItem] = useState(DEFAULT_ITEM);
    const [items, setItems]= useState([]);

    const [incomplete, setIncomplete]= useState([]);
    const [progress, setProgress] = useState(0);

    //switched to using State for progress so progress bar would have a default of 0
    // let progress = Math.round(((items.length - incomplete.length)/items.length)*100);
    


    useEffect(()=>{
        fetch(`http://localhost:8080/api/exit-items`)
        .then(resp =>{
            switch(resp.status){
                case 200:
                    return resp.json();
                default: Promise.reject("Something has gone wrong.");
            }
        })
        .then(data=>{
            setItems(data);
        })
        .catch(err=>console.log("Error: ", err));
    },[])

    useEffect(()=>{
        fetch(`http://localhost:8080/api/exit-items/incomplete`)
        .then(resp =>{
            switch(resp.status){
                case 200:
                    return resp.json();
                default: Promise.reject("Something has gone wrong.");
            }
        })
        .then(data=>{
            setIncomplete(data);
            setProgress(Math.round(((items.length - incomplete.length)/items.length)*100));
        })
        .catch(err=>console.log("Error: ", err));
    })
    
    const handleChange = evt =>{
        const property = evt.target.name;
        const value = evt.target.value;
        const changedItem = {...newItem};
        changedItem[property]=value;
        setNewItem(changedItem);
    }

    const addNewItem= ()=>{
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({...newItem})
        };
        fetch(`http://localhost:8080/api/exit-items`,init)
        .then(resp =>{
            switch (resp.status){
                case 201:
                    window.location.reload();
                    break;
                case 400:
                    return resp.json();
                default:
                    return Promise.reject("Something has gone wrong.")
            }
        })
        .then(body =>{
            if(body.exitItemId){
                
            }else if(body){
                //TODO: set Errors
            }
        })
        .catch(err=> console.log("Error: ", err));

    }


    //Resets each item in the items list to a 'goodToGo' value of false
    const handleResetClick = ()=>{
        items.forEach(item =>{

        const itemToUpdate = {...item};
            itemToUpdate.goodToGo = false;
        
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
                default:
                    return Promise.reject("Something has gone wrong");
            }
        })
        .catch(err =>console.log("Error: ", err));
    }
        )

        }

        
  

    const handleBackToSummary = ()=> history.push("");


    return(<>
        <h2>Town Exit Checklist</h2>
        <button type="button" className="btn btn-green mr-2 mb-3" data-toggle="modal" data-target="#addWindow">
            Add New Item
        </button>
        <button onClick={handleBackToSummary} className = "btn btn-blue mr-2 mb-3">Back To Section Summary</button>
        <button onClick ={handleResetClick} className="btn btn-grey  mb-3">Reset List</button>
        {/* progress bar for checklist progress */}
        {items.length ===0? <p>Add an item to start creating your town exit checklist!</p> :
        <div className="progress mb-2">
            <div className="progress-bar" role="progressbar" style={{width: `${progress}%`}} aria-valuenow={progress} aria-valuemin="0" aria-valuemax="100">{progress}%</div>
        </div>
        }

        <table className="table table-hover">
            <thead>
            </thead>
            <tbody>
                 {items.map(item=> <ExitItem key={item.exitItemId} item = {item}/>)}
            </tbody>
        </table>
       
       
       
       {/* testing out a modal for adding items*/}

<div className="modal fade" id="addWindow"  role="dialog" aria-labelledby="addTitle" aria-hidden="true">
  <div className="modal-dialog modal-dialog-centered" role="document">
    <div className="modal-content">
      <div className="modal-header">
        <h5 className="modal-title" id="addTitle">Add a New Checklist Item</h5>
        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
            </button>
                </div>
                    <div className="modal-body">
                    <label htmlFor="exitItemName">Item Name</label>
                        <input name="exitItemName" type="text" className="form-control" id="exitItemName" value={newItem.exitItemName} onChange={handleChange}/>

                    </div>
                <div className="modal-footer">
            <button type="button" className="btn btn-blue" data-dismiss="modal">Close Without Saving</button>
        <button type="button" className="btn btn-green" data-dismiss="modal" onClick={addNewItem}>Add Item</button>
      </div>
    </div>
  </div>
</div>
    </>)
}
export default ExitItemDisplay;