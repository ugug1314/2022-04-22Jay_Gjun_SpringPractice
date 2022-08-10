function ApearNumCount(){
var request=new XMLHttpRequest();
var result=document.getElementById("ApearNumCountRul");
var text = '<table id="Numcount" class="pure-table pure-table-bordered "><tr>';

var count=1;
request.onreadystatechange = function() {
	if (this.readyState == XMLHttpRequest.DONE && this.status == 200) {
		 var obj =JSON.parse(this.responseText);
		 //取得物件長度
		 var lengthJason=getJsonObjLength(obj);
		 //轉換物件為js的map
		 var arrayObj=new Map(Object.entries(obj))
		 
		 //Symbol.iterator symbol specifies the default iterator for an object
		 //透過修改iterator預設的排序方式，來依map的值排序
		 arrayObj[Symbol.iterator] = function* () {
		     yield* [...this.entries()].sort((a, b) => b[1] - a[1]);
		 }


		 
		 if(lengthJason<5){
		       for(var j=0;j<lengthJason;j++)
			    	 text+="<th>號碼</th><th>次數</th></tr>"
		    }
		    else{
		      for(var j=1;j<6;j++)
			    	 text+="<th>號碼</th><th>次數</th>"
		    }
		     text+="</tr><tr>"
		 for(var [key,value] of arrayObj){
			 if((count%5)==0){
			      text+="<td>"+key+"</td><td>"+value+"</td></tr><tr>";
			      count+=1;
			    }
			    else{
			      text+="<td>"+key+"</td><td>"+value+"</td>";
			      count+=1;
			    }
			   }
			    text += "</tr></table>";
			    result.innerHTML=text; 
			    console.log(obj);
			    drawChart(3);
			 
		   }
	}  
request.open("GET","countNum",true);
request.setRequestHeader("content-type","application/json");
request.send();


}
//取得jason的長度
function getJsonObjLength(jsonObj) {
	 var Length = 0; 
	 for (var item in jsonObj) {
		 Length++;
	} 
	 return Length;
}