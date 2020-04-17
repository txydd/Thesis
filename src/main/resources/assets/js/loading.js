function loadingIn() {
    $("body").append('<div id="loadArea"><div class="load-container load1"><div class="loader">Loading...</div><p id="loadAreaTips">正在加载请稍候</p></div></div>');
    $("#loadArea").fadeIn();
}
function loadingOut() {
    $("#loadArea").fadeOut(function () {
        $("#loadArea").remove();
    });

}