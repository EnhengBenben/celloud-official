<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<br />
<h4 class="share" style="padding-left:30px;margin:10px 5px;height:25px;">共享给我的数据</h4>
<br />
<input type="hidden" id="sessionUserNameHidden" value='<s:property value="#session.username"/>'/>
<table class="table">
	<thead>
    	<tr>
        	<th>文件名称 <a href="javascript:ssortByFileName();"><img id="sFileNameSortType" src="<%=request.getContextPath()%>/images/publicIcon/descending.png"/></a></th>
        	<th>数据编号</th>
        	<th>文件别名</th>
        	<th>上传时间<a href="javascript:ssortByCreateDate();"><img id="sCreateDateSortType" src="<%=request.getContextPath()%>/images/publicIcon/descending_b.png"/></a></th>
        	<th>所有人</th>
        	<th>操作</th>
        </tr>
    </thead>
	<tbody>
		<s:if test="%{sharedDataPageList.datas.size()>0}">
			<s:iterator value="sharedDataPageList.datas" status="st" id="data">
				<tr>
					<td title="<s:property value="#data.fileName"/>">
						<s:if test="#data.fileName.length()>40">
							<s:property value="#data.fileName.substring(0,40)"/>...
						</s:if>
						<s:else>
							<s:property value="#data.fileName" />
						</s:else>
					</td>
					<td class="center"><s:property value="#data.dataKey"/></td>
					<td class="center"><s:property value="#data.anotherName"/></td>
					<td class="center"><s:date name="#data.createDate" format="yyyy/MM/dd" /></td>
					<td class="center"><s:property value="#data.owner"/></td>
        			<td class="center">
        				<a href="javascript:showSharedDataMoreInfoModal('<s:property value="#data.strain"/>','<s:property value="#data.dataTags"/>');"><img  title="更多" alt="更多" class="more" src="<%=request.getContextPath()%>/images/publicIcon/more.png"/></a>
        			</td>
				</tr>
			</s:iterator>
		</s:if>
		<s:else>
			<tr><td colspan="6">共享数据记录为空</td></tr>
		</s:else>
   	</tbody>
	<tfoot>
   	</tfoot>
