function SectionAlert ({alert}){

    //considering making a field variable for alerts so user can choose whether something is a link or not
    return(<>
        {alert.alertContent.startsWith("www.") || alert.alertContent.startsWith("http") ? <li>{alert.alertCategory.alertCategoryName}: <a href={`${alert.alertContent}`}>{alert.alertContent}</a></li> : 
        <li>{alert.alertCategory.alertCategoryName}: {alert.alertContent}</li>}
             
        
    </>)
}
export default SectionAlert;