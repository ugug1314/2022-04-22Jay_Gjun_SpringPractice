function updateExamNote(index, examNote) {
	var newExamNote = prompt('請輸入考試備註(examNote)資料', examNote);
	document.getElementById('_method').value = 'PUT';
	document.getElementById('examNote').value = newExamNote;
	document.getElementById('exam').action = '/springmvc/mvc/case03/exam/' + index + '/exam_note';
	document.getElementById('exam').submit();
}

function updateExam(index) {
	document.getElementById('exam').action = '/springmvc/mvc/case03/exam/' + index;
	document.getElementById('exam').submit();
}

function deleteExam(index) {
	document.getElementById('_method').value = 'DELETE';
	updateExam(index);
}

function resetExam() {
	document.getElementById('_method').value = 'POST';
	
}