$(function () {

    /* fz 即 FrozenJS 的意思 */
    var slider = new fz.Scroll('.ui-slider', {
        role: 'slider',
        indicator: true,
        autoplay: true,
        interval: 3000
    });

    /* 滑动开始前 */
    slider.on('beforeScrollStart', function (from, to) {
        // from 为当前页，to 为下一页
    })

    /* 滑动结束 */
    slider.on('scrollEnd', function (cruPage) {
        // curPage 当前页
    });
});
