<%@ page isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spform"
	uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet"
		href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
	<meta charset="UTF-8">
	<title>Fundstock Form</title>
	<style type="text/css">
	.error {
		color: #FF0000
	}
	</style>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="${ pageContext.request.contextPath }/js/util.js"></script>
	<script src="${ pageContext.request.contextPath }/js/fundstock.js"></script>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);
      
	  function drawChart() {
	  // 將要產生得資料放置於此，格式是map。
		  drawChart(1);				//呼叫方法畫長條、圓餅、折線圖等。
		  drawStockChart('^TWII');  //呼叫方法畫股票走勢圖
	  }
	  //畫股票走勢圖實作
	  function drawStockChart(symbol) {
	  console.log(symbol);
		  $.get("${ pageContext.request.contextPath }/mvc/lab/price/histquotes/" + symbol, 
		  function(quotes, status) {
			  console.log("quotes:" + quotes);
			  console.log("status:" + status);
			  drawChartHist(symbol, quotes);
		  });
	  }
	  
	  function drawChartHist(symbol, quotes) {
		  // 建立 data 欄位
		  var data =  new google.visualization.DataTable();
		  // 定義欄位
		  data.addColumn('string', 'Date');
		  data.addColumn('number', 'High');
		  data.addColumn('number', 'Open');
		  data.addColumn('number', 'Close');
		  data.addColumn('number', 'Low');
		  data.addColumn('number', 'AdjClose');
		  data.addColumn('number', 'Volumn');
		  // 加入資料
		  $.each(quotes, function (i, item) {
              var array = [getMD(quotes[i].date), quotes[i].high, quotes[i].open, quotes[i].close, quotes[i].low, quotes[i].adjClose, quotes[i].volume];
              data.addRow(array);
          });
		  console.log("data:" + data);
		  // 設定 chart 參數
		  var options = {
			title: symbol + ' 日K線圖',
            legend: 'none',
            vAxes: [
            	{},
                {minValue: 1, maxValue: 6000000}  //量的區間
            ],
            series: {
            	1: {targetAxisIndex: 0, type: 'line', color: '#e7711b'},
            	2: {targetAxisIndex: 1, type: 'bars', color: '#cccccc'}
            },
            	candlestick: {
                	fallingColor: {strokeWidth: 0, fill: '#0f9d58'}, // green
                    risingColor: {strokeWidth: 0, fill: '#a52714'}   // red
             },
             chartArea: {left: 50}
         };
		  // 產生 chart 物件
		  var chart = new google.visualization.CandlestickChart(document.getElementById('stockchart'));
		  // 繪圖
		  chart.draw(data, options);
	  }
	  
      function drawChart(chartId) {

        var data = google.visualization.arrayToDataTable([
        //欄位名稱
          ['symbol', 'share'],
        //資料值
          <c:forEach var="map" items="${ groupMap }">
			['${ map.key }', ${ map.value }],
		  </c:forEach>
        ]);
  
        var options = {
        //設定圖表標題
          title: 'stock info'
         //設定3D圖
         // is3D:'true'
        };
         //預設畫bar chart(橫條圖)
        var chart = new google.visualization.BarChart(document.getElementById('piechart'));
        switch(chartId) {
         //畫圓餅圖
        	case 2:
        		chart = new google.visualization.PieChart(document.getElementById('piechart'));
        		break;
        //畫長條圖	
        	case 3:
        		chart = new google.visualization.ColumnChart(document.getElementById('piechart'));
        		break;
        //畫折線圖
        	case 4:
        		chart = new google.visualization.LineChart(document.getElementById('piechart'));
        		break;	
        }
        //執行畫圖
        chart.draw(data, options);
      }
	</script>
</head>
<body style="padding: 15px">
	<table>
		<tr>
			<!-- Fundstock Form -->
			<td valign="top">
				<spform:form class="pure-form" method="post"
					modelAttribute="fundstock"
					action="${ pageContext.request.contextPath }/mvc/lab/fundstock/addFundstock">
					<fieldset>
						<legend>
							Fundstock Form | 
							<a href="${ pageContext.request.contextPath }/html/fund.html">Fund Form（Ajax）</a>
						</legend>
						<input type="hidden" id="_method" name="_method" value="${ _method }">
						 序號：
						<spform:input path="sid" />
						<spform:errors path="sid" cssClass="error" />
						<p />
						代號：
						<spform:input path="symbol" required="true"/>
						<spform:errors path="symbol" cssClass="error" />
						<p />
						數量：
						<spform:input path="share" required="true"/>
						<spform:errors path="share" cssClass="error" />
						<p />
						基金：
						<spform:select path="fid">
							<spform:option value="">請選擇</spform:option>
							<spform:options items="${ funds }" itemValue="fid" itemLabel="fname" />
						</spform:select>
						<p />
						<button type="submit" class="pure-button pure-button-primary"
							${ _method=='POST'?'':'disabled' }>新增</button>
						<button type="submit" class="pure-button pure-button-primary"
							${ _method=='PUT'?'':'disabled' } onclick="updatefundstock($('#sid').val())">修改</button>
							<button type="submit" class="pure-button pure-button-primary"
							${ _method=='PUT'?'':'disabled' } onclick="deletefundstock($('#sid').val())">刪除</button>
						<p />
						<spform:errors path="*" cssClass="error" />
					</fieldset>
				</spform:form>
			</td>
			<!-- Fundstock List -->
			<td valign="top">
				<form class="pure-form">
					<fieldset>
						<legend>
							Fundstock List&nbsp;|&nbsp;
							<a href="${ pageContext.request.contextPath }/mvc/lab/fundstock/page/0">全部</a>
							&nbsp;|&nbsp;
							<c:forEach var="num" begin="1" end="${ pageTotalCount }">
								<a href="${ pageContext.request.contextPath }/mvc/lab/fundstock/page/${ num }">${ num }</a>
							</c:forEach>
						</legend>
						<table class="pure-table pure-table-bordered">
							<thead>
								<tr>
									<th>序號</th>
									<th>代號</th>
									<th>數量</th>
									<th>基金</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="fundstock" items="${ fundstocks }">
									<tr>
										<td><a
											href="${ pageContext.request.contextPath }/mvc/lab/fundstock/${ fundstock.sid }">${ fundstock.sid }</a></td>
										<td>
											<a href="#" onclick="drawStockChart('${ fundstock.symbol }')">${ fundstock.symbol }</a>
										</td>
										<td>${ fundstock.share }</td>
										<td>${ fundstock.fund.fname }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</fieldset>
				</form>
			</td>
			<!-- Fundstock chart -->
			<td valign="top">
				<form class="pure-form">
					<fieldset>
						<legend>
							Fundstock Chart |
							<!-- 產生連結建立各種圖表 -->
							<a href="#" onclick="drawChart(1)">bar</a> |
							<a href="#" onclick="drawChart(2)">pie</a> |
							<a href="#" onclick="drawChart(3)">column</a> |
							<a href="#" onclick="drawChart(4)">line</a>
						</legend>
						<div id="piechart" style="width: 500px; height: 300px;"></div>
					</fieldset>
				</form>
			</td>
		</tr>
		<tr>
			<td colspan="3" valign="top">
				<form class="pure-form">
					<fieldset>
						<legend>
							Fundstock Chart | <a href="#" onclick="drawStockChart('^TWII')">加權股價</a>
						</legend>
						<div id="stockchart" style="width: 1500px; height: 500px;"></div>
					</fieldset>
				</form>
			</td>
		</tr>
	</table>




</body>
</html>