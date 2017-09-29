<%--
  Created by IntelliJ IDEA.
  User: qxg
  Date: 17-6-29
  Time: 下午8:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/base/base.jsp"%>
<html>
<head>
    <title>欢迎</title>
    <mylink>
        <style>

        </style>
    </mylink>
</head>
<body>
    <mydiv>
        <div style="width: 100%;height: 100%;">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th class="col-sm-4">比赛名称</th>
                        <th class="col-sm-1">比赛等级</th>
                        <th class="col-sm-1">队长人数</th>
                        <th class="col-sm-1">队员人数</th>
                        <th class="col-sm-1">指导老师人数</th>
                        <th class="col-sm-2">创建时间</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${matchInfos}" var="item">
                        <tr>
                            <td>${item.name}</td>
                            <td>${item.level}</td>
                            <td>${item.leaderNum}</td>
                            <td>${item.memberNum}</td>
                            <td>${item.teacherNum}</td>
                            <td>${item.createTime}</td>
                            <td>
                                <a style="cursor: pointer" class="delete" data-id="${item.id}"><span class="label label-danger"><i class="glyphicon glyphicon-remove"></i>删除</span></a>
                                <a style="cursor: pointer" class="info" data-id="${item.id}"><span class="label label-info"><i class="glyphicon glyphicon-option-vertical"></i>详情</span></a>
                                <c:if test="${item.supply < 0}">
                                    <a style="cursor: pointer" class="openSupply" data-id="${item.id}"><span class="label label-info"><i class="glyphicon glyphicon-eye-open"></i>开启补录</span></a>
                                </c:if>
                                <c:if test="${item.supply > 0}">
                                    <a style="cursor: pointer" class="closeSupply" data-id="${item.id}"><span class="label label-warning"><i class="glyphicon glyphicon-eye-close"></i>关闭补录</span></a>
                                </c:if>
                                <a style="cursor: pointer" href="/admin/awards/info/${item.id}"><span class="label label-success"><i class="glyphicon glyphicon-usd"></i>评奖</span></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!--模态框，显示详细信息-->
        <div class="supplyModal modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">开启补录</h4>
                    </div>
                    <div class="modal-body">
                        <span id="info"></span>
                        <input type="hidden" id="itemId"/>
                        <input type="hidden" id="supplyFlag"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="confirm" class="btn btn-primary">确定</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!--模态框结束-->

        <!--模态框，显示删除提示框-->
        <div class="deleteModal modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">开启补录</h4>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="delete"/>
                        如果删除该比赛，则该比赛中的所有记录都将会被删除，比如比赛申请表，比赛提交的数据等。谨慎操作，确定要删除？
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="confirmToDelete" class="btn btn-primary">确定</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!--模态框结束-->

        <!--模态框，显示详细信息-->
        <div class="match_info modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">详情</h4>
                    </div>
                    <div class="modal-body">
                        <form action="">
                            <input type="hidden" id="id" value=""/>
                            <div class="input-group">
                                <div class="input-group-addon">比赛名称</div>
                                <input class="form-control"  type="text" id="name" name="name" />
                            </div>

                            <div class="input-group">
                                <div class="input-group-addon">比赛类别</div>
                                <select id="type1" class="form-control" name="type1"></select>
                                <div class="input-group-addon">类别分类</div>
                                <select id="type2" class="form-control" name="type2"></select>
                                <div class="input-group-addon">比赛级别</div>
                                <select id="level" class="form-control" name="level">
                                    <option value="1">国赛</option>
                                    <option value="2">省级</option>
                                    <option value="3">校级</option>
                                    <option value="4">院级</option>
                                </select>
                            </div>

                            <div class="input-group">
                                <%--<div class="input-group-addon">队长个数</div>--%>
                                <input class="form-control" id="leaderNum" type="hidden" name="leaderNum" />
                                <div class="input-group-addon">队长可参与个数</div>
                                <input class="form-control" id="leaderInNum" type="number" name="leaderInNum" />
                            </div>

                            <div class="input-group">
                                <div class="input-group-addon">成员个数</div>
                                <input class="form-control" id="memberNum" type="number" name="memberNum" />
                                <div class="input-group-addon">成员可参与个数</div>
                                <input class="form-control" id="memberInNum" type="number" name="memberInNum" />
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">指导老师个数</div>
                                <input class="form-control" id="teacherNum" type="number" name="teacherNum" />
                                <div class="input-group-addon">指导老师指导个数</div>
                                <input class="form-control" id="teacherInNum" type="number" name="teacherInNum" />
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">项目开始时间</div>
                                <input type="text" readonly id="startTime" class="form_datetime form-control"/>
                                <div class="input-group-addon">项目结束时间</div>
                                <input type="text" readonly id="endTime" class="form_datetime form-control"/>
                            </div>
                            <input type="hidden" id="supply"/>
                            <hr>
                            <span style="display: inline-block;font-size: 16px;"><bold>阶段信息</bold></span>
                            <table id="allStage" class="table table-bordered">
                            </table>
                            <br>
                            <br>
                            <br>
                            <br>
                            <br>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" id="alter" class="btn btn-primary">修改</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!--模态框结束-->
    </mydiv>
    <myscript>
        <script>
            $(document).ready(function(){

                $("#alter").click(function(){
                    var id = $("#id").val();
                    var name = $("#name").val();
                    var type1 = $("#type1").val();
                    var type2 = $("#type2").val();
                    var level = $("#level").val();
                    var leaderNum    = $("#leaderNum").val();
                    var leaderInNum  = $("#leaderInNum").val();
                    var memberNum    = $("#memberNum").val();
                    var memberInNum  = $("#memberInNum").val();
                    var teacherNum   = $("#teacherNum").val();
                    var teacherInNum = $("#teacherInNum").val();
                    var isChoose     = new Array(11);
                    var startTime    = new Array(11);
                    var endTime      = new Array(11);
                    var stageName    = new Array(11);
                    var isChooseCheckBox = $(".isChoose");
                    var startTimeTmp = $(".startTime");
                    var endTimeTmp = $(".endTime");
                    var stageNameTmp = $(".stageName");
                    var infoStartTime = $("#startTime").val();
                    var infoEndTime = $("#endTime").val();
                    var supply = $("#supply").val();


                    for(var i=0; i<isChooseCheckBox.length; i++){
                        isChoose[i] = isChooseCheckBox[i].value;
                        startTime[i] = startTimeTmp[i].value;
                        endTime[i] = endTimeTmp[i].value;
                        stageName[i] = stageNameTmp[i].value;
                        //type[i] = typeTmp[i].value;
                    }

                    var data = {
                        "id":id,
                        "name":name,
                        "type1":type1,
                        "type2":type2,
                        "level":level,
                        "stageName":stageName,
                        "leaderNum":leaderNum,
                        "leaderInNum":leaderInNum,
                        "memberNum":memberNum,
                        "memberInNum":memberInNum,
                        "teacherNum":teacherNum,
                        "teacherInNum":teacherInNum,
                        "isChoose":isChoose,
                        "startTime":startTime,
                        "endTime":endTime,
                        "infoStartTime":infoStartTime,
                        "infoEndTime":infoEndTime,
                        "supply":supply
                    };

                    $.ajax({
                        url:"/admin/match/update",
                        data:data,
                        type:'post',
                        success:function(json){
                            if(json["success"] == true){
                                alert("更新成功");
                                location.href="/admin/matchs";
                            }else{
                                alert("更新失败");
                            }

                            //TODO 不论成功与否，都要把按钮的disable换成可点击
                        },
                        error:function(){
                            alert("未知错误");
                        }
                    });
                });

                updateDatetime();
                /** 禁止模态窗点击其他位置关闭*/
                $('.match_info').modal({backdrop: 'static', keyboard: false,show:false});
                /** 在进来的时候，要更新模态框中的类型信息，因为是下拉框选择的内容，所以一定要先获取所有的类型信息*/
                $.ajax({
                    url:"../type/type1",
                    success:function(json){
                        json.forEach(function(item){
                            //每个item都是一个json:{"id":2,"name":"省赛"}
                            $("#type1").append('<option value="'+item["id"]+'">' + item["name"] + '</option>');
                        });
                    }
                });

                $("#type1").change(function(){
                    var id = $(this).val();
                    changeType1(id);
                });


                /** 点击删除后，弹出删除页面*/
                $(".delete").click(function(){
                    var id = $(this).attr("data-id");
                    $("#delete").val(id);
                    $(".deleteModal").modal('show');
                });

                $("#confirmToDelete").click(function(){
                    var id = $("#delete").val();
                    $.ajax({
                        url:"./match/delete/"+id,
                        success:function(json){
                            if(json["success"] == true){
                                alert("删除成功");
                                window.location.href = "./matchs";
                            }else{
                                alert("删除失败");
                            }
                            $(".deleteModal").modal('hide');
                        },
                        error:function(error){

                        }
                    });
                });

                /** 点击详情后，弹出详情页面*/
                $(".info").click(function(){
                    //点击info后显示详情页面
                    var id = $(this).attr("data-id");
                    $.ajax({
                        url:"./match/"+id,
                        type:'get',
                        success:function(json){
                            //{"matchInfo":{"id":3,"name":"2016-2017全国大学生挑战杯竞赛","type1":1,"type2":2,"level":1,"allStage":"1,2,3,4","leaderNum":2,"memberNum":4,"teacherNum":1,"leaderInNum":1,"memberInNum":4,"teacherInNum":5,"createTime":1498799996000},"matchType":{"id":1,"name":"国赛"},"matchType2":{"id":2,"name":"嘿嘿嘿","matchTypeId":2},"allStage":[{"id":1,"name":"立项申请"},{"id":2,"name":"学院审核"},{"id":3,"name":"学校审核"},{"id":4,"name":"公布"}]}
                            var matchInfo = json["matchInfo"];
                            //alert(matchInfo["startTime"]);
                            $("#id").val(matchInfo["id"]);
                            $("#name").val(matchInfo["name"]);
                            $("#type1").val(matchInfo["type1"]);
                            changeType1(matchInfo["type1"]);
                            $("#type2").val(matchInfo["type2"]);
                            $("#level").val(matchInfo["level"]);
                            $("#allStage").val(matchInfo["allStage"]);
                            $("#allStage").selectpicker("refresh");
                            getAllStageByIds(matchInfo["allStage"]);
                            $("#leaderNum").val(matchInfo["leaderNum"]);
                            $("#memberNum").val(matchInfo["memberNum"]);
                            $("#teacherNum").val(matchInfo["teacherNum"]);
                            $("#leaderInNum").val(matchInfo["leaderInNum"]);
                            $("#memberInNum").val(matchInfo["memberInNum"]);
                            $("#teacherInNum").val(matchInfo["teacherInNum"]);
                            $("#startTime").val(getLocalTime(matchInfo["startTime"]));
                            $("#endTime").val(getLocalTime(matchInfo["endTime"]));
                            $("#supply").val(matchInfo["supply"]);
                            $(".match_info").modal('show');
                        },
                        error:function(error){
                            alert('失败');
                        }
                    });
                });

                /** 以下是function的定义*/
                /** type1改变的时候更新type2的option所有值*/
                function changeType1(id){
                    //首先要清楚数据
                    $("#type2").empty();
                    $.ajax({
                        url:"../type/type1/type2/" + id,
                        success:function(json){
                            json.forEach(function(item){
                                //每个item都是一个json:{"id":2,"name":"省赛"}
                                $("#type2").append('<option value="'+item["id"]+'">' + item["name"] + '</option>');
                            });
                        }
                    });
                }
                /**通过 1,2,3,4获取所有的stage*/
                function getAllStageByIds(ids){
                    //首先清空数据
                    $("#allStage").empty();
                    $("#allStage").append('<thead><tr> <th class="col-sm-1">阶段</th> <th class="col-sm-2">阶段名称</th> <th class="col-sm-3">阶段开始时间</th> <th class="col-sm-3">阶段结束时间</th> </tr> </thead>')
                    $.ajax({
                        url:"../stage/"+ids,
                        success:function(json){
                            $("#allStage").append('<tbody>');
                            var idt=1;
                            json.forEach(function(item){
                                //TODO 将时间戳转换为正常的日期信息格式
                                $("#allStage").append('<tr><th>'+idt+'<input type="hidden" class="isChoose" value="'+ item["id"] +'" </th><th>'+item["name"]+'<input type="hidden" class="stageName" value="'+item["name"] +'"></th><th><input type="text" class="form_datetime form-control startTime" readonly value="'+getLocalTime(item["startTime"])+'"/></th><th><input type="text" readonly class="form_datetime form-control endTime" value="'+getLocalTime(item["endTime"])+'"/></th></tr>');
                                idt = idt+1;
                            });
                            $("#allStage").append('</tbody>');
                            updateDatetime()
                        }
                    })
                }

                //时间戳转换为日期
                function add0(m){return m<10?'0'+m:m }
                function getLocalTime(timestamp)
                {
                    //timestamp是整数，否则要parseInt转换,不会出现少个0的情况

                    var time = new Date(timestamp);
                    var year = time.getFullYear();
                    var month = time.getMonth()+1;
                    var date = time.getDate();
                    var hours = time.getHours();
                    var minutes = time.getMinutes();
                    var seconds = time.getSeconds();
                    return year+'-'+add0(month)+'-'+add0(date)+' '+add0(hours)+':'+add0(minutes);
                }
                /** 更新选择日期按钮*/
                function updateDatetime(){
                    $(".form_datetime").datetimepicker({
                        language:  'zh-CN',
                        format: "yyyy-MM-dd hh:ii",
                        autoclose: true,
                        todayBtn: true,
                        pickerPosition: "bottom-left"
                    });
                }

                $(".openSupply").click(function(){
                    var itemId = $(this).attr("data-id");
                    $("#info").text("确定开启补录?");
                    $("#itemId").val(itemId);
                    $("#supplyFlag").val(1);
                    $(".supplyModal").modal("show");
                });

                $(".closeSupply").click(function(){
                    var itemId = $(this).attr("data-id");
                    $("#info").text("确定关闭补录?");
                    $("#supplyFlag").val(-1);
                    $("#itemId").val(itemId);
                    $(".supplyModal").modal("show");
                });

                $("#confirm").click(function(){
                    var itemId = $("#itemId").val();
                    var supplyFlag = $("#supplyFlag").val();
                    var data = {
                        supplyFlag:supplyFlag,
                        itemId:itemId
                    }

                    $.ajax({
                        url:"/admin/supply",
                        data:data,
                        type:'post',
                        success:function(res){
                            var json = JSON.parse(res);
                            alert(json["msg"]);
                        }
                    });
                });
            });
        </script>
    </myscript>
</body>
</html>
