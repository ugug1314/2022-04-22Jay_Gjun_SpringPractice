function updatefundstock(sid) {
	console.log(sid);
	document.getElementById('fundstock').action = '/springmvc/mvc/lab/fundstock/' + sid;
	document.getElementById('fundstock').submit();
}

function deletefundstock(sid) {
	document.getElementById('_method').value = 'DELETE';
	updatefundstock(sid);
}