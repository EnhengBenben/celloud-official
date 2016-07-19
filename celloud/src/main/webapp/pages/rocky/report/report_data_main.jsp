<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="content">
	<div class="report-content">
		<section>
			<h4>检测结果：</h4>
			<div class="result-div">
				<p class="main">
					${rocky.records.size()<=0?'未':'' }发现<strong>致病相关</strong>突变！
				</p>
				<p class="notice">注： 本报告中&nbsp;“致病相关”&nbsp;是指本系统采集到的已知研究结论中，受检人样本中存在的突变与疾病有相关性。</p>
			</div>
		</section>
		<c:if test="${rocky.records.size()>0}">
			<section>
				<h4>结果说明：</h4>
				<table class="table table-main -report">
					<thead>
						<tr>
							<th>基因</th>
							<th>突变</th>
							<th>说明</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${rocky.records }" var="record">
							<tr>
								<td>
									<strong>${record.gene }</strong>
								</td>
								<td>
									突变位置:${record.chromosome }:${record.position }<br> 碱基变化${record.bases }<br> 氨基酸变化:${record.acids }
								</td>
								<td>${record.description }根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......根据......</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<p class="notice">1.根据......</p>
				<p class="notice">1.根据......</p>
				<p class="notice">1.根据......</p>
				<p class="notice">1.根据......</p>
			</section>
		</c:if>

	</div>
</div>