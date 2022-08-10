<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
      <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
         <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
               <title>Show Lotto</title>
               <link rel="stylesheet" href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
               <link rel="stylesheet" href="${ pageContext.request.contextPath }/css/lotto.css">
               <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
               <script type="text/javascript" src="${ pageContext.request.contextPath }/js/loto.js" defer></script>
               <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>       
               <script>
      google.charts.load('current', {'packages':['corechart']});
      //google.charts.setOnLoadCallback(drawChart);
      
	  function drawChart() {
	  // 將要產生得資料放置於此，格式是map。
		  drawChartgo(3);				//呼叫方法畫長條、圓餅、折線圖等。
	  } 
	  
      function drawChart(chartId) {

        var data = google.visualization.arrayToDataTable([
        //欄位名稱
          ['numbers', 'count'],
        //資料值
          <c:forEach var="map" items="${ countNumRlt }">
			['${ map.key }', ${ map.value }],
		  </c:forEach>
        ]);
        var options = {
        //設定圖表標題
          title: 'lottoselect info'
         //設定3D圖
         // is3D:'true'
        };
         //預設畫bar chart(橫條圖)
        var chart = new google.visualization.BarChart(document.getElementById('columnchart'));
        switch(chartId) {
         //畫圓餅圖
        	case 2:
        		chart = new google.visualization.PieChart(document.getElementById('columnchart'));
        		break;
        //畫長條圖	
        	case 3:
        		chart = new google.visualization.ColumnChart(document.getElementById('columnchart'));
        		break;
        //畫折線圖
        	case 4:
        		chart = new google.visualization.LineChart(document.getElementById('columnchart'));
        		break;	
        }
        //執行畫圖
        chart.draw(data, options);
      }
	</script>
            </head>

            <body style="padding: 15px" onload="ApearNumCount()">
               <table style="border-spacing:20px;">
                  <tr>
                     <td valign="top">
                        <form class="pure-form" method="post" action="./add">
                           <fieldset>
                              <button type="submit" class="pure-button pure-button-primary">
                                 電腦選號</button>
                           </fieldset>
                        </form>

                        <p />
                        
                        <form class="pure-form" method="post" action="./">
                           <fieldset>
                              <button type="button" onclick="ApearNumCount()"
                                 class="pure-button pure-button-primary">統計每一號碼出現的次數</button>
                           </fieldset>
                           格式：號碼：(出現次數) <br /> 順序：按照出現次數由大到小依序排列 <br />
                           範例：9:(7)、18:(5)、31:(3)、12:(1) ...

                           <div id="ApearNumCountRul"></div>
                        </form>

                        <p />
                        <table class="pure-table pure-table-bordered">
                           <thead>
                              <tr>
                                 <th>index</th>
                                 <th>樂透號碼</th>
                                 <th>修改</th>
                                 <th>刪除</th>
                              </tr>
                           </thead>
                           <tbody>
                              <c:forEach varStatus="status" var="lotto" items="${ lottos }">
                                 <tr>
                                    <td>${ status.index }</td>
                                    <td>${ lotto }</td>
                                    <td>
                                       <button type="botton" onclick="window.location.href='./update/${status.index}';"
                                          class="pure-button pure-button-primary">修改</button>
                                    </td>
                                    <td>
                                       <button type="botton" onclick="window.location.href='./delete/${status.index}';"
                                          class="pure-button pure-button-primary">刪除</button>
                                    </td>
                                 </tr>
                              </c:forEach>
                           </tbody>
                        </table>
                     </td>
                     <td valign="top">
						<form class="pure-form">
							<fieldset>
								<legend>
									 Chart |
								<!-- 產生連結建立各種圖表 -->
								<a href="#" onclick="drawChart(3)">column</a> |
								
						</legend>
						<div id="columnchart" style="width: 1200px; height: 400px;"></div>
					</fieldset>
				</form>
			</td>
                  </tr>
               </table>
               
               <a href="http://localhost:8080/springmvc/">回首頁</a>
            </body>
           
            </html>