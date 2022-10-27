function Error ({errors}){
    return(<>
    <div className="alert alert-red" role="alert">
        <ul>
            {errors.map(error =><li key={error}>{error}</li>)}
        </ul>
        
    </div>
    </>)
}
export default Error;