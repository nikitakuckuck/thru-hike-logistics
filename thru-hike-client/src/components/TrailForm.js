import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import Error from "./Error";

const DEFAULT_TRAIL = {trailId: 0, trailName: '', trailAbbreviation: ''};

function TrailForm (){
    const [trail, setTrail]= useState(DEFAULT_TRAIL);
    const {editTrailId} = useParams();
    const [errors, setErrors] = useState([]);
    const history = useHistory();

    useEffect(()=>{
        if(editTrailId){
            fetch(`http://localhost:8080/api/trails/${editTrailId}`)
            .then(resp=>{
                switch(resp.status){
                    case 200:
                        return resp.json();
                    case 404:
                        return null;
                        //add routing to not found
                    default: return Promise.reject("Something has gone wrong.");
                }
            })
            .then(body =>{
                if(body.trailId){
                    setTrail(body);
                }
            })
            .catch(err=>console.log("Error: ", err));
        }
    }, [editTrailId]);

    const saveTrail = ()=>{
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({...trail})
        };
        fetch('http://localhost:8080/api/trails', init)
        .then(resp =>{
            switch(resp.status){
                case 201:
                case 400:
                    return resp.json();
                default:
                    return Promise.reject("Something has gone wrong");

            }
            
        })
        .then(body=>{
            if(body.trailId){
                history.push('/trails');
            } else if(body){
                setErrors(body);
            }
        })
        .catch(err => console.log("Error: ", err));
    }

    const updateTrail = ()=>{

        const init = {
            method: 'PUT',
            headers: {'Content-Type':'application/json'},
            body: JSON.stringify(trail)
        };
        fetch(`http://localhost:8080/api/trails/${editTrailId}`,init)
        .then(resp =>{
            switch(resp.status){
                case 204:
                    return null;
                case 400:
                    return resp.json();
                case 404:
                    return null;
                    //add routing to not found
                default: 
                    return Promise.reject("Something has gone wrong");
            }
            
        })
        .then(body=>{
            if(!body){
                history.push('/trails');
            } else if(body){
                setErrors(body);
            }
        })
        .catch(err => console.log("Error: ", err));
    }


    const handleCancel=()=> history.push('/trails');

    const onSubmit = evt => {
        evt.preventDefault();
        const functionChoice = editTrailId > 0 ? updateTrail : saveTrail;
        functionChoice();
    }

    const handleChange = evt =>{
        const property = evt.target.name;
        const value = evt.target.value;

        const newTrail = {...trail};
        newTrail[property] = value;

        setTrail(newTrail);
    }

    return(<>
    <h2>{editTrailId ? 'Edit' : 'Add'} a trail:</h2>
    {errors.length > 0 ? <Error errors = {errors}/> : null}
    <form onSubmit={onSubmit}>
        <div className="form-group">
            <label htmlFor="trailName">Trail Name</label>
            <input name="trailName" type="text" className="form-control" id = "trailName" value={trail.trailName} onChange={handleChange}/>
        </div>
        <div className="form-group">
            <label htmlFor="trailAbbreviation">Trail Abbreviation or Nickname</label>
            <input name="trailAbbreviation" type="text" className="form-control" id="trailAbbreviation" value={trail.trailAbbreviation} onChange={handleChange}/>
        </div>
        <div className="form-group">
            <button type="submit" className="btn btn-green mr-2">Submit</button>
            <button type="button" className="btn btn-blue" onClick={handleCancel}>Cancel</button>
        </div>
    </form>
    </>)
}
export default TrailForm;