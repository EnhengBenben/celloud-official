<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
.dataautocomplete{list-style-type:none;margin-left:30px;border:1px solid #ccc;width:281px;position:absolute;left:170px;top:83px; background:#fff}
.dataautocomplete li{font-size:12px; font-family:"Lucida Console", Monaco, monospace; cursor:pointer; height:21px; line-height:20px}
.datahovers{ background-color:#3368c4; color:fff}
select{display: inline-block;margin-bottom: 0;background-color: #f3fafd;height: 28px;padding: 4px;font-size: 13px;line-height: 18px;color: #555555;border: 1px solid #a0d1e3;font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;font-weight: normal;margin: 0;vertical-align: middle;-webkit-appearance: menulist;box-sizing: border-box;align-items: center;white-space: pre;-webkit-rtl-ordering: logical;cursor: default;font: -webkit-small-control;letter-spacing: normal;word-spacing: normal;text-transform: none;text-indent: 0px;text-shadow: none;text-align: start;-webkit-writing-mode: horizontal-tb;}
</style>
<input type="hidden" id="pageRecordNumHidden" value='<s:property value="pageRecordNum"/>'/>
<input type="hidden" id="currentPageRecordNum" value='<s:property value="%{dataPageList.datas.size()}" />'>
<table class="table">
	<thead>
    	<tr>
        	<th>
        		<!-- 全选 -->
        		<input type="checkbox" id="selAll" class="selAll" style="border:none;"/>
        	</th>
        	<th>文件名称 <a href="javascript:sortByFileName();"> <img id="sortFileName" src="<%=request.getContextPath()%>/images/publicIcon/descending.png"/></a></th>
        	<th>数据编号</th>
        	<th>文件别名</th>
        	<th>数据大小</th>
        	<th>上传时间<a href="javascript:sortByCreateDate();"> <img id="sortCreateDate" src="<%=request.getContextPath()%>/images/publicIcon/descending_b.png"/></a></th>
			<th>操作</th>
        </tr>
    </thead>
	<tbody>
		<s:if test="%{dataPageList.datas.size()>0}">
			<s:iterator value="dataPageList.datas" status="st" id="data">
				<tr>
					<td class="center">
		        		<input name="datachk" type="checkbox" id='chk<s:property value="#data.fileId" />' value='<s:property value="#data.fileId" />' onclick="javascript:chkOnChange(this);" style="border:none;"/>
		        		<input type="hidden" value="<s:property value="#data.fileName" />" id="fileName<s:property value="#data.fileId" />">
		        	</td>
					<td title="<s:property value="#data.fileName"/>">
						<s:if test="#data.fileName.length()>40">
							<s:property value="#data.fileName.substring(0,40)"/>...
						</s:if>
						<s:else>
							<s:property value="#data.fileName" />
						</s:else>
						<s:date name="#data.createDate" format="yyyy/MM/dd" var="createDate"/>
						<s:date name="lastDate" format="yyyy/MM/dd" var="lastDate"/>
						<s:if test="#createDate==#lastDate">
							<img src="<%=request.getContextPath()%>/images/publicIcon/icon_new.png"/>
						</s:if>
						<s:if test="%{#data.isRunning>0}">
							<img src="<%=request.getContextPath()%>/images/publicIcon/icon-running.png" title="running" style="position: absolute;margin-top: 1px;"/>
						</s:if>
					</td>
					<td class="center">
						<div id="popover">
				            <a href="javascript:void(0);">
								<s:property value="#data.dataKey"/>
								<div>
									<div>
									  <table>
										<tr>
											<td align="right" width="100px;">样本类型/物种：</td>
											<td align="left"><s:property value="#data.strain"/></td>
										</tr>
										<tr>
											<td align="right">数据标签：</td>
											<td align="left"><s:property value="#data.dataTags"/></td>
										</tr>
										<tr>
											<td align="right">样本：</td>
											<td align="left"><s:property value="#data.sample"/></td>
										</tr>
									  </table>
									</div>
								</div>
							</a>
						</div>
					</td>
					<td class="center"><s:property value="#data.anotherName"/></td>
					<td class="center">
						<s:if test="%{#data.size>1048576}">
							<s:property value="#data.size/1048576" /> MB
						</s:if>
						<s:else>
							<s:property value="#data.size/1024" /> KB
						</s:else>
					</td>
					<td class="center"><s:date name="#data.createDate" format="yyyy/MM/dd" /></td>
					<td class="center">
        				<a href="javascript:showDataMoreInfoModal('<s:property value="#data.fileId"/>','<s:property value="#data.fileName"/>','<s:property value="#data.strain"/>','<s:property value="#data.dataTags"/>','<s:property value="#data.sample"/>','<s:property value="#data.anotherName"/>',<s:property value="#data.fileFormat"/>);"><img  title="更多" alt="更多" class="more" src="<%=request.getContextPath()%>/images/publicIcon/more.png"/></a>
        			</td>
				</tr>
			</s:iterator>
		</s:if>
		<s:else>
			<tr><td colspan="7">数据记录为空</td></tr>
		</s:else>
   	</tbody>
	<tfoot>
   	</tfoot>
</table>
<div class="pagination-new center">
	<s:if test="%{dataPageList.datas.size()>0}">
		<ul class="pages">
	    	<!-- 显示prev -->
	    	<li>
	    		每页
				<select id="pageRecordSel" onchange="javascript:changePageRecordNum();">
		   			<option value="10">10</option>
		   			<option value="20">20</option>
		   			<option value="30">30</option>
		   			<option value="40">40</option>
		   			<option value="50">50</option>
		   		</select>
				条
	    	</li>
	        <s:if test="%{dataPageList.page.hasPrev}">
				<li><a href="javascript:searchData('<s:property value="dataPageList.page.currentPage-1" />')">&lt;</a></li>
			</s:if>
			<!-- 显示第一页 -->
			<s:if test="%{dataPageList.page.currentPage==1}">
				<li class="active"><a href="#">1</a></li>
			</s:if>
			<s:else>
				<li><a href="javascript:searchData(1)">1</a></li>
			</s:else>
			
			<s:if test="%{dataPageList.page.currentPage>4&&dataPageList.page.totalPage>10}">
				<li>...</li>
			</s:if>
			
			<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>=7}">
				<s:if test="%{dataPageList.page.currentPage==3}">
					<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage-1"/>)"><s:property value="dataPageList.page.currentPage-1"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.currentPage==4}">
					<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage-2"/>)"><s:property value="dataPageList.page.currentPage-2"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.currentPage>3}">
					<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage-1"/>)"><s:property value="dataPageList.page.currentPage-1"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.currentPage>1&&dataPageList.page.currentPage<dataPageList.page.totalPage}">
					<li class="active"><a href="#"><s:property value="dataPageList.page.currentPage"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>1}">
					<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage+1"/>)"><s:property value="dataPageList.page.currentPage+1"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>2}">
					<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage+2"/>)"><s:property value="dataPageList.page.currentPage+2"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>3}">
					<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage+3"/>)"><s:property value="dataPageList.page.currentPage+3"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>4}">
					<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage+4"/>)"><s:property value="dataPageList.page.currentPage+4"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>5}">
					<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage+5"/>)"><s:property value="dataPageList.page.currentPage+5"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.currentPage<4}">
					<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>6}">
						<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage+6"/>)"><s:property value="dataPageList.page.currentPage+6"/></a></li>
					</s:if>
				</s:if>
				<s:if test="%{dataPageList.page.currentPage==1}">
					<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>7}">
						<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage+7"/>)"><s:property value="dataPageList.page.currentPage+7"/></a></li>
					</s:if>
					<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>8}">
						<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage+8"/>)"><s:property value="dataPageList.page.currentPage+8"/></a></li>
					</s:if>
				</s:if>
				<s:elseif test="%{dataPageList.page.currentPage==2}">
					<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>7}">
						<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage+7"/>)"><s:property value="dataPageList.page.currentPage+7"/></a></li>
					</s:if>
				</s:elseif>
				<s:elseif test="%{dataPageList.page.currentPage>4}">
					<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>6}">
						<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage+6"/>)"><s:property value="dataPageList.page.currentPage+6"/></a></li>
					</s:if>
				</s:elseif>
			</s:if>
			<s:else>
				<s:if test="%{dataPageList.page.totalPage-8>0}">
					<s:iterator begin="%{dataPageList.page.totalPage-8}" step="1" end="%{dataPageList.page.totalPage-1}" var="step">
						<s:if test="#step==dataPageList.page.currentPage">
							<li class="active"><a href="#"><s:property value="#step"/></a></li>
						</s:if>
						<s:else>
							<li><a href="javascript:searchData(<s:property value="#step"/>)"><s:property value="#step"/></a></li>
						</s:else>
					</s:iterator>
				</s:if>
				<s:else>
					<s:iterator begin="2" step="1" end="%{dataPageList.page.totalPage-1}" var="step">
						<s:if test="#step==dataPageList.page.currentPage">
							<li class="active"><a href="#"><s:property value="#step"/></a></li>
						</s:if>
						<s:else>
							<li><a href="javascript:searchData(<s:property value="#step"/>)"><s:property value="#step"/></a></li>
						</s:else>
					</s:iterator>
				</s:else>
			</s:else>
			
			<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>=8&&dataPageList.page.totalPage>10}">
				<li>...</li>
			</s:if>
			
			<s:if test="%{dataPageList.page.currentPage==dataPageList.page.totalPage&&dataPageList.page.totalPage>1}">
				<li class="active"><a href="#"><s:property value="dataPageList.page.totalPage"/></a></li>
			</s:if>
			<s:elseif test="%{dataPageList.page.totalPage>1}">
				<li><a href="javascript:searchData(<s:property value="dataPageList.page.totalPage"/>)"><s:property value="dataPageList.page.totalPage"/></a></li>
			</s:elseif>
			<s:if test="%{dataPageList.page.hasNext}">
				<li><a href="javascript:searchData(<s:property value="dataPageList.page.currentPage+1"/>)">&gt;</a></li>
			</s:if>
			<li>
				共<s:property value="dataPageList.page.totalPage"/>页&nbsp;|&nbsp;合计<s:property value="dataPageList.page.rowCount"/>条
			</li>
	</ul>
    </s:if>
