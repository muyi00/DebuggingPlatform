window.onload = function () {
    $("#tab_content").load("./html/index_page_a.html")
}

window.onbeforeunload = function () {
    $("#tab_content").load("./html/index_page_a.html")
}


function changeStyle() {
    this.onclick = function () {
        var list = this.parentNode.childNodes;
        for (var i = 0; i < list.length; i++) {
            if (1 == list[i].nodeType && 'active' == list[i].className) {
                list[i].className = "";
            }
        }
        this.className = 'active';
        if (this.id == "a_li") {
            $("#tab_content").load("./html/index_page_a.html")
        } else if (this.id == "b_li") {
            $("#tab_content").load("./html/index_page_b.html")
            getAppInfo();
        } else if (this.id == "c_li") {
            $("#tab_content").load("./html/index_page_c.html")
        }
    }
}
var tabs = document.getElementById('tabs').childNodes;
for (var i = 0; i < tabs.length; i++) {
    if (1 == tabs[i].nodeType) {
        changeStyle.call(tabs[i]);
    }
}




///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

var URL_COOL = "http://cool.haoyunqi.com.cn";
var URL_220 = "http://192.168.2.220:8080/cool.web";
var URL_SHUANG = "http://192.168.2.21:8080/cool.shuang";
var URL_TAO = "http://192.168.2.20:8080/cool.web";

var qrcode = new QRCode(document.getElementById("qrcode"), {
    text: "",
    width: 200,
    height: 200,
    colorDark: "#000000",
    colorLight: "#ffffff",
    correctLevel: QRCode.CorrectLevel.H
});


function createQR() {

    var url = URL_220;
    if (document.getElementById("url_online").checked) {
        url = URL_COOL;
    } else if (document.getElementById("url_offline").checked) {
        url = URL_220;
    } else if (document.getElementById("url_shuang").checked) {
        url = URL_SHUANG;
    } else if (document.getElementById("url_tao").checked) {
        url = URL_TAO;
    } else {
        var otherUrl = document.getElementById("url").value;
        var str = otherUrl.replace(/(^\s*)|(\s*$)/g, '');//去除空格;
        if (str == '' || str == undefined || str == null) {
            alert("地址不能为空")
            return;
        }
        url = otherUrl;
    }

    var isOpenHx = document.getElementById("isOpenHx").checked;
    var isDebug = document.getElementById("isDebug").checked
    var isWriteLog = document.getElementById("isWriteLog").checked

    var params = {
        "packageName": "com.dlh.hqy.psclient",
        "dateStr": getCurrentDateYYYYMMDD(),
        "urlStr": url,
        "openHx": isOpenHx,
        "debug": isDebug,
        "writeLog": isWriteLog
    }
    var json = JSON.stringify(params);

    qrcode.clear();
    qrcode.makeCode(json);
}


function url_online_change() {
    document.getElementById("url_online").checked = true;
    document.getElementById("url_offline").checked = false;
    document.getElementById("url_shuang").checked = false;
    document.getElementById("url_tao").checked = false;
    document.getElementById("url_other").checked = false;
    document.getElementById("url_input_row").style.display = 'none';

}

function url_offline_change() {
    document.getElementById("url_online").checked = false;
    document.getElementById("url_offline").checked = true;
    document.getElementById("url_shuang").checked = false;
    document.getElementById("url_tao").checked = false;
    document.getElementById("url_other").checked = false;
    document.getElementById("url_input_row").style.display = 'none';
}

function url_shuang_change() {
    document.getElementById("url_online").checked = false;
    document.getElementById("url_offline").checked = false;
    document.getElementById("url_shuang").checked = true;
    document.getElementById("url_tao").checked = false;
    document.getElementById("url_other").checked = false;
    document.getElementById("url_input_row").style.display = 'none';
}

function url_tao_change() {
    document.getElementById("url_online").checked = false;
    document.getElementById("url_offline").checked = false;
    document.getElementById("url_shuang").checked = false;
    document.getElementById("url_tao").checked = true;
    document.getElementById("url_other").checked = false;
    document.getElementById("url_input_row").style.display = 'none';
}


function url_otherchange() {
    document.getElementById("url_online").checked = false;
    document.getElementById("url_offline").checked = false;
    document.getElementById("url_shuang").checked = false;
    document.getElementById("url_tao").checked = false;
    document.getElementById("url_other").checked = true;
    document.getElementById("url_input_row").style.display = 'block';
    document.getElementById("url").value = URL_220;
}

function getCurrentDateYYYYMMDD() {
    var nowDate = new Date();
    var year = nowDate.getFullYear();
    var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
    var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
    return year + month + day + "";
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function CreateXMLHttpRequest() {
    if (window.ActiveXObject) { //如果当前浏览器支持Active Xobject，则创建ActiveXObject对象
        //xmlobj = new ActiveXObject("Microsoft.XMLHTTP");
        try {
            xmlobj = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            try {
                xmlobj = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (E) {
                xmlobj = false;
            }
        }
    } else if (window.XMLHttpRequest) {//如果当前浏览器支持XMLHttp Request，则创建XMLHttpRequest对象
        xmlobj = new XMLHttpRequest();
    }
}

function getDownloadAppInfo() {
    var url = "http://api.haoyunqi.com.cn/open-api/app/release/v1/query/info/list"
    var data = JSON.stringify({
        "appType": 1,
        "page": 1,
        "pageIndex": 1,
        "pageSize": 20,
        "rows": 20
    })
    alert(data)
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json; charset=utf-8",
        data: data,
        dataType: "json",
        success: function (message) {
            if (message > 0) {
                alert("请求已提交！我们会尽快与您取得联系");
            }
        },
        error: function (message) {
            alert("提交数据失败！" + JSON.stringify(message));
        }
    });
}