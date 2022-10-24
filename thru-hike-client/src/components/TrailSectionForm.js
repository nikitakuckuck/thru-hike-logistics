import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import Error from "./Error";

const DEFAULT_SECTION = {trailSectionId: 0, trailId: 0, sectionStart: "", sectionEnd: "", latitude: 0, longitude: 0, sectionLength: 0,sectionDays: 0, upcoming: true};

function TrailSectionForm (){
const [trails, setTrails]=useState([]);
const [section, setSection]=useState(DEFAULT_SECTION);
const history = useHistory();
const [errors, setErrors] = useState([]);
const {editSectionId} = useParams();

    useEffect(()=>{
        if(editSectionId){
            fetch(`http://localhost:8080/api/section/${editSectionId}`)
            .then(resp=>{
                switch(resp.status){
                    case 200:
                        return resp.json();
                    case 404:
                        return null;
                        //add routing to not found
                    default:
                        return Promise.reject("Something has gone wrong.");
                }
            })
            .then(body =>{
                if(body.trailSectionId){
                    setSection(body);
                }
            })
            .catch(err=>console.log("Error: ", err));
        }
    },[editSectionId]);

    const saveSection = ()=>{
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({...section})
        };
        fetch('http://localhost:8080/api/section', init)
        .then(resp =>{
            switch(resp.status){
                case 201:
                case 400:
                    return resp.json();
                default:
                    return Promise.reject("Something has gone wrong.");
            }
        })
        .then(body =>{
            if(body.trailSectionId){
                history.push('/sections');
            } else if(body){
                setErrors(body);
            }
        })
        .catch(err => console.log("Error: ", err));
    }


    useEffect(()=>{
        fetch('http://localhost:8080/api/trail')
        .then(resp=>{
            switch(resp.status){
                case 200:
                    return resp.json();
                    default:
                        return Promise.reject("Something has gone wrong.");
            }
        })
        .then(body =>{
            if(body){
                setTrails(body);
            }
        })
        .catch(err=>console.log("Error: ", err));
    })

    const updateSection = ()=>{
        const init = {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(section)
        };
        fetch(`http://localhost:8080/api/section/${editSectionId}`,init)
        .then(resp =>{
            switch(resp.status){
                case 204:
                    return null;
                case 400:
                    return resp.json();
                case 404:
                    return null;
                    //add routing to not found pg
                default:
                    return Promise.reject("Something has gone wrong");
            }
        })
        .then(body =>{
            if(!body){
                history.push('/sections');
            } else {
                setErrors(body);
            }
        })
        .catch(err => console.log("Error: ", err));
    }


    const onSubmit= evt =>{
        evt.preventDefault();
        const functionChoice = editSectionId > 0 ? updateSection : saveSection;
        functionChoice();
    }

    const handleChange = evt =>{
        const property = evt.target.name;
        const valueType = evt.target.type === "checkbox" ? "checked" : "value";
        const value = evt.target[valueType];

        const newSection = {...section};
        newSection[property] = value;

        setSection(newSection);
    }

    const handleCancel = ()=>history.push('/sections');

    return(<>
    <h2>{editSectionId ? "Edit" : "Add"} a Section:</h2>
    {errors.length >0 ? <Error errors = {errors}/> : null}
    <form onSubmit={onSubmit}>
        <div className="form-group">
            <label htmlFor="trailId">Trail</label>
            <select name="trailId" className ="form-control" id="trailId" value={section.trailId} onChange={handleChange}>
                <option>Please select a trail:</option>
                {trails.map(trail =><option key={trail.trailId} value={trail.trailId}>{trail.trailAbbreviation === ""? `${trail.trailName}`: `${trail.trailName} (${trail.trailAbbreviation})`}</option>)}
            </select>
        </div>
        <div className="form-group">
            <label htmlFor="sectionStart">Starting Town Name</label>
            <input name="sectionStart" type="text" className="form-control" id="sectionStart" value={section.sectionStart} onChange={handleChange}/>
        </div>
        <div className="form-group">
            <label htmlFor="sectionEnd">Ending Town Name</label>
            <input name="sectionEnd" type="text" className="form-control" id="sectionEnd" value={section.sectionEnd} onChange={handleChange}/>
        </div>
        <div className="form-group">
            <label htmlFor="sectionDays">Total Days</label>
            <input name="sectionDays" type="number" className="form-control" id="sectionDays" value={section.sectionDays} onChange={handleChange}/>
        </div>
        <div className="form-group">
            <label htmlFor="sectionLength">Section Length (miles)</label>
            <input name="sectionLength" type="number" className="form-control" id="sectionLength" value={section.sectionLength} onChange={handleChange}/>
        </div>
        <div className="form-group ml-4">
            <input type="checkbox" id="upcoming" name="upcoming" className="form-check-input" checked={section.upcoming} onChange={handleChange}/>
            <label htmlFor="upcoming">Upcoming? (this box should only be unchecked if you have already passed through this section)</label>
        </div>
        <div className="form group">
            <button type="submit" className="btn btn-green mr-2">Submit</button>
            <button type="button" className="btn btn-blue" onClick={handleCancel}>Cancel</button>
        </div>

    </form>
    </>)
}
export default TrailSectionForm;