</div> 
<!--修改物种标签-->
<div class="modal fade" id="addStrainModal">
	<div class="modal-header">
		<input type="hidden" id="dataStrainHidden"/>
		<a class="close" data-dismiss="modal">×</a>
        <span id="dataStrainFileNameSpan"></span>
    	<h3><img src="<%=request.getContextPath()%>/images/publicIcon/icon-modal-01.png"/><span id="dataStrainSpanInfo"></span>数据物种</h3>
  	</div>
  	<div class="modal-body">
    	<div class="control-group">
            <label class="control-label" for="textarea">数据物种：</label>
            <div class="controls">
            	<input type="hidden" id="dataStrainSel" style="width:300px;" value=""/>
		   		<span id="dataStrainInfo"></span>
            </div>
    	</div>
  	</div>
  	<div class="modal-footer">
    	<a href="javascript:saveDataStrain();" class="btn btn-green btn-small">提 交</a>
  	</div>
</div>
<!--添加标签-->
<div class="modal fade" id="addTagModal">
	<div class="modal-header">
		<input type="hidden" id="dataTagHidden"/>
		<a class="close" data-dismiss="modal">×</a>
        <span id="addTagSpan"></span>
    	<h3><img src="<%=request.getContextPath()%>/images/publicIcon/icon-modal-01.png"/><span id="dataTagHeaderMsg">数据标签</span></h3>
  	</div>
  	<div class="modal-body">
    	<div class="control-group">
            <label class="control-label" for="textarea">数据标签：</label>
            <div class="controls">
              <textarea class="input-xlarge" rows="3" id="dataTagTxt"></textarea>
              <span id="dataTagInfo"></span>
            </div>
    	</div>
  	</div>
  	<div class="modal-footer">
    	<a href="javascript:saveDataTag();" class="btn btn-green btn-small">提 交</a>
  	</div>
