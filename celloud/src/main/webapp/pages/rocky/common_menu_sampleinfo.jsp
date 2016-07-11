<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<div class="info-form">
  <input id="info-input" class="field" type="text" placeholder="扫描样本编号/病历号"/>
  <a id="info-add-a" class="action">添加</a>
</div>
<div id="sample-error" class="errortip hide">
  <p> 此样品信息已经收集过，请核查或者采集下一管样品信息！
    <a id="close-error" href="javascript:void(0)"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a>
  </p>
</div>