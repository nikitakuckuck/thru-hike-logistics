function SectionAlert ({alert}){
    return(<>
        <li>
            {alert.alertCategory.alertCategoryName}: {alert.alertContent}
        </li>
    </>)
}
export default SectionAlert;