</div>
<!--数据共享-->
<div class="modal hide fade" id="shareDataModal">
	<div class="modal-header">
		<input type="hidden" id="dataShareHidden"/>
		<input type="hidden" id="dataShareOwnerHidden"/>
		<a class="close" data-dismiss="modal">×</a>
        <span id="shareDataSpan"></span>
    	<h3><img src="<%=request.getContextPath()%>/images/publicIcon/icon-modal-03.png"/>数据共享</h3>
  	</div>
  	<div class="modal-body">
        <input type="hidden" id="userSel" style="width:564px;" value=""/>
        <span id="dataShareSpanInfo"></span>
  	</div>
  	<div class="modal-footer">
    	<a href="javascript:saveShareData();" class="btn btn-green btn-small">确 定</a>
  	</div>
</div>
<!-- 查看数据更多信息 -->
<div class="modal hide fade" id="dataMoreInfoModal">
	<div class="modal-header">
		<input type="hidden" id="dataMoreInfoHidden"/>
		<a class="close" data-dismiss="modal">×</a>
        <span id="dataMoreNameInfoSpan"></span>
    	<h3><img src="<%=request.getContextPath()%>/images/publicIcon/icon-modal-03.png"/>更多信息</h3>
  	</div>
  	<div class="modal-body">
  		<div style="float: right;">
  			<a href="javascript:showDataMoreInfoEdit();"><img  title="编辑" alt="编辑" class="edit" src="<%=request.getContextPath()%>/images/publicIcon/edit.png"/></a>&nbsp;&nbsp;
  			<a href="javascript:cancelEditMoreInfo();"><img  title="取消编辑" class="canceledit" alt="取消编辑" src="<%=request.getContextPath()%>/images/publicIcon/cancel_edit.png"/></a>
  		</div>
  		<table>
  		    <tr style="line-height: 40px;">
                <td align="right">文件别名：</td>
                <td>
                    <input type="text" readonly="readonly" id="anotherNameHidden" style="width:353px;background-color: #eee;" onkeyup="value=value.replace(/[^\u4E00-\u9FA5\w]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5\w]/g,''))" placeholder="请输入字母\数字\下划线\汉字"/>
                    <input type="hidden" id="fileFormatHidden" />
                </td>
            </tr>
  			<tr style="line-height: 40px;">
  				<td align="right">样本类型/物种：</td>
  				<td id="dataTag">
        			<span id="dataMoreInfoStrainSpan">
        				<input type="hidden" id="dataMoreInfoStrainSel" style="width:364px;" value="" disabled="disabled"/>
        			</span>
  				</td>
  			</tr>
  			<tr style="line-height: 40px;">
  				<td align="right">数据标签：</td>
  				<td>
        			<input type="text" readonly="readonly" id="dataMoreInfoStrainHidden" style="width:353px;background-color: #eee;"/>
  				</td>
  			</tr>
  			<tr style="line-height: 40px;">
  				<td align="right">样本：</td>
  				<td>
        			<input type="text" readonly="readonly" id="dataMoreInfoSampleHidden" style="width:353px;background-color: #eee;" maxlength="45"/>
  				</td>
  			</tr>
  		</table>
  		<div id="saveMoreInfoDiv" style="margin-left: 70px;margin-top: 20px;"></div>
  	</div>
  	<div class="modal-footer">
    	<a href="javascript:saveDataMoreInfo();" class="btn btn-green btn-small">确 定</a>
  	</div>
