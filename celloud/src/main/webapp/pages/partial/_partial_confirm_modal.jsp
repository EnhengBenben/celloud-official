<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="confirm-modal" class="modal tips-modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
        <h4 class="modal-title">请确认</h4>
      </div>
      <div class="modal-body">
        <h5><i class="fa fa-exclamation-triangle" aria-hidden="true"></i>{{confirmInfo}}</h5>
      </div>
      <div class="modal-footer text-center">
        <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
        <button type="submit" class="btn" ng-click="ok()">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->