</table>
<div class="pagination-new center">
	<s:if test="%{sharedDataPageList.datas.size()>0}">
		<ul class="pages">
	    	<!-- 显示prev -->
	        <s:if test="%{sharedDataPageList.page.hasPrev}">
				<li><a href="javascript:searchSharedData('<s:property value="sharedDataPageList.page.currentPage-1" />')">&lt;</a></li>
			</s:if>
			<!-- 显示第一页 -->
			<s:if test="%{sharedDataPageList.page.currentPage==1}">
				<li class="active"><a href="#">1</a></li>
			</s:if>
			<s:else>
				<li><a href="javascript:searchSharedData(1)">1</a></li>
			</s:else>
			
			<s:if test="%{sharedDataPageList.page.currentPage>4&&sharedDataPageList.page.totalPage>10}">
				<li>...</li>
			</s:if>
			
			<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>=7}">
				<s:if test="%{sharedDataPageList.page.currentPage==3}">
					<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage-1"/>)"><s:property value="sharedDataPageList.page.currentPage-1"/></a></li>
				</s:if>
				<s:if test="%{sharedDataPageList.page.currentPage==4}">
					<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage-2"/>)"><s:property value="sharedDataPageList.page.currentPage-2"/></a></li>
				</s:if>
				<s:if test="%{sharedDataPageList.page.currentPage>3}">
					<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage-1"/>)"><s:property value="sharedDataPageList.page.currentPage-1"/></a></li>
				</s:if>
				<s:if test="%{sharedDataPageList.page.currentPage>1&&sharedDataPageList.page.currentPage<sharedDataPageList.page.totalPage}">
					<li class="active"><a href="#"><s:property value="sharedDataPageList.page.currentPage"/></a></li>
				</s:if>
				<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>1}">
					<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage+1"/>)"><s:property value="sharedDataPageList.page.currentPage+1"/></a></li>
				</s:if>
				<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>2}">
					<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage+2"/>)"><s:property value="sharedDataPageList.page.currentPage+2"/></a></li>
				</s:if>
				<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>3}">
					<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage+3"/>)"><s:property value="sharedDataPageList.page.currentPage+3"/></a></li>
				</s:if>
				<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>4}">
					<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage+4"/>)"><s:property value="sharedDataPageList.page.currentPage+4"/></a></li>
				</s:if>
				<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>5}">
					<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage+5"/>)"><s:property value="sharedDataPageList.page.currentPage+5"/></a></li>
				</s:if>
				<s:if test="%{sharedDataPageList.page.currentPage<4}">
					<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>6}">
						<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage+6"/>)"><s:property value="sharedDataPageList.page.currentPage+6"/></a></li>
					</s:if>
				</s:if>
				<s:if test="%{sharedDataPageList.page.currentPage==1}">
					<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>7}">
						<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage+7"/>)"><s:property value="sharedDataPageList.page.currentPage+7"/></a></li>
					</s:if>
					<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>8}">
						<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage+8"/>)"><s:property value="sharedDataPageList.page.currentPage+8"/></a></li>
					</s:if>
				</s:if>
				<s:elseif test="%{sharedDataPageList.page.currentPage==2}">
					<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>7}">
						<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage+7"/>)"><s:property value="sharedDataPageList.page.currentPage+7"/></a></li>
					</s:if>
				</s:elseif>
				<s:elseif test="%{sharedDataPageList.page.currentPage>4}">
					<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>6}">
						<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage+6"/>)"><s:property value="sharedDataPageList.page.currentPage+6"/></a></li>
					</s:if>
				</s:elseif>
			</s:if>
			<s:else>
				<s:if test="%{sharedDataPageList.page.totalPage-8>0}">
					<s:iterator begin="%{sharedDataPageList.page.totalPage-8}" step="1" end="%{sharedDataPageList.page.totalPage-1}" var="step">
						<s:if test="#step==sharedDataPageList.page.currentPage">
							<li class="active"><a href="#"><s:property value="#step"/></a></li>
						</s:if>
						<s:else>
							<li><a href="javascript:searchSharedData(<s:property value="#step"/>)""><s:property value="#step"/></a></li>
						</s:else>
					</s:iterator>
				</s:if>
				<s:else>
					<s:iterator begin="2" step="1" end="%{sharedDataPageList.page.totalPage-1}" var="step">
						<s:if test="#step==sharedDataPageList.page.currentPage">
							<li class="active"><a href="#"><s:property value="#step"/></a></li>
						</s:if>
						<s:else>
							<li><a href="javascript:searchSharedData(<s:property value="#step"/>)""><s:property value="#step"/></a></li>
						</s:else>
					</s:iterator>
				</s:else>
			</s:else>
			
			<s:if test="%{sharedDataPageList.page.totalPage-sharedDataPageList.page.currentPage>=8&&sharedDataPageList.page.totalPage>10}">
				<li>...</li>
			</s:if>
			
			<s:if test="%{sharedDataPageList.page.currentPage==sharedDataPageList.page.totalPage&&sharedDataPageList.page.totalPage>1}">
				<li class="active"><a href="#"><s:property value="sharedDataPageList.page.totalPage"/></a></li>
			</s:if>
			<s:elseif test="%{sharedDataPageList.page.totalPage>1}">
				<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.totalPage"/>)"><s:property value="sharedDataPageList.page.totalPage"/></a></li>
			</s:elseif>
			<s:if test="%{sharedDataPageList.page.hasNext}">
				<li><a href="javascript:searchSharedData(<s:property value="sharedDataPageList.page.currentPage+1"/>)">&gt;</a></li>
			</s:if>
			<li>
				共<s:property value="sharedDataPageList.page.totalPage"/>页&nbsp;|&nbsp;合计<s:property value="sharedDataPageList.page.rowCount"/>条
			</li>
	</ul>
    </s:if>
</div> 
<div class="modal hide fade" id="sharedDataMoreInfoModal">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">×</a>
    	<h3><img src="<%=request.getContextPath()%>/images/publicIcon/icon-modal-03.png"/>更多信息</h3>
  	</div>
  	<div class="modal-body">
  		<table>
  			<tr style="line-height: 40px;">
  				<td align="right">物种：</td>
  				<td>
  					<span id="sharedDataStrainSpan"></span>
  				</td>
  			</tr>
  			<tr style="line-height: 40px;">
  				<td align="right">数据标签：</td>
  				<td>
  					<span id="sharedDataDataTagSpan"></span>
  				</td>
  			</tr>
  		</table>
  		<div id="saveMoreInfoDiv" style="margin-left: 70px;margin-top: 20px;"></div>
  	</div>
  	<div class="modal-footer">
  	</div>
</div>