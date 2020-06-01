define('humanize/main', [], function () {
    var humanize = {};
    humanize.pad = function (str, count, padChar, type) {
        str += '';
        if (!padChar) {
            padChar = ' ';
        } else if (padChar.length > 1) {
            padChar = padChar.charAt(0);
        }
        type = type === undefined ? 'left' : 'right';
        if (type === 'right') {
            while (str.length < count) {
                str = str + padChar;
            }
        } else {
            while (str.length < count) {
                str = padChar + str;
            }
        }
        return str;
    };
    humanize.time = function () {
        return new Date().getTime() / 1000;
    };
    var dayTableCommon = [
        0,
        0,
        31,
        59,
        90,
        120,
        151,
        181,
        212,
        243,
        273,
        304,
        334
    ];
    var dayTableLeap = [
        0,
        0,
        31,
        60,
        91,
        121,
        152,
        182,
        213,
        244,
        274,
        305,
        335
    ];
    humanize.date = function (format, timestamp) {
        var jsdate = timestamp === undefined ? new Date() : timestamp instanceof Date ? new Date(timestamp) : new Date(timestamp * 1000);
        var formatChr = /\\?([a-z])/gi;
        var formatChrCb = function (t, s) {
            return f[t] ? f[t]() : s;
        };
        var shortDayTxt = [
            'Sunday',
            'Monday',
            'Tuesday',
            'Wednesday',
            'Thursday',
            'Friday',
            'Saturday'
        ];
        var monthTxt = [
            'January',
            'February',
            'March',
            'April',
            'May',
            'June',
            'July',
            'August',
            'September',
            'October',
            'November',
            'December'
        ];
        var f = {
            d: function () {
                return humanize.pad(f.j(), 2, '0');
            },
            D: function () {
                return f.l().slice(0, 3);
            },
            j: function () {
                return jsdate.getDate();
            },
            l: function () {
                return shortDayTxt[f.w()];
            },
            N: function () {
                return f.w() || 7;
            },
            S: function () {
                var j = f.j();
                return j > 4 && j < 21 ? 'th' : {
                    1: 'st',
                    2: 'nd',
                    3: 'rd'
                }[j % 10] || 'th';
            },
            w: function () {
                return jsdate.getDay();
            },
            z: function () {
                return (f.L() ? dayTableLeap[f.n()] : dayTableCommon[f.n()]) + f.j() - 1;
            },
            W: function () {
                var midWeekDaysFromJan4 = f.z() - f.N() + 1.5;
                return humanize.pad(1 + Math.floor(Math.abs(midWeekDaysFromJan4) / 7) + (midWeekDaysFromJan4 % 7 > 3.5 ? 1 : 0), 2, '0');
            },
            F: function () {
                return monthTxt[jsdate.getMonth()];
            },
            m: function () {
                return humanize.pad(f.n(), 2, '0');
            },
            M: function () {
                return f.F().slice(0, 3);
            },
            n: function () {
                return jsdate.getMonth() + 1;
            },
            t: function () {
                return new Date(f.Y(), f.n(), 0).getDate();
            },
            L: function () {
                return new Date(f.Y(), 1, 29).getMonth() === 1 ? 1 : 0;
            },
            o: function () {
                var n = f.n();
                var W = f.W();
                return f.Y() + (n === 12 && W < 9 ? -1 : n === 1 && W > 9);
            },
            Y: function () {
                return jsdate.getFullYear();
            },
            y: function () {
                return String(f.Y()).slice(-2);
            },
            a: function () {
                return jsdate.getHours() > 11 ? 'pm' : 'am';
            },
            A: function () {
                return f.a().toUpperCase();
            },
            B: function () {
                var unixTime = jsdate.getTime() / 1000;
                var secondsPassedToday = unixTime % 86400 + 3600;
                if (secondsPassedToday < 0) {
                    secondsPassedToday += 86400;
                }
                var beats = secondsPassedToday / 86.4 % 1000;
                if (unixTime < 0) {
                    return Math.ceil(beats);
                }
                return Math.floor(beats);
            },
            g: function () {
                return f.G() % 12 || 12;
            },
            G: function () {
                return jsdate.getHours();
            },
            h: function () {
                return humanize.pad(f.g(), 2, '0');
            },
            H: function () {
                return humanize.pad(f.G(), 2, '0');
            },
            i: function () {
                return humanize.pad(jsdate.getMinutes(), 2, '0');
            },
            s: function () {
                return humanize.pad(jsdate.getSeconds(), 2, '0');
            },
            u: function () {
                return humanize.pad(jsdate.getMilliseconds() * 1000, 6, '0');
            },
            O: function () {
                var tzo = jsdate.getTimezoneOffset();
                var tzoNum = Math.abs(tzo);
                return (tzo > 0 ? '-' : '+') + humanize.pad(Math.floor(tzoNum / 60) * 100 + tzoNum % 60, 4, '0');
            },
            P: function () {
                var O = f.O();
                return O.substr(0, 3) + ':' + O.substr(3, 2);
            },
            Z: function () {
                return -jsdate.getTimezoneOffset() * 60;
            },
            c: function () {
                return 'Y-m-d\\TH:i:sP'.replace(formatChr, formatChrCb);
            },
            r: function () {
                return 'D, d M Y H:i:s O'.replace(formatChr, formatChrCb);
            },
            U: function () {
                return jsdate.getTime() / 1000 || 0;
            }
        };
        return format.replace(formatChr, formatChrCb);
    };
    humanize.numberFormat = function (number, decimals, decPoint, thousandsSep) {
        decimals = isNaN(decimals) ? 2 : Math.abs(decimals);
        decPoint = decPoint === undefined ? '.' : decPoint;
        thousandsSep = thousandsSep === undefined ? ',' : thousandsSep;
        var sign = number < 0 ? '-' : '';
        number = Math.abs(+number || 0);
        var intPart = parseInt(number.toFixed(decimals), 10) + '';
        var j = intPart.length > 3 ? intPart.length % 3 : 0;
        return sign + (j ? intPart.substr(0, j) + thousandsSep : '') + intPart.substr(j).replace(/(\d{3})(?=\d)/g, '$1' + thousandsSep) + (decimals ? decPoint + Math.abs(number - intPart).toFixed(decimals).slice(2) : '');
    };
    humanize.naturalDay = function (timestamp, format) {
        timestamp = timestamp === undefined ? humanize.time() : timestamp;
        format = format === undefined ? 'Y-m-d' : format;
        var oneDay = 86400;
        var d = new Date();
        var today = new Date(d.getFullYear(), d.getMonth(), d.getDate()).getTime() / 1000;
        if (timestamp < today && timestamp >= today - oneDay) {
            return 'yesterday';
        } else if (timestamp >= today && timestamp < today + oneDay) {
            return 'today';
        } else if (timestamp >= today + oneDay && timestamp < today + 2 * oneDay) {
            return 'tomorrow';
        }
        return humanize.date(format, timestamp);
    };
    humanize.relativeTime = function (timestamp) {
        timestamp = timestamp === undefined ? humanize.time() : timestamp;
        var currTime = humanize.time();
        var timeDiff = currTime - timestamp;
        if (timeDiff < 2 && timeDiff > -2) {
            return (timeDiff >= 0 ? 'just ' : '') + 'now';
        }
        if (timeDiff < 60 && timeDiff > -60) {
            return timeDiff >= 0 ? Math.floor(timeDiff) + ' seconds ago' : 'in ' + Math.floor(-timeDiff) + ' seconds';
        }
        if (timeDiff < 120 && timeDiff > -120) {
            return timeDiff >= 0 ? 'about a minute ago' : 'in about a minute';
        }
        if (timeDiff < 3600 && timeDiff > -3600) {
            return timeDiff >= 0 ? Math.floor(timeDiff / 60) + ' minutes ago' : 'in ' + Math.floor(-timeDiff / 60) + ' minutes';
        }
        if (timeDiff < 7200 && timeDiff > -7200) {
            return timeDiff >= 0 ? 'about an hour ago' : 'in about an hour';
        }
        if (timeDiff < 86400 && timeDiff > -86400) {
            return timeDiff >= 0 ? Math.floor(timeDiff / 3600) + ' hours ago' : 'in ' + Math.floor(-timeDiff / 3600) + ' hours';
        }
        var days2 = 2 * 86400;
        if (timeDiff < days2 && timeDiff > -days2) {
            return timeDiff >= 0 ? '1 day ago' : 'in 1 day';
        }
        var days29 = 29 * 86400;
        if (timeDiff < days29 && timeDiff > -days29) {
            return timeDiff >= 0 ? Math.floor(timeDiff / 86400) + ' days ago' : 'in ' + Math.floor(-timeDiff / 86400) + ' days';
        }
        var days60 = 60 * 86400;
        if (timeDiff < days60 && timeDiff > -days60) {
            return timeDiff >= 0 ? 'about a month ago' : 'in about a month';
        }
        var currTimeYears = parseInt(humanize.date('Y', currTime), 10);
        var timestampYears = parseInt(humanize.date('Y', timestamp), 10);
        var currTimeMonths = currTimeYears * 12 + parseInt(humanize.date('n', currTime), 10);
        var timestampMonths = timestampYears * 12 + parseInt(humanize.date('n', timestamp), 10);
        var monthDiff = currTimeMonths - timestampMonths;
        if (monthDiff < 12 && monthDiff > -12) {
            return monthDiff >= 0 ? monthDiff + ' months ago' : 'in ' + -monthDiff + ' months';
        }
        var yearDiff = currTimeYears - timestampYears;
        if (yearDiff < 2 && yearDiff > -2) {
            return yearDiff >= 0 ? 'a year ago' : 'in a year';
        }
        return yearDiff >= 0 ? yearDiff + ' years ago' : 'in ' + -yearDiff + ' years';
    };
    humanize.ordinal = function (number) {
        number = parseInt(number, 10);
        number = isNaN(number) ? 0 : number;
        var sign = number < 0 ? '-' : '';
        number = Math.abs(number);
        var tens = number % 100;
        return sign + number + (tens > 4 && tens < 21 ? 'th' : {
            1: 'st',
            2: 'nd',
            3: 'rd'
        }[number % 10] || 'th');
    };
    humanize.filesize = function (filesize, kilo, decimals, decPoint, thousandsSep, suffixSep) {
        kilo = kilo === undefined ? 1024 : kilo;
        if (filesize <= 0) {
            return '0 Bytes';
        }
        if (filesize < kilo && decimals === undefined) {
            decimals = 0;
        }
        if (suffixSep === undefined) {
            suffixSep = ' ';
        }
        return humanize.intword(filesize, [
            'Bytes',
            'KB',
            'MB',
            'GB',
            'TB',
            'PB'
        ], kilo, decimals, decPoint, thousandsSep, suffixSep);
    };
    humanize.intword = function (number, units, kilo, decimals, decPoint, thousandsSep, suffixSep) {
        var humanized, unit;
        units = units || [
            '',
            'K',
            'M',
            'B',
            'T'
        ], unit = units.length - 1, kilo = kilo || 1000, decimals = isNaN(decimals) ? 2 : Math.abs(decimals), decPoint = decPoint || '.', thousandsSep = thousandsSep || ',', suffixSep = suffixSep || '';
        for (var i = 0; i < units.length; i++) {
            if (number < Math.pow(kilo, i + 1)) {
                unit = i;
                break;
            }
        }
        humanized = number / Math.pow(kilo, unit);
        var suffix = units[unit] ? suffixSep + units[unit] : '';
        return humanize.numberFormat(humanized, decimals, decPoint, thousandsSep) + suffix;
    };
    humanize.linebreaks = function (str) {
        str = str.replace(/^([\n|\r]*)/, '');
        str = str.replace(/([\n|\r]*)$/, '');
        str = str.replace(/(\r\n|\n|\r)/g, '\n');
        str = str.replace(/(\n{2,})/g, '</p><p>');
        str = str.replace(/\n/g, '<br />');
        return '<p>' + str + '</p>';
    };
    humanize.nl2br = function (str) {
        return str.replace(/(\r\n|\n|\r)/g, '<br />');
    };
    humanize.truncatechars = function (string, length) {
        if (string.length <= length) {
            return string;
        }
        return string.substr(0, length) + '\u2026';
    };
    humanize.truncatewords = function (string, numWords) {
        var words = string.split(' ');
        if (words.length < numWords) {
            return string;
        }
        return words.slice(0, numWords).join(' ') + '\u2026';
    };
    return humanize;
});
/** d e f i n e */
define('humanize', ['humanize/main'], function (target) { return target; });