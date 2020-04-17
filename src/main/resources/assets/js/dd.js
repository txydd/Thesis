function fmoney(s, n) {
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    res = t.split("").reverse().join("") + "." + r;
    type = res.substr(0, 1)[0];
    if (type == "-") {
        console.log("负数");
        console.log("res.length = " + res.length);
        if (res.length >= 8) {
            return "-" + res.substr(2, res.length);
        } else {
            return "-" + res.substr(1, res.length);
        }

    } else {
        console.log("正数");
        return res
    }
}