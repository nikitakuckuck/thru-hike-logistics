import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import SectionAlerts from "./SectionAlerts";

const DEFAULT_ITEM = {exitItemId: 0, exitItemName: "", isGoodToGo: false};

function ExitItemDisplay ({allComplete}){
    const history = useHistory();
    const [newItem, setNewItem] = useState(DEFAULT_ITEM);
    const [items, setItems]= useState([]);
    //todo: change progress const to number of checked boxes.
    const progress = 30.56;

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
    
    const handleChange = evt =>{
        const property = evt.target.name;
        const valueType = evt.target.type=== "checkbox" ? "checked" : "value";
        const value = evt.target[valueType];
        const changedItem = {...newItem};
        changedItem[property]=value;
        setNewItem(changedItem);

        let checklistBoxes = document.getElementById("exitChecklist").getElementsByTagName("input");
        let boxCount = 0;
    for(let i = 0; i<checklistBoxes.length; i++){
        if(!checklistBoxes[i].checked){
            allComplete=false;
        } else{
            boxCount++;
            // change color of label to grey out?
        }
    }
    if(boxCount===checklistBoxes.length){
        allComplete=true;
    }

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
  

    const handleBackToSummary = ()=> history.push("");


    return(<>
        <h2>Town Exit Checklist</h2>
        <button type="button" className="btn btn-green mr-2 mb-3" data-toggle="modal" data-target="#addWindow">
            Add New Item
        </button>
        <button onClick={handleBackToSummary} className = "btn btn-blue mb-3">Back To Section Summary</button>

        {/* progress bar for checklist progress - possibly move to home page?*/}
        <div className="progress mb-2">
            <div className="progress-bar" role="progressbar" style={{width: `${progress}%`}} aria-valuenow={progress} aria-valuemin="0" aria-valuemax="100">{progress}%</div>
        </div>
        

        <form id="exitChecklist">
            {items.map(item => 
                <div key = {item.exitItemId} className="form-group ml-4">
                <input type="checkbox" id={item.exitItemId} name="checkAlerts"  className="form-check-input" onChange={handleChange}/>
                <label htmlFor="checkAlerts">{item.exitItemName}</label>
            </div>
            )}
        </form>
       
       
       
       {/* testing out a modal for adding items. still need to exit modal after successful add*/}

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
        <button type="button" className="btn btn-green" onClick={addNewItem}>Add Item</button>
      </div>
    </div>
  </div>
</div>
    </>)
}
export default ExitItemDisplay;