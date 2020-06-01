/**
 * @file framework/loadjs
 * @author jianling(zhaochengyang@baidu.com)
 * @author gukunfeng(gukunfeng@baidu.com)
 */

/* eslint-disable */
(function () {
    // IAM 模块路径判断
    var iamModule = function () {
        var parts = (location.href.split('~')[1] || '').split('&');
        var redirect = 'https://console.bce.baidu.com';
        var mobile = '***';
        var url = '';

        for (var i = 0; i < parts.length; i++) {
            if (parts[i].indexOf('redirect=') === 0) {
                redirect = parts[i].substr('redirect'.length + 1);
            }

            // 如果是二次验证则会携带加密手机号码
            if (parts[i].indexOf('mobile=') === 0) {
                mobile = parts[i].substr('mobile'.length + 1);
            }
        }

        if (/iam\/user\/v2\/activate/.test(location.hash)) {
            // 定向跳转至账户激活页面
            url = location.origin + '/index_bce_app.html?redirect=' + redirect + '#/accountActive';
        }
        else if (/iam\/user\/v2\/verify\/login/.test(location.hash)) {
            // 定向跳转至二次验证页面 & 携带加密电话号码
            url = location.origin + '/index_bce_app.html?mobile='+ mobile + '&redirect=' + redirect + '#/loginVerify';
        }
        else if (/qualify\/person/.test(location.hash)) {
            // 定向跳转至实名认证
            url = location.origin + '/index_bce_app.html?redirect=' + redirect + '#/personQualify';
        }
        else {
            url = location.origin + '/index_bce_app.html#/accountInfo';
        }
        return url;
    };

    // 主模块路径判断逻辑
    var judgeMoudle = function () {
        var url = location.origin + '/index_bce_app.html/#/';
        // billing模块,只跳转(财务总览页)
        if (/#\/account\/index/.test(location.hash)) {
            url += 'billingOverview';
            return url;
        }

        // ticket模块 跳转工单域
        if (/\/ticket\//g.test(location.href)) {
            url = 'https://ticket.bce.baidu.com/app/index.html#/ticketList';
            return url;
        }

        // iam模块
        if (/\/iam\//g.test(location.href)) {
            url = iamModule();
            return url;
        }

        /**
         * 对于已经接入移动端的模块，则跳转到对应的移动端产品页或者产品默认页面
         * 否则就不跳转
         */
        var finishedModules = {
            home: {
                'default': 'dashboard'
            },
            bcc: {
                'default': 'bccInstance',
                '/bcc/instance/list': 'bccInstance'
                // '/bcc/instance/create': 'bccPurchase'
            },
            cdn: {
                'default': 'cdnInstance',
                '/cdn/list': 'cdnInstance'
            },
            bcd: {
                'default': 'bcdUserAsset'
            },
            bos: {
                'default': 'bosInstance'
            },
            rds: {
                'default': 'rdsInstance'
            },
            bae: {
                'default': 'baeInstance'
            },
            baepro: {
                'default': 'baeproInstance'
            },
            vod: {
                'default': 'vodInstance'
            },
            lss: {
                'default': 'lssInstance'
            },
            bcm: {
                'default': 'bcmConsole'
            }
        };

        // 总览页跳转到移动端 dashboard
        var overviewHash = ['/index/overview', '/index/overview_v3', '/aip/overview'];
        if (location.pathname === '/' || overviewHash.indexOf(location.hash) !== -1) {
            const mobileUrl = url + finishedModules.home.default;
            return location.replace(mobileUrl);
        }

        // 其它产品页则根据配置的映射关系跳转
        var regResult = /\/(.*)\//g.exec(location.pathname) || [];
        var currentModule = regResult[1];
        if (finishedModules.hasOwnProperty(currentModule)) {
            var currentModulePath = finishedModules[currentModule];
            const mobileUrl =  url + (currentModulePath[location.hash.slice(1)] || currentModulePath.default);
            return location.replace(mobileUrl);
        }

        // 不是移动端的产品则不处理
        return false;
    };

    /**
     * 判断当前用户是否是子用户
     *
     * @param {string} cookieString document.cookie
     * @return {boolean} 是否是子用户
     */
    var isSubuser = function (cookieString) {
        var cookieArr = (document.cookie || cookieString).split(';');
        var cookie = {};
        for (var i = 0; i < cookieArr.length; i++) {
            // fixme: "BAIDUID=29AE489074DB0FF5E269D268D2E0EC59:FG=1"
            var temp = cookieArr[i].split('=');
            cookie[temp[0].trim()] = temp[1];
        }

        var mainAccountId = cookie['bce-login-accountid'];
        var subAccountId = cookie['bce-login-userid'];
        if (mainAccountId && subAccountId) {
            return !(mainAccountId === subAccountId);
        }
        // 子账户id不存在，则不为子用户
        return false;
    };

    // 如果是沙盒环境，loadjs.js 换成 loadjs.dev.js
    if (/bcetest/.test(location.host)) {
        document.write('<script type="text/javascript" src="https://bce.bdstatic.com/console/fe-framework/loadjs.dev.js"></script>');
        return;
    }

    if (G_CDN_ENDPOINT === undefined || !G_CDN_ENDPOINT) {
        var G_CDN_ENDPOINT = 'https://bce.bdstatic.com';
    }

    if (!window.G_CONSOLE_ENDPOINT) {
        window.G_CONSOLE_ENDPOINT = '';
    }

    var FW_PATH_PREFIX = G_CDN_ENDPOINT + '/console/fe-framework/f973cf7';

    // 判断活动来源 和 强制不跳转
    var cookies = document.cookie.split(';');
    var referrer = decodeURIComponent(document.referrer);
    var reg = new RegExp('(BCE_APP_IGNORE_FORCE=|")', 'g');
    var ignoreForce = true;

    for (var j = 0; j < cookies.length; j++) {
        if (cookies[j].indexOf('BCE_APP_IGNORE_FORCE') >= 0) {
            // 判断用户是否设置强制PC
            ignoreForce = cookies[j].replace(reg, '').trim() !== 'FALSE';
        }
    }

    // 跳转移动端对应模块
    if (referrer.indexOf('.baidu.com/campaign/') < 0
        && ignoreForce
        && screen.width <= 768
        && isSubuser() === false) {
        // 以前未登录跳转会在第一个文档请求处理，现在没有在该请求处理，导致我们的代码与预期运行不一致
        judgeMoudle();
    }


    function loadCss(url) {
        var link = document.createElement('link');
        link.setAttribute('rel', 'stylesheet');
        link.setAttribute('href', url);

        var head = document.getElementsByTagName('head')[0] || document.body;
        head.insertBefore(link, document.head.getElementsByTagName('link')[0]);
    }

    loadCss(FW_PATH_PREFIX + '/main.css');
    loadCss(G_CDN_ENDPOINT + '/iconfont/iconfont.css');

    if (window.esl || window.requirejs) {
        require.config({
            paths: {
                framework: FW_PATH_PREFIX + '/bundle'
            }
        });
    }
    else {
        document.write('<script src="' + FW_PATH_PREFIX + '/bundle.js"></script>');
    }
})();
