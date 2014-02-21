
/** 
 * cellFormatter
 */
cellFormatter["age"] = function ( data, type, full ) {
    if(data == 1){
        return 'Male';
    }else if(data == 2){
        return 'Female';
    }else{
       return 'Unkown';
    } 
}
/** 
 * actions
 */
actions.unshift({
    "title":"Lock/Unlock",
    "action": function (thisObj){
          var tableObj = $('#'+$(thisObj).attr("for")).dataTable();
          var selectRows = tableObj.$('tr.row_selected');
          if(selectRows.length != 1){
              alert("Please select one record!");
          }else{
             var selectRowData =  tableObj.fnGetData( selectRows[0] );
             window.location.href = actionPrex + "/lock.action?id=" + selectRowData[idName];
          }
    }
})