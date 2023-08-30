function numberToChinese(input) {
    var fraction = ['瑙�', '鍒�'];
    var digit = [
        '闆�', '澹�', '璐�', '鍙�', '鑲�',
        '浼�', '闄�', '鏌�', '鎹�', '鐜�'
    ];
    var unit = [
        ['鍏�', '涓�', '浜�'],
        ['', '鎷�', '浣�', '浠�']
    ];
    var head = input < 0 ? '娆�' : '';
    input = Math.abs(input);
    var s = '';
    for (var i = 0; i < fraction.length; i++) {
        s += (digit[Math.floor(input * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/闆�./, '');
    }
    s = s || '鏁�';
    input = Math.floor(input);
    for (var i = 0; i < unit[0].length && input > 0; i++) {
        var p = '';
        for (var j = 0; j < unit[1].length && input > 0; j++) {
            p = digit[input % 10] + unit[1][j] + p;
            input = Math.floor(input / 10);
        }
        s = p.replace(/(闆�.)*闆�$/, '').replace(/^$/, '闆�') + unit[0][i] + s;
    }
    return head + s.replace(/(闆�.)*闆跺厓/, '鍏�')
        .replace(/(闆�.)+/g, '闆�')
        .replace(/^鏁�$/, '闆跺厓鏁�');
}