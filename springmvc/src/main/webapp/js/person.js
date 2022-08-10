

function updatePerson(index) {
	document.getElementById('person').action = '/springmvc/mvc/case04/person/' + index;
	document.getElementById('person').submit();
}

function deletePerson(index) {
	document.getElementById('_method').value = 'DELETE';
	updatePerson(index);
}