</div>
<!-- 批量管理数据信息 -->
<div class="modal hide fade" id="batchManageDatasModal">
	<div class="modal-header">
		<input type="hidden" id="batchManageDatasHidden"/>
		<a class="close" data-dismiss="modal">×</a>
    	<h3><img src="<%=request.getContextPath()%>/images/publicIcon/icon-modal-03.png"/>统一编辑数据
    	  <a href="javascript:showOnetoOneManageModel();"style="font-size: 14px; font-weight: normal;padding-left:30px">编辑单个数据</a>
    	</h3>
  	</div>
  	<div class="modal-body">
  		<table>
  		    <tr style="line-height: 40px;">
                <td align="right">文件别名：</td>
                <td>
                    <input type="text"  id="batchManageAnotherName" style="width:353px;" onkeyup="value=value.replace(/[^\u4E00-\u9FA5\w]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5\w]/g,''))" placeholder="请输入字母\数字\下划线\汉字"/>
                </td>
            </tr>
  			<tr style="line-height: 40px;">
  				<td align="right">样本类型/物种：</td>
  				<td>
        			<span id="dataMoreInfoStrainSpan">
        				<input type="hidden" id="batchManageDatasStrainSel" style="width:364px;" value=""/>
        			</span>
  				</td>
  			</tr>
  			<tr style="line-height: 40px;">
  				<td align="right">数据标签：</td>
  				<td>
        			<input type="text" id="batchManageDatasTarg" style="width:353px;"/>
  				</td>
  			</tr>
  			<tr style="line-height: 40px;">
  				<td align="right">样本：</td>
  				<td>
        			<input type="text" id="batchManageDatasSample" style="width:353px;" maxlength="45"/>
  				</td>
  			</tr>
  		</table>
  		<div id="saveMoreInfoDiv" style="margin-left: 70px;margin-top: 20px;"></div>
  	</div>
  	<div class="modal-footer">
    	<a href="javascript:saveBatchManageDatas();" class="btn btn-green btn-small">确 定</a>
  	</div>
</div>
<!-- 编辑单个数据信息 -->
<div class="modal hide fade" id="oneToOneManageDatasModal" >
	<div class="modal-header">
		<input type="hidden" id="batchManageDatasHidden"/>
		<a class="close" data-dismiss="modal">×</a>
    	<h3>
    	  <img src="<%=request.getContextPath()%>/images/publicIcon/icon-modal-03.png"/>编辑单个数据
    	  <a href="javascript:tobatchManageModel();"style="font-size: 14px; font-weight: normal;padding-left:30px">统一编辑数据</a>
    	</h3>
  	</div>
  	<div class="modal-body">
  		<table class="table table-tab table-bordered" id="onetoOneManageList">
  		</table>
  		<div id="saveMoreInfoDiv" style="margin-left: 70px;margin-top: 20px;"></div>
  	</div>
  	<div class="modal-footer">
    	<a href="javascript:saveOneToOneManageDatas();" class="btn btn-green btn-small">确 定</a>
  	</div>
</div>
<script type="text/javascript">
	$.ajaxSetup ({
		cache: false //关闭AJAX相应的缓存
	});
	$(document).ready(function(){
		initDataList();
	});
</script>