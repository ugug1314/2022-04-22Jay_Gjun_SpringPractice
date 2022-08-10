
	// 頁面載入完成後要執行的程序
	$(document).ready(function() {
		// 驗證註冊
		$('#myForm').validate({
			onsubmit: false,  //表示所有的按鈕都要自行控制
			onkeyup: false,   //在輸入字的時候為要檢查
			rules: {
				fname: { // fname 指的是 input 輸入框標籤的 name 的值
					required: true,        //該欄位必須輸入
					rangelength: [2, 50]   //資料長度要在2~50個字
				}
			},
			messages: { // 自訂錯誤訊息
				fname: { 
					required: "請輸入基金名稱",
					rangelength: "基金名稱長度必須介於{0}~{1}之間"
				}
			}
		});
		//取得分頁總數並生成分頁連結
		//先清除原有分頁資料
		$('#pageScope > span').remove();
		//先放查詢所有資料的span
		$('#pageScope').append('<span class="mylink" onclick="queryPage(0);">全部</span>&nbsp;|&nbsp;');
		let path='../mvc/lab/fund/totalPagecount/'
		// 發送請求取得總頁數
		$.get(path,function(datas,status){
			console.log(datas);
		  for(var i=1;i<=datas;i++){
			 $('#pageScope').append(String.format('<span class="mylink" onclick="queryPage({0})">{1}</span>&nbsp;',i,i));
		  }
		});
		
		// Fund List 的資料列表
		table_list();
		// 註冊相關事件
		$('#add').on('click', function() {
			addOrUpdate('POST');
		});
		//在點選每列時要將該列欄位上資料丟至表單的輸入框中
		$('#myTable').on('click', 'tr', function() {
			getItem(this);
		});
		//更新的按鈕
		$('#upt').on('click', function() {
			addOrUpdate('PUT');
		});
		//刪除按鈕
		$('#del').on('click', function() {
			deleteItem();
		});
		//重置的按鈕
		$('#rst').on('click', function() {
			btnAttr(0);
		});
	});
	
	// 分頁查詢
	function queryPage(pageNumber) {
		var path = "../mvc/lab/fund/";
		if(pageNumber > 0) {
			path = "../mvc/lab/fund/page/" + pageNumber;
		}
		// 取得所有 fund 資料
		$.get(path, function(datas, status) {
			//console.log(datas);
			//console.log(status);  //顯示是否成功
			// 清除目前 myTable中tr的舊有資料
			$('#myTable tbody > tr').remove();
			// 將資料 datas 依序放入 myTable 中，i是指目前在哪一筆資料，item代表那一筆資料
			$.each(datas, function(i, item){
				var html = '<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td></tr>';
				var fundStockInfo="";
				if(item.fundstocks!=null){
				for(var id=0;id<=item.fundstocks.length-1;id++){
					fundStockInfo=fundStockInfo+item.fundstocks[id].symbol+"、";
				  }
				}
				console.log(fundStockInfo);
				$('#myTable').append(String.format(html, item.fid, item.fname, item.createtime,fundStockInfo.slice(0,fundStockInfo.length-1)));
			});
		});
	}
	
	function getItem(elem) {
		//elem是呼叫時傳入的該列元素集，在該列找到第1個td, 並取得該td的text值，而eq()會依指定的index找到該元素集中的某一個元素。
		var fid = $(elem).find('td').eq(0).text().trim();
		console.log(fid);
		var path = '../mvc/lab/fund/' + fid;
		var func = function(fund, status) {
			console.log(fund);
			// 將資料配置到 myForm 表單中
			$('#myForm').find('#fid').val(fund.fid);
			$('#myForm').find('#fname').val(fund.fname);
			// 修改 btn 狀態，讓按鈕只顯示修改、刪除，將新增disabled掉。
			btnAttr(1);
			// 該筆資料是否能刪除，取決於 fund 物件下面是否有 fundstock 陣列物件
			console.log(fund.fundstocks.length);
			if(fund.fundstocks.length > 0) {
				$('#myForm').find('#del').attr('disabled', true);
			}
		};
		//塞入請求路徑，及要執行的function。
		$.get(path, func);
	}
	
	function addOrUpdate(method) {
		// 驗證#myForm
		console.log($('#myForm').valid());
		if(!$('#myForm').valid()) { // 判斷是否表單驗證成功？
			return;                 // 沒有驗證成功就直接返回，取消動作
		}
		// 將表單欄位資料json物件序列化
		var jsonObject = $('#myForm').serializeObject();
		// 將json物件轉為json字串
		var jsonString = JSON.stringify(jsonObject);
		console.log(jsonString);
		// 將資料傳遞到後端
		$.ajax({
			url: "../mvc/lab/fund/",
			type: method,
			contentType: 'application/json;charset=utf-8',
			data: jsonString,
			success: function(respData) {
				console.log(respData);
				// 列表資料更新
				table_list();
				// rst更新完後讓按鈕回到重置的狀態
				btnAttr(0);
				// form reset清空表單資料
				$('#myForm').trigger('reset');
			}
		});
	}
	
	function deleteItem() {
		//取得現所點選的fid的資料
		var fid = $('#myForm').find('#fid').val();
		$.ajax({
			url: '../mvc/lab/fund/' + fid,
			type: 'DELETE',
			contentTyep: 'application/json;charset=utf-8',
			success: function(respData) {
				console.log(respData);
				// 列表資料更新
				table_list();
				// rst 刪除完後讓按鈕回到重置的狀態
				btnAttr(0);
				// form reset
				$('#myForm').trigger('reset');
			},
			//如果有錯誤的時候，回傳錯誤訊息來看
			error: function(http, textStatus, errorThrown) {
				console.log("http:" + http);
				console.log("textStatus:" + textStatus);
				console.log("errorThrown:" + errorThrown);
				var errorInfoText = JSON.stringify(http)
				console.log(errorInfoText.includes('REFERENCES'));
				if(errorInfoText.includes('REFERENCES')) {
					alert('該筆資料無法刪除，原因：因為此基金下有成分股的參照');
				} else {
					alert('該筆資料無法刪除，原因：' + textStatus);
				}
			}
		});
	}
	
	// Fund List 的資料列表
	function table_list() {
		
		queryPage(0);
	}
	//設定btn的狀態
	function btnAttr(status) {
		$('#myForm').find('#add').attr('disabled', status != 0);
		$('#myForm').find('#upt').attr('disabled', status == 0);
		$('#myForm').find('#del').attr('disabled', status == 0);
	}
	
