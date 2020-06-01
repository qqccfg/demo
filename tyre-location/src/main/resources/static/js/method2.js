/*根据id获取对象*/
function $(str) {
    return document.getElementById(str);
}

//var addrShow = $('addr-show');
//var btn = document.getElementsByClassName('met1')[0];
var prov2 = $('prov2');
var city2 = $('city2');
var country2 = $('country2');

prov2.onchange = function (ev) {
    showCity2(this);
}
city2.onchange = function (ev) {
    showCountry2(this)
}
country2.onchange = function (ev) {
    selecCountry2(this)
}

/*用于保存当前所选的省市区*/
var current2 = {
    prov2: '',
    city2: '',
    country2: ''
};

/*自动加载省份列表*/
(function showProv2() {
    //btn.disabled = true;
    var len = provice.length;
    for (var i = 0; i < len; i++) {
        var provOpt = document.createElement('option');
        provOpt.innerText = provice[i]['name'];
        provOpt.value = i;
        prov2.appendChild(provOpt);
    }
})();

/*根据所选的省份来显示城市列表*/
function showCity2(obj) {
    var val = obj.options[obj.selectedIndex].value;
    if (val != current2.prov2) {
        current2.prov2 = val;
        //addrShow.value = '';
        //btn.disabled = true;
    }
    //console.log(val);
    if (val != null) {
        city2.length = 1;
        var cityLen = provice[val]["city"].length;
        for (var j = 0; j < cityLen; j++) {
            var cityOpt = document.createElement('option');
            cityOpt.innerText = provice[val]["city"][j].name;
            cityOpt.value = j;
            city2.appendChild(cityOpt);
        }
    }
}

function loadCity2(val) {
    current2.prov2 = val
    city2.length = 1;
    var cityLen = provice[val]["city"].length;
    for (var j = 0; j < cityLen; j++) {
        var cityOpt = document.createElement('option');
        cityOpt.innerText = provice[val]["city"][j].name;
        cityOpt.value = j;
        city2.appendChild(cityOpt);
    }
}

/*根据所选的城市来显示县区列表*/
function showCountry2(obj) {
    var val = obj.options[obj.selectedIndex].value;
    current2.city2 = val;
    if (val != null) {
        country2.length = 1; //清空之前的内容只留第一个默认选项
        var countryLen = provice[current2.prov2]["city"][val].districtAndCounty.length;
        if (countryLen == 0) {
            //addrShow.value = provice[current.prov].name + '-' + provice[current.prov]["city"][current.city].name;
            return;
        }
        for (var n = 0; n < countryLen; n++) {
            var countryOpt = document.createElement('option');
            countryOpt.innerText = provice[current2.prov2]["city"][val].districtAndCounty[n];
            countryOpt.value = n;
            country2.appendChild(countryOpt);
        }
    }
}

function loadCountry2(val) {
    current2.city2 = val
    country2.length = 1; //清空之前的内容只留第一个默认选项
    var countryLen = provice[current2.prov2]["city"][val].districtAndCounty.length;
    if (countryLen == 0) {
        //addrShow.value = provice[current.prov].name + '-' + provice[current.prov]["city"][current.city].name;
        return;
    }
    for (var n = 0; n < countryLen; n++) {
        var countryOpt = document.createElement('option');
        countryOpt.innerText = provice[current2.prov2]["city"][val].districtAndCounty[n];
        countryOpt.value = n;
        country2.appendChild(countryOpt);
    }
}

/*选择县区之后的处理函数*/
function selecCountry2(obj) {
    current2.country2 = obj.options[obj.selectedIndex].value;
    if ((current2.city2 != null) && (current2.country2 != null)) {
        //btn.disabled = false;
    }
}

function saveCountry2(val) {
    current2.country2 = val

}

/*点击确定按钮显示用户所选的地址*/

function showAddr2() {
    return  provice[current2.prov2].name + '-' + provice[current2.prov2]["city"][current2.city2].name + '-' + provice[current2.prov2]["city"][current2.city2].districtAndCounty[current2.country2];
}
function AddrIndex2() {
    return current2.prov2+"-"+current2.city2+"-"+current2.country2
}