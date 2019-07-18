//
//
var spike = {
    URL: {},
    handlerSpike:function(spikeId,node) {
        //获取秒杀地址，显示控制逻辑，执行秒杀
        node.hide().html('<button class="btn btn-primary btn-lg" id="spikeBtn">开始秒杀</button>');
        $.post('/phoneSpike/spike/'+spikeId+'/exposer',{},function (result) {
            //回调函数中，执行交互逻辑
            if (result && result['success']){
                var exposer=result['data'];
                if (exposer['exposed']){
                    //开启秒杀
                    var md5=exposer['md5'];
                    var spikeUrl='/phoneSpike/spike/'+spikeId+'/'+md5+'/execution';
                    console.log('spikeUrl'+spikeUrl);
                    //绑定一次点击事件,防止重复点击
                    $('#spikeBtn').one('click',function () {
                        //先禁用按钮
                        $(this).addClass('disabled');
                        //发送秒杀请求
                        $.post(spikeUrl, {},function (result) {
                            console.log("result"+result['success']);
                            if (result && result['success']){
                                var spikeResult=result['data'];
                                var state=spikeResult['status'];
                                var stateinfo=spikeResult['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">'+stateinfo+'</span>');
                            }
                        });
                    });
                    node.show();
                }else{
                    //未开启秒杀
                    var now=exposer['now'];
                    var start=exposer['start'];
                    var end=exposer['end'];
                    //重新计算倒计时逻辑
                    spike.countdown(spikeId,now,start,end);
                }
            }else{
                console.log("result"+result);
            }
        });


    },
    ValidatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },

    countdown: function (spikeId, nowTime, startTime, endTime) {
        var spikeBox = $('#spike-box');
        if (nowTime > endTime) {
            spikeBox.html('秒杀结束');
        } else if (nowTime < startTime) {
            //秒杀未开始，计时事件绑定
            var spikeTime = new Date(startTime + 1000);
            spikeBox.countdown(spikeTime, function (event) {
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                spikeBox.html(format);
            }).on('finish.countdown',function () {
                //获取秒杀地址，控制现实逻辑
                spike.handlerSpike(spikeId,spikeBox);
            });
        }else{
            //秒杀已经开始
            spike.handlerSpike(spikeId,spikeBox);
        }
    },
    detail: {
        init: function (params) {
            //手机验证登录，计时交互
            //在cookie中查找手机
            var spikePhone = $.cookie('spikePhone');
            if (!spike.ValidatePhone(spikePhone)) {
                //绑定手机号
                //控制输出
                var spikePhoneModal = $('#spikePhoneModel');
                spikePhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: 'static',//禁止位置关闭
                    keyboard: false
                });
                $('#spikePhoneBtn').click(function () {
                    var inputPhone = $('#spikephoneKey').val();
                    console.log("inputPhone" + inputPhone)//TODO
                    if (spike.ValidatePhone(inputPhone)) {
                        //写入cookie,设置超时时间
                        $.cookie('spikePhone', inputPhone, {expires: 7, path: '/phoneSpike/spike'});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $('#spikePhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });

            }
            //已经登录
            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var spikeId = params['spikeId'];
            $.get('/phoneSpike/spike/currentTime', {}, function (result) {
                if (result && result['success']) {
                    var nowtime = result['data'];
                    spike.countdown(spikeId, nowtime, startTime, endTime);
                } else {
                    console.log("result" + result);
                }
            });
        }

